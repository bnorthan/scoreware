package com.truenorth.scoreware.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.truenorth.scoreware.common.utility.GenericStats;
import com.truenorth.scoreware.common.utility.RunwareUtilities;
import com.truenorth.scoreware.common.utility.ScoreWareStats;

import com.truenorth.scoreware.data.DataFormats.DataTypes;
import com.truenorth.scoreware.data.Enums.ResultHeader;

public class ScoreWareGroup 
{
	// the index of the data within the raw row
	// **NOTE** for an individual piece of data this is an integer
	// but for the group it can be a float (because it is the average position)
	float index;
	
	// the index of the data in terms of type within the raw row (example 3rd integer)
	float typeIndex;
	
	// the average starting position of the data
	float startPosition;
	// the average ending position of the data
	float endPosition;
	
	// is the start alligned
	boolean startAlligned;
	
	// is the end alligned
	boolean endAlligned;
	
	DataTypes type;
	
	ResultHeader header;
	
	ArrayList<ScoreWareData> data;
	
	float numStrings=1;
	
	public ScoreWareGroup()
	{
		data=new ArrayList<ScoreWareData>();
	}
	
	public void addFirstData(ScoreWareData swd)
	{
		// initialize with the type of data and the type index
		type=swd.getDataType();
		header=swd.getResultHeader();
		typeIndex=swd.getDataTypeIndex();
		data.add(swd);
	}
	
	public void compileStats()
	{
		index=0.0f;
		typeIndex=0.0f;
		startPosition=0.0f;
		endPosition=0.0f;
		
		numStrings=0.0f;
		
		Integer[] arrIndex=new Integer[data.size()];
		Integer[] arrTypeIndex=new Integer[data.size()];
		Integer[] arrStart=new Integer[data.size()];
		Integer[] arrFinish=new Integer[data.size()];
		Integer[] arrStrings=new Integer[data.size()];
		
		int i=0;
		for (ScoreWareData swd:data)
		{
			arrIndex[i]=swd.getCollumn();
			arrTypeIndex[i]=swd.getDataTypeIndex();
			arrStart[i]=swd.getStartPosition();
			arrFinish[i]=swd.getEndPosition();
			
			arrStrings[i]=swd.getDataString().split("\\s").length;
			
			i++;
		}
		
		numStrings=(float)(GenericStats.CalculateMean(arrStrings));
		
		System.out.println("Mean index: "+GenericStats.CalculateMean(arrIndex));
		System.out.println("Mean num strings: "+numStrings);
		
	//	RunwareUtilities.Pause();
		
		
		if (type==DataTypes.N)
		{
			
			// form list of integers
			ArrayList<Integer> integerList=new ArrayList<Integer>();
			
			for (ScoreWareData swd:data)
			{
				int n=Integer.parseInt(swd.getDataString());
				integerList.add(n);
			}
			
			// apply the place test
			boolean isPlace=ScoreWareStats.isPlace(integerList);
			
			if (isPlace)
			{
				System.out.println("PlacePlacePlace");
				header=ResultHeader.PLACE;
				return;
			}
			
			// apply the age test
			boolean isAge=ScoreWareStats.isAge(integerList);
			
			if (isAge)
			{
				System.out.println("AgeAgeAge");
				header=ResultHeader.AGE;
				return;
			}	
		}
		else if (type==DataTypes.LETTERS)
		{
			// if data has not been identified yet
			if (header==null)
			{
				// form a list of strings
				ArrayList<String> stringList=new ArrayList<String>();
				
				for (ScoreWareData swd:data)
				{
					//	System.out.println(swd.getDataString());
					stringList.add(swd.getDataString());
				}
				
				// test for first name
				if (numStrings<1.25)
				{
					if (MatchStrings.isListOfFirstNames(stringList))
					{
						header=ResultHeader.FIRSTNAME;
						return;
					}
				}
				
				// test for last name
				
				// test for first last name
				if (numStrings>=2)
				{
					ArrayList<String> firsts=new ArrayList<String>();
					
					for (String s:stringList)
					{
						String[] split=s.split("\\s");
						firsts.add(split[0]);
					}
					
					// apply first name test to firsts
					if (MatchStrings.isListOfFirstNames(firsts))
					{
						header=ResultHeader.FULLNAME;
						return;
					}
				}
				
				// test for city
				if (numStrings<2)
				{
					if (MatchStrings.isListOfCities(stringList))
					{
						header=ResultHeader.CITY;
						return;
					}
				}
					
				else if (numStrings>1.5)
				{
					
				}
				
				RunwareUtilities.Pause();
			}
		}

	}
	
	public void setDataHeadersFromGroup()
	{
		if (this.header!=null)
		{
			for (ScoreWareData swd:this.data)
			{
				swd.setResultHeader(header);
			}
		}
	}
	
	public void setGroupHeaderFromData()
	{
		Map<ResultHeader, Integer> rsMap;
		
		// create and initialize a map to keep track of the header of the data
		rsMap=new HashMap<ResultHeader, Integer>();
		for (ResultHeader rs:ResultHeader.values())
		{
			rsMap.put(rs, 0);
		}
		
		// loop through the data
		for (ScoreWareData swd:this.data)
		{
			// get the header (if known)
			ResultHeader rs=swd.getResultHeader();
			
			// put the header in the map (if known)
			if (rs!=null)
			{
					rsMap.put(rs, rsMap.get(rs)+1);
			}
		}
		
		int max=-1;
		ResultHeader maxHeader=null;
		
		Iterator it=rsMap.entrySet().iterator();
		
		// iterate through looking at the data headers (if they were found yet)
		// same as above... ideally they would all be the same but this is often not the case
		while (it.hasNext())
		{
			 Map.Entry pairs = (Map.Entry)it.next();
			 
			 int num=(int)( pairs.getValue());
			        	
			 if ((int)(pairs.getValue())!=0)
			 {
			     System.out.println(pairs.getKey()+" :"+pairs.getValue());
			 }
			 
			 if (num>max)
	         {
	        	maxHeader=(ResultHeader)pairs.getKey();
	        	max=num;
	         }
		}
		
		this.header=maxHeader;
	}
	
	public ArrayList<ScoreWareData> getData()
	{
		return this.data;
	}
	
	public DataTypes getType()
	{
		return this.type;
	}
	
	public ResultHeader getResultHeader()
	{
		return header;
	}
	
	public float getTypeIndex()
	{
		// cast typeIndex to integer and return it
		return this.typeIndex;
	}

}
