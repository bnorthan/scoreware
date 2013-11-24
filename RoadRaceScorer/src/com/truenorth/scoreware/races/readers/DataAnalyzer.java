package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.common.utility.ScoreWareStats;
import com.truenorth.scoreware.ScoreWareData;
import com.truenorth.scoreware.DataFormats;
import com.truenorth.scoreware.common.utility.RunwareUtilities;

import com.truenorth.scoreware.DataFormats.DataTypes;

public class DataAnalyzer 
{
	ArrayList<ArrayList<String>> lines;
	
	ArrayList<ArrayList<ScoreWareData>> data;
	
	int minCols;
	int maxCols;
	int medCols;
	
	int[] histBins;
	int[] histData;
	
	

	public DataAnalyzer(ArrayList<String> text)
	{
		// this is our last chance before asking a human to help
		lines=new ArrayList<ArrayList<String>>();
		data=new ArrayList<ArrayList<ScoreWareData>>();
		
		// build the ArrayList of data
		for (String s:text)
		{
			// split the string using space except for the case where 
			// the space is between two parts of a name
			String[] split=s.split("\\s+");
			
			ArrayList<String> list=new ArrayList<String>();
			ArrayList<ScoreWareData> dataList=new ArrayList<ScoreWareData>();
			
			for (String word:split)
			{
				list.add(word);
				
				ScoreWareData d=new ScoreWareData(word);
				d.setDataType(DataFormats.whatType(word));
				d.setResultHeader(DataFormats.whatHeader(word, d.getDataType()));
				dataList.add(d);
			}
				
			lines.add(list);
			data.add(dataList);
			
			String string="";
			
		/*	for (ScoreWareData swd:dataList)
			{
				string+=swd.getDataString();
				string+=swd.getDataType()+":";
				string+=swd.getResultHeader()+"\n";
			}
			
			System.out.println(string);
			
			RunwareUtilities.Pause();*/
		}
		
		generateStats();
		
		analyzeIntegerCollumn(0);
		
		analyzeCollumn(1);
		analyzeCollumn(2);
		analyzeCollumn(3);
		analyzeCollumn(4);
		analyzeCollumn(5);
		analyzeCollumn(6);
		
		
		
		RunwareUtilities.Pause();
		printArrayList(data);
	}
	
	public void analyzeCollumn(int c)
	{
		int numberRowsWithThisManyCollumns=0;
		
		for (ArrayList<ScoreWareData> dataList:data)
		{
			if (c<dataList.size())
			{
				numberRowsWithThisManyCollumns++;
			}
		}
		
		System.out.println("number rows with collumn "+c+" is: "+numberRowsWithThisManyCollumns);
		
		
	}
	
	public void analyzeIntegerCollumn(int c)
	{
		// loop through the collumns trying to identify what they are
		ArrayList<Integer> integerList=new ArrayList<Integer>();
		
		int n=0;
		
		for (ArrayList<ScoreWareData> dataList:data)
		{
			if (c<dataList.size())
			{
				ScoreWareData swd=dataList.get(c);
			
				if (swd.getDataType()==DataTypes.N)
				{
					integerList.add(Integer.parseInt(swd.getDataString()));
					n++;
				}
			}
		}	
	
		System.out.println("number data points"+data.size());
		System.out.println("number ints "+n);
		
		// apply the place test
		boolean isPlace=isPlace(integerList);
		
		if  (isPlace)
		{
			for (ArrayList<ScoreWareData> dataList:data)
			{
				dataList.get(c).setResultHeader(ResultHeader.PLACE);
			}
			return;
		}
	}
	
	public boolean isPlace(ArrayList<Integer> integerList)
	{
		int expectedPlace=1;
		int previousPlace=0;
		
		int nExpectedPlace=0;
		int nIncreasingPlace=0;
		
		int nTotal=integerList.size();
		
		// simple test to see if this is place
		for (Integer i:integerList)
		{
			if (i==expectedPlace) nExpectedPlace++;
			
			expectedPlace++;
			
			// occasionally we will have a subset of results but overall place
			// should still increase
			if (i>previousPlace)
			{
				nIncreasingPlace++;
			}
			
			previousPlace=i;
		}
		
		double ratio=(double)nIncreasingPlace/(double)nTotal;
		
		System.out.println("place complete: "+expectedPlace);
		System.out.println("place consistent: "+nIncreasingPlace);
		System.out.println("ratio: "+ratio);
		
		if (ratio>0.9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void printArrayList(ArrayList<ArrayList<ScoreWareData>> data)
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
	
	/**
	 * Generate some stats on the data structure
	 */
	public void generateStats()
	{
		// we are primarilly interested in the number of collumns and how consistent it is.
		// we expect most rows to have the same number of collumns. 
		// some rows will have less because data is missing.
		// some rows will have more because there is extra data. 
		
		minCols=Integer.MAX_VALUE;
		maxCols=Integer.MIN_VALUE;
		
		
		int[] numcollumnsarray=new int[lines.size()];
		int i=0;
		for (ArrayList<String> row:lines)
		{
			int ncols=row.size();
			
			numcollumnsarray[i]=ncols;
			
			if (ncols<minCols)
			{
				minCols=ncols;
			}
			
			if (ncols>maxCols)
			{
				maxCols=ncols;
			}
			
			i++;
		}
		
		int nbins=maxCols-minCols+1;
		
		histBins=new int[nbins];
		histData=new int[nbins];
		
		int hist[]=ScoreWareStats.calcHistogram(numcollumnsarray, minCols, maxCols);
		
		
		int maxcolsnumber=-1;
		int maxcolsPosition=-1;
		for (int n=0;n<nbins;n++)
		{
			if (hist[n]>maxcolsnumber)
			{
				maxcolsnumber=hist[n];
				maxcolsPosition=n;
			}
			System.out.println(n+minCols+" cols occurred "+hist[n]+" times.");
		}
		
		medCols=minCols+maxcolsPosition;
		
		System.out.println("mincols: "+minCols+" maxcols: "+maxCols+" medcols: "+medCols);
		
	}
	
	public ArrayList<ArrayList<String>> getLines()
	{
		return lines;
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
