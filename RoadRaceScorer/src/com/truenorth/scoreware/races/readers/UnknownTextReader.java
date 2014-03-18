package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.races.parsers.RunScoreResultParser;
import com.truenorth.scoreware.races.parsers.UnknownResultParser;
import com.truenorth.scoreware.data.HeaderStrings;
import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.extractors.overall.RunScoreOverallExtractor;
import com.truenorth.scoreware.extractors.overall.SimpleGetOverallResults;
import com.truenorth.scoreware.races.readers.DataAnalyzer;

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
		try
		{
			// try and set up for run score.  If successful return.
			if (setUpForRunScore())
			{
				return true;
			}
		}
		catch(Exception ex)
		{
			// keep going
		}
		
		// we have no idea what kind of format this is 1st lets see if we can find a header. 
		header=findHeader();
		
		// if we found a header
		if (header!=null)
		{
			System.out.println(header);
			
			// set up to parse unknown data using the header as a guide 
			resultParser=new UnknownResultParser();
			UnknownResultParser unknown=(UnknownResultParser)resultParser;
			unknown.parseHeader(header);
			
			overallExtractor=new SimpleGetOverallResults();
			
			return true;
		}
		
		
		// no header found
		return false;
		
	}
	
	protected boolean AnalyzeEntireTable()
	{
		race.getResults().clear();
		
		DataAnalyzer analyzer=new DataAnalyzer(text);
			
		analyzer.generateStats();
	
		analyzer.addResults(race.getResults());
		
		return true;
	}
	
	private String findHeader()
	{
		String headerString=null;
		
		float maxHeaderFuzzy=0.0f;
		float fuzzyThresh=0.8f;
		
		for (String s:text)
		{
			float headerFuzzy=HeaderStrings.fuzzyIsHeader(s);
			//int n=HeaderStrings.headerStringOccurrences(s);
//			System.out.println("header?? :"+s+" "+n);
			
			//if (n>3) headerString=s;
			
			if ( (headerFuzzy>maxHeaderFuzzy) && (headerFuzzy>fuzzyThresh) )
			{
				maxHeaderFuzzy=headerFuzzy;
				headerString=s;
			}
		}
		return headerString;
	}
	
	public void ReadRaceHeader()
	{
		
	}
	
	private boolean setUpForRunScore()
	{
		// find the line with all the "=" characters that will be just above the table header
		int maxMarkersLine=0;
		int maxMarkers=-1;
		String maxMarkersString="";
		char marker='=';
		int j=0;
				
		// loop through all lines
		for (String s:text)
		{
			
			int counter = 0;
			
			// count the number of markers
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
			// make sure the maxMarkersString can be split
			String[] split=maxMarkersString.split("\\s+");
			
			if (split.length==1)
			{
				return false;
			}
				
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
