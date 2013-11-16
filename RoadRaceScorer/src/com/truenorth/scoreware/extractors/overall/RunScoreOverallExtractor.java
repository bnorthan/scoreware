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
		
		int j=0;
		
		while(!endOfTable)
		{
			try
			{
				overall.add(lines.get(startOfTable+j));
			
				j++;
			
				if (lines.get(startOfTable+j).trim().length()<10)
				{
					endOfTable=true;
				}
			}
			catch(Exception e)
			{
				endOfTable=true;
			}
		}
			
		
		
		return overall;
	}
}
