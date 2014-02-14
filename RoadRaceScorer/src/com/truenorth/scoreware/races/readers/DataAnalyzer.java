package com.truenorth.scoreware.races.readers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.truenorth.scoreware.common.utility.ScoreWareStats;
import com.truenorth.scoreware.common.utility.RunwareUtilities;
import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.data.DataFormats;
import com.truenorth.scoreware.data.Enums;
import com.truenorth.scoreware.data.MatchStrings;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.ScoreWareData;
import com.truenorth.scoreware.data.ScoreWareGroup;
import com.truenorth.scoreware.data.ScoreWareGroups;
import com.truenorth.scoreware.data.DataFormats.DataTypes;
import com.truenorth.scoreware.data.Enums.ResultHeader;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class DataAnalyzer 
{	
	ArrayList<ArrayList<ScoreWareData>> data;
	
	ScoreWareGroups groups;
	
	Map<DataTypes, Integer> dtMap;
	Map<ResultHeader, Integer> rsMap;
	int minCols;
	int maxCols;
	int modeCols;
	
	int[] histBins;
	int[] histData;
	
	// some booleans to track if we have found the main data we are looking for
	boolean placeFound=false;
	boolean firstNameFound=false;
	boolean lastNameFound=false;
	boolean ageFound=false;
	boolean bibFound=false;
	boolean stateFound=false;
	boolean cityFound=false;
	boolean gunTimeFound=false;
	
	public DataAnalyzer(Elements elements)
	{
		initStructures();
		
		int test=elements.size();
		
		// loop through all the elements in the table
		for (int i=0;i<elements.size();i++)
		{
			Element element=elements.get(i);
			
			// create a list to store each piece of data from the line
			ArrayList<ScoreWareData> dataList=new ArrayList<ScoreWareData>();
									
			for (int j=0;j<element.children().size();j++)
			{
				String text=element.child(j).text();
				System.out.println(text);
				
				ScoreWareData swd=new ScoreWareData(text);
				
				// find the type of data this word represents (Letters, int, etc.)
				DataTypes dt=DataFormats.whatType(text);
				swd.setDataType(dt);
				
				// see if we can determine the result header (Place, Gender, etc.)
				swd.setResultHeader(DataFormats.whatHeader(text, swd.getDataType()));
				
				swd.setCollumn(j);
				swd.setRow(i);
				
				// add the data to the list
				dataList.add(swd);
				
				// add the data to the datagroups
				groups.addDataToGroup(swd);
				
			}
			
			data.add(dataList);
		}
		
		// first pass at generating some stats on the data
		generateStats();
		
		analyzeTimes();
		
		int nGroups=groups.getGroups().size();
		
		for (ScoreWareGroup group:groups.getGroups())
		{
			group.compileStats();
		}
		
		printDataArray();
		
		for (ScoreWareGroup group:groups.getGroups())
		{
			if (group.getResultHeader()==null)
			{
				group.setGroupHeaderFromData();
			}
			else
			{
				group.setDataHeadersFromGroup();
			}
			
			System.out.println("types: "+group.getType()+" type index: "+group.getTypeIndex()+" header: "+group.getResultHeader()+" num: "+group.getData().size());
		}
				
	}
	
	public DataAnalyzer(ArrayList<String> text)
	{
		// this is our last chance before asking a human to help
		initStructures();
		
		int r=0;
		
		// see if there is a header
		
		// build the 2D ArrayList of data by analyzing each line of text
		for (String line:text)
		{	
			// make a copy of the line
			String orig=line.toString();
			
			// split the string using space 
			String trimmed=line.trim();
			String[] split=trimmed.split("\\s+");
			
			// create a list to store each piece of data from the line
			ArrayList<ScoreWareData> dataList=new ArrayList<ScoreWareData>();
			
			// create and initialize a map to keep track of the types of data we find in the line
			HashMap<DataTypes, Integer> typesMap=new HashMap<DataTypes, Integer>();
			
			// initialize the types map.  This is used to keep track of how many
			// instances of each type of data we find. 
			for (DataTypes type:DataTypes.values())
			{
				typesMap.put(type, 0);
			}
			
			// index
			int i=0;
			
			int c=0;
			// loop through every word on the line
			for (String word:split)
			{
				// some light preprocessing before we put the word in the list
				word=preprocessWord(word);
				
				// create a ScoreWareData structure for the word
				ScoreWareData d=new ScoreWareData(word);
				
				// compile some useful information about the data
				
				// index of the word within the trimmed string
				int index=line.indexOf(word);
				
				// character position in original string
				int position=i+index;
				int ptest=orig.indexOf(word);
				
				// increment i to reflect current character position in original string (which is being trimmed as we go)
				i=i+index+word.length();
				
				// trim the word out of s so we don't find the first index of repeat values (like 2 "1"s)
				line=line.substring(index+word.length());
				
				// find the type of data this word represents (Letters, int, etc.)
				DataTypes dt=DataFormats.whatType(word);
				d.setDataType(dt);
				
				// get the typeIndex (ie if this is the 3rd integer we found in the line the type index would be 3)
				int typeIndex=typesMap.get(dt)+1;
				typesMap.put(dt, typeIndex);
				d.setDataTypeIndex(typeIndex);
				
				// see if we can determine the result header (Place, Gender, etc.)
				d.setResultHeader(DataFormats.whatHeader(word, d.getDataType()));
				
				// set the start and end character positions of the word
				d.setStartPosition(position);
				d.setEndPosition(i);
				
				d.setCollumn(c);
				d.setRow(r);
				
				// add the data to the list
				dataList.add(d);
				
				// add the data to the datagroups
				groups.addDataToGroup(d);
				
				c++;
			}// end for (String word:split)
				
			// add the list of data from this line to the overall data list
			data.add(dataList);
			r++;
		}// end for (String line:text)
		
		// first pass at generating some stats on the data
		generateStats();
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// form the groups of data
		for (int c=0;c<maxCols;c++)
		{
			//convertCollumnToGroup(c);
		}
		
		int nGroups=groups.getGroups().size();
		
		for (ScoreWareGroup group:groups.getGroups())
		{
			System.out.println("types: "+group.getType()+" type index: "+group.getTypeIndex()+" header: "+group.getResultHeader()+" num: "+group.getData().size());
			group.compileStats();
		}
				
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// loop through all the groups
	    
		// loop through all the collumns
		for (int i=0;i<maxCols;i++)
		{
			// what type of data??
			DataTypes type=analyzeCollumn(i);
			
			// if integer
			if (type==DataTypes.N)
			{
				analyzeIntegersInCollumn(i);
			}
			// if letters
			else if (type==DataTypes.LETTERS)
			{
				analyzeLettersCollumn(i);
			}
		}
		
		// this routine analyzes the times (could be gun time, net time, and possible splits)
		analyzeTimes();
		
		// this routine analyzes groups of letters
		analyzeLetters();
		
	//	RunwareUtilities.Pause();
		printDataArray();
	}
	
	private void initStructures()
	{
		// the data structure organizes the data by rows.  Each row is separated 
		// into multiple pieces of data.  Ideally all rows would have the same number
		// of data but this is ussually not the case.  Data can be missing.  Sometimes names
		// have multiple words.
		data=new ArrayList<ArrayList<ScoreWareData>>();
		
		// the groups data structure organizes data into groups of similar pieces of data. 
		// the groups start off as general (for example integers, letters, etc) and the goal
		// is to identify what each group represents.  Data can be transferred from one group to
		// another as we find out more. 
		groups=new ScoreWareGroups();
	
	}
	
	public void convertCollumnToGroup(int c)
	{
		int row=0;
		
		// loop through all the data
		for (ArrayList<ScoreWareData> dataList:data)
		{
			// if the collumn exists
			if (c<dataList.size())
			{
				row++;
				
				ScoreWareData swData=dataList.get(c);
				
				groups.addDataToGroup(swData);
				
			}
		}
	}
	
	/**
	 * analyzes a collumn of data
	 * 
	 * @param c
	 * collumn of data to analyze 
	 * 
	 * @return
	 * 
	 * the type of data in the collumn
	 * (if more than one type returns the type that occurs most often
	 */
	public DataTypes analyzeCollumn(int c)
	{
		int numberRowsWithThisManyCollumns=0;
		
		// create and initialize a map to keep track of the type of data
		dtMap=new HashMap<DataTypes, Integer>();
		for (DataTypes type:DataTypes.values())
		{
			dtMap.put(type, 0);
		}
		
		// create and initialize a map to keep track of the header of the data
		rsMap=new HashMap<ResultHeader, Integer>();
		for (ResultHeader rs:ResultHeader.values())
		{
			rsMap.put(rs, 0);
		}
		
		// loop through all the data
		for (ArrayList<ScoreWareData> dataList:data)
		{
			// if the collumn exists
			if (c<dataList.size())
			{
				// increment the collumn count
				numberRowsWithThisManyCollumns++;
				
				// take a closer look at the data
				ScoreWareData swData=dataList.get(c);
				
				// get the data type 
				DataTypes type=swData.getDataType();
				
				// get the header (if known)
				ResultHeader rs=swData.getResultHeader();
				
				// put type in the map
				dtMap.put(type, dtMap.get(type)+1);
				
				// put the header in the map (if known)
				if (rs!=null)
				{
 					rsMap.put(rs, rsMap.get(rs)+1);
				}
			}
		}
		
		System.out.println("number rows with collumn "+c+" is: "+numberRowsWithThisManyCollumns);
		
		// iterator to loop through data type map
		Iterator it=dtMap.entrySet().iterator();
		
		int max=-1;
		DataTypes maxType=null;
		
		// now iterate through the map and see what type of data we have
		// (ideally there would be only one type of data in each collumn... because of missing data 
		//  this is not always the case)
		 while (it.hasNext())
	     {
			 	Map.Entry pairs = (Map.Entry)it.next();
			 	int num=(int)( pairs.getValue());
		        
	        	System.out.println(pairs.getKey()+" :"+num);
	        	
	        	if (num>max)
	        	{
	        		//
	        		maxType=(DataTypes)pairs.getKey();
	        		max=num;
	        	}
	    }
		 
		 // if data is not consistent (contains different datatypes)
		 if (max!=numberRowsWithThisManyCollumns)
		 {
			 System.out.println("INCONSISTENT!!!!!!!!!!!!!!!!!!!");
		 }
		 
		 // now set iterator to loop through result header map
		 it=rsMap.entrySet().iterator();
		 
		 System.out.println();
		 
		 // iterate through looking at the data headers (if they were found yet)
		 // same as above... ideally they would all be the same but this is often not the case
		 while (it.hasNext())
	     {
	        	Map.Entry pairs = (Map.Entry)it.next();
	        	
	        	if ((int)(pairs.getValue())!=0)
	        	{
	        		System.out.println(pairs.getKey()+" :"+pairs.getValue());
	        	}
	    }
		 
		 System.out.println(maxType);
		 System.out.println();
		 
	//	 RunwareUtilities.Pause();
		
		 // return the type that has occurred the most
		 return maxType;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void analyzeIntegersInCollumn(int c)
	{
		// a list of the integers
		ArrayList<Integer> integerList=new ArrayList<Integer>();
		
		int n=0;
		
		// form the list of integers in this collumn
		for (ArrayList<ScoreWareData> dataList:data)
		{
			// if the collumn exists
			if (c<dataList.size())
			{
				// get the data
				ScoreWareData swd=dataList.get(c);
			
				// if the data type is an integer
				if (swd.getDataType()==DataTypes.N)
				{
					// add it to the list
					integerList.add(Integer.parseInt(swd.getDataString()));
					// count the number of integers we find
					n++;
				}
			}
		}	
	
		System.out.println("number data points"+data.size());
		System.out.println("number ints "+n);
		System.out.println();
		
		// apply the place test
		boolean isPlace=ScoreWareStats.isPlace(integerList);
		
		// if the data is a place label it as such and return
		if  (isPlace)
		{
			for (ArrayList<ScoreWareData> dataList:data)
			{
				if (c<dataList.size())
				{
					dataList.get(c).setResultHeader(ResultHeader.PLACE);
				}
			}
			return;
		}
		
		// apply the age test 
		boolean isAge=ScoreWareStats.isAge(integerList);
		
		// if the data is a age label it as such and return
		if (isAge)
		{
			ageFound=true;
			
			for (ArrayList<ScoreWareData> dataList:data)
			{
				if (c<dataList.size())
				{
					if (dataList.get(c).getDataType()==DataTypes.N)
					{
						dataList.get(c).setResultHeader(ResultHeader.AGE);
					}
				}
			}
			return;
		}
	}// end analyzeIntegersInCollumn(int c)
	
	public void analyzeLettersCollumn(int c)
	{
		// a list of the letter strings
		ArrayList<String> stringList=new ArrayList<String>();
		
		int n=0;
		
		// loop through all the data
		for (ArrayList<ScoreWareData> dataList:data)
		{
			// if the collumn exists
			if (c<dataList.size())
			{
				ScoreWareData swd=dataList.get(c);
				
				stringList.add(swd.getDataString());
				
				n++;
			}
		}	
		
		// test to see if it is first name
		if (!firstNameFound)
		{
			
			firstNameFound=MatchStrings.isListOfFirstNames(stringList);
			
			if (firstNameFound)
			{
				// this is first name
				firstNameFound=true;
			
				// look for last name next
				for (ArrayList<ScoreWareData> dataList:data)
				{
					if (c<dataList.size())
					{
						if (dataList.get(c).getDataType()==DataTypes.LETTERS)
						{
							dataList.get(c).setResultHeader(ResultHeader.FIRSTNAME);
						}
					}
					
					// look for a second part of last name
					if ( (c+1)<dataList.size() )
					{
						if ( (dataList.get(c+1).getDataType()==DataTypes.LETTERS) &&
								(dataList.get(c+1).getResultHeader()==null) )
						{
							dataList.get(c+1).setResultHeader(ResultHeader.LASTNAME);
						}
					}
					
					/*if ( (c+2)<dataList.size() )
					{
						if ( (dataList.get(c+1).getDataType()==DataTypes.LETTERS) &&
								(dataList.get(c+1).getResultHeader()==null) )
						{
							dataList.get(c+1).setResultHeader(ResultHeader.LASTNAME);
						}
					}*/
				} // end for (ArrayList<ScoreWareData> dataList:data)
			} // if (ratio>0.15)
		}
	}
	
	// this routine looks through each row, finds all the times and identifies 
	// gun time, chip time and other splits
	void analyzeTimes()
	{
		// for every row of data
		for (ArrayList<ScoreWareData> dataList:data)
		{
			ArrayList<ScoreWareData> times=new ArrayList<ScoreWareData>();
			
			// loop through the row
			for (ScoreWareData sw:dataList)
			{
				// get data type
				DataTypes dt=sw.getDataType();
				
				if ( (dt==DataTypes.MM_SS)|| (dt==DataTypes.HH_MM_SS) )
				{
					times.add(sw);
				}
			}
			
			Date largest=new Date(0L);
			int posLargest=-1;
			
			for (int i=0;i<times.size();i++)
			{
				String strTemp=times.get(i).getDataString();
				Date temp=DateTimeParser.getTime(strTemp);
				
				if (temp.after(largest))
				{
					largest=temp;
					posLargest=i;
				}
				
			//	System.out.println(temp+" : "+largest);
			}
			
			if (posLargest!=-1)
			{
				times.get(posLargest).setResultHeader(ResultHeader.GUN_TIME);
			}
			
			//RunwareUtilities.Pause();
		}
	}
	
	void analyzeLetters()
	{
		for (ArrayList<ScoreWareData> dataList:data)
		{
			int length=dataList.size();
			
			for (int c=0;c<length;c++)
			{
				ScoreWareData sw=dataList.get(c);
				
				if (sw.getDataType()==DataTypes.LETTERS)
				{
					if (sw.getResultHeader()==null)
					{
						// if next is state assign this as city
						if (c+1<length)
						{
							ScoreWareData n=dataList.get(c+1);
							
							if (n.getResultHeader()==ResultHeader.STATE)
							{
								sw.setResultHeader(ResultHeader.CITY);
							}
						}
						
						// if previous is last name append this to last name
						if (c!=0)
						{
							
							ScoreWareData p=dataList.get(c-1);
							
							int temp1=sw.getStartPosition();
							int temp2=p.getStartPosition()+p.getDataString().length();
							
							if (temp1==temp2)
							{
								int stop=5;
							}
							if (temp1==(temp2+1))
							{
								int stop=5;
							}
						
							if (temp1==(temp2+3))
							{
								int stop=5;
							}
						
							if (temp1==temp2)
							{
								int stop=5;
							}
						
						
							
							if ( (p.getResultHeader()==ResultHeader.LASTNAME) &&
									(temp1==(temp2+2) ) )
							{
								sw.setResultHeader(ResultHeader.LASTNAME);
							}
						}
					}
				}
			}
		}
	}
	
	void mergeCollumns()
	{
		
	}
	
	public void printDataArray()
	{
		for (ArrayList<ScoreWareData> dataList:data)
		{
			String string="";
			
			for (ScoreWareData swd:dataList)
			{
				string+=swd.getDataString();
				string+=swd.getDataType()+":";
				string+=swd.getResultHeader()+" ";
			}
			
			System.out.println(string);
		}
	}
	
	public void addResults(ArrayList<Result> results)
	{
			
		for (ArrayList<ScoreWareData> dataList:data)
		{
			Result result = new Result();
			
			for (ScoreWareData swd:dataList)
			{
				if (swd.getResultHeader()==ResultHeader.PLACE)
				{
					try
					{
						int place=Integer.parseInt(swd.getDataString());
						result.setOverallPlace(place);
					}
					catch(Exception e)
					{
						
					}
				}
				if (swd.getResultHeader()==ResultHeader.AGE)
				{
					try
					{
						int age=Integer.parseInt(swd.getDataString());
						result.getRacer().setAge(age);
					}
					catch(Exception e)
					{
						
					}
				}
				else if (swd.getResultHeader()==ResultHeader.FIRSTNAME)
				{
					result.getRacer().setFirstName(swd.getDataString());
				}
				else if (swd.getResultHeader()==ResultHeader.LASTNAME)
				{
					if (result.getRacer().getLastName()==null)
					{
						result.getRacer().setLastName(swd.getDataString());
					}
					else 
					{
						//append
						String lname=result.getRacer().getLastName()+" "+swd.getDataString();
						result.getRacer().setLastName(lname);
					}
				}
				else if (swd.getResultHeader()==ResultHeader.FULLNAME)
				{
					String[] name=swd.getDataString().split("\\s");
					
					result.getRacer().setFirstName(name[0]);
					
					for (int i=1;i<name.length;i++)
					{
						if (i==1)
						{
							result.getRacer().setLastName(name[i]);
						}
						else
						{
							result.getRacer().setLastName(result.getRacer().getLastName()+" "+name[i]);
						}
					}
				}
				else if (swd.getResultHeader()==ResultHeader.GUN_TIME)
				{
					result.setGunTimeString(swd.getDataString());
				}
				else if (swd.getResultHeader()==ResultHeader.STATE)
				{
					result.getRacer().setState(swd.getDataString());
				}
				else if (swd.getResultHeader()==ResultHeader.CITY)
				{
					result.getRacer().setCity(swd.getDataString());
				}
				
			
			}
			results.add(result);
		}
			
	}
	
	/**
	 * Generate some stats on the data structure
	 */
	public void generateStats()
	{
		// we are primarilly interested in the number of collumns and how consistent it is.
		// we expect most rows to have the same number of collumns. 
		// some rows will have less because data is missing.
		// some rows will have more because there is extra data. 
		
		int[] numcollumnsarray=new int[data.size()];
		int i=0;
		for (ArrayList<ScoreWareData> row:data)
		{
			int ncols=row.size();
			
			numcollumnsarray[i]=ncols;
		
			i++;
		}
		
		ScoreWareStats stats= new ScoreWareStats();
		
		stats.calcIntegerHistogram(numcollumnsarray);
		
		int hist[]=stats.getHistogram(); //ScoreWareStats.calcHistogram(numcollumnsarray, minCols, maxCols);
		
		maxCols=stats.getMax();
		minCols=stats.getMin();
		modeCols=stats.getMode();
		
		System.out.println("mincols: "+minCols+" maxcols: "+maxCols+" modecols: "+modeCols);
		
	}
	
	
/*	public ArrayList<ArrayList<String>> getLines()
	{
		return lines;
	}*/
	
	private String preprocessWord(String word)
	{
		// check for ss.mm or h.ss.mm formats
		DateFormat format_mmss=new SimpleDateFormat("mm.ss");
		Date mmss_test=null;
		try
		{
			mmss_test=format_mmss.parse(word);
		}
		catch(Exception e)
		{
			
		}
		
		DateFormat format_hmmss=new SimpleDateFormat("HH:mm.ss");
		Date hhmmss_test=null;
		try
		{
			hhmmss_test=format_mmss.parse(word);
		}
		catch(Exception e)
		{
			
		}
		
		if ( (mmss_test!=null) || (hhmmss_test!=null) )
		{
			String returnString = word.replace(".", ":");
			return returnString;
		}
		
		// get rid of trailing comma
		word.replace(",", "");
		
		return word;
	}
	
	
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////
// scrap area
///////////////////////////////////////////////////////////////////////////////////////////////////////////

/*for (String s:text)
{
	Pattern p=Pattern.compile("(^|\\s)[1-9]+(\\s|$)");
	
	//Pattern p=Pattern.compile("(?<!\\s)[1-9]+(?!\\s)");
	
	Matcher m=p.matcher(s);
	
	System.out.println(s);
	
	while(m.find())
	{
		System.out.println(m.group());
	}
		
	if (true) return;
	for (String st:s.split("[^\\d]\\s+"))
	{
		System.out.println(st);
	}
	
	
}*/
