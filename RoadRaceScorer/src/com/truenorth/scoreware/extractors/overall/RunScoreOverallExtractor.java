package com.truenorth.scoreware.extractors.overall;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;

public class RunScoreOverallExtractor implements OverallExtractor
{
	int maxMarkersLine;
	
	public RunScoreOverallExtractor(int maxMarkersLine)
	{
		this.maxMarkersLine=maxMarkersLine;
	}
	
	public ArrayList<String> extractText(ArrayList<String> lines)
	{
		ArrayList<String> overall=new ArrayList<String>();
	
		int startOfTable=maxMarkersLine+1;
		boolean endOfTable=false;
		
		int j=1;
		
		for (String line:lines)
		//while(!endOfTable)
		{
			try
			{
				String trim=line.trim();
				String split[]=trim.split("\\s+");
				
				String temp=split[0];
				temp=temp.trim();
					
				int place=Integer.parseInt(temp);
					
				if (place==j)
				{
					overall.add(line);
					j++;
				}
			}
			catch(Exception e)
			{
				//continue
			}
		}
		
		return overall;
	}
}
