package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.races.parsers.RunScoreResultParser;
import com.truenorth.scoreware.races.parsers.UnknownResultParser;
import com.truenorth.scoreware.extractors.overall.RunScoreOverallExtractor;
import com.truenorth.scoreware.HeaderStrings;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Race;

import com.truenorth.scoreware.extractors.overall.SimpleGetOverallResults;

public class UnknownTextReader extends TextRaceReader
{
	
	public UnknownTextReader(Race race)
	{
		super(race);
		
		extractor.setKeepSpaces(true);
	}
	
	@Override
	public boolean initialize()
	{
		// find out if it is runscore -- if so just set up for run score
		if (isRunScore())
		{
			return true;
		}
		
		// we have no idea what kind of format this is 1st lets see if we can find a header. 
		String header=findHeader();
		
		// if we found a header
		if (header!=null)
		{
			System.out.println(header);
			
			resultParser=new UnknownResultParser();
			
			UnknownResultParser unknown=(UnknownResultParser)resultParser;
			unknown.parseHeader(header);
			
			overallExtractor=new SimpleGetOverallResults();
			
			return true;
		}
		
		// no header
		boolean parsable=TryAFewThings();
				
		if (parsable)		
		{
			
		}
		
	}
	
	private boolean TryAFewThings()
	{
		for (String s:text)
		{
			String[] split=s.split("\\s+");
		}
		
		return false;
	}
	
	private String findHeader()
	{
		String headerString=null;
		for (String s:text)
		{
			int n=HeaderStrings.headerStringOccurrences(s);
//			System.out.println("header?? :"+s+" "+n);
			
			if (n>3) headerString=s;
		}
		return headerString;
	}
	
	private boolean isRunScore()
	{
		// find the line with all the "=" characters that will be just above the table header
		int maxMarkersLine=0;
		int maxMarkers=-1;
		String maxMarkersString="";
		char marker='=';
		int j=0;
				
		for (String s:text)
		{
			int counter = 0;
			for( int i=0; i<s.length(); i++ ) 
			{
			    if( s.charAt(i) == marker ) 
			    {
			        counter++;
			    } 
			}
					
			System.out.println(counter);
					
			if (counter>maxMarkers)
			{
				maxMarkers=counter;
				maxMarkersLine=j;
				maxMarkersString=s;
			}
			
			j++;
		}
		
		if (maxMarkers>15)
		{
				
			// the header names will be just above the "maxMarkersLine"
			String headerLine=text.get(maxMarkersLine-1);
			
			resultParser=new RunScoreResultParser();
			RunScoreResultParser rsParser=(RunScoreResultParser)(this.resultParser);
			
			rsParser.parseHeader(maxMarkersString, headerLine);
			
			overallExtractor=new RunScoreOverallExtractor(maxMarkersLine);
		   
			return true;
		}
		
		return false;
	}
}
