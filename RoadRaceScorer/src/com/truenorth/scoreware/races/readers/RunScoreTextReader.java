package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.races.parsers.RunScoreResultParser;

/**
 * Extends TextRaceReader to read results that are in the "run score" format
 * @author bnorthan
 *
 */
public class RunScoreTextReader extends TextRaceReader
{
	
	public RunScoreTextReader(Race race)
	{
		super(race);
		
		resultParser=new RunScoreResultParser();
		
		extractor.setKeepSpaces(true);
	}
		
	@Override
	public void read()
	{
		// extract text from the source data (could be a PDF, web page, database, etc.)
		ArrayList<String> text=extractor.extractText(race.getSourceName());
			
		if (text!=null)
		{
			System.out.println(race.getSourceName()+": lines read: "+text.size());
		}
		else
		{
			System.out.println("could not read file: ");
		}
	
		// find the line with all the "=" characters that will be just above the table header
		int maxMarkersLine=0;
		int maxMarkers=-1;
		String maxMarkersString="";
		char marker='=';
		int j=0;
		
		for (String s:text)
		{
			int counter = 0;
			for( int i=0; i<s.length(); i++ ) {
			    if( s.charAt(i) == marker ) {
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
		
		// the header names will be just above the "maxMarkersLine"
		String headerLine=text.get(maxMarkersLine-1);
		
		RunScoreResultParser rsParser=(RunScoreResultParser)(this.resultParser);
		
		rsParser.parseHeader(maxMarkersString, headerLine);
	    
		int startOfTable=maxMarkersLine+1;
		boolean endOfTable=false;
		j=0;
		
		while(!endOfTable)
		{
			Result result;
			
			try
			{
				result=rsParser.parseResultFromLine(text.get(startOfTable+j));
				//	System.out.println(j+" "+result);
				results.add(result);
			}
			catch(Exception e)
			{
				
			}
			
			j++;
			
			if (text.get(startOfTable+j).trim().length()<10)
			{
				endOfTable=true;
			}
		}
		
		
	/*	String testLine=text.get(maxMarkersLine+1);
		
		i=0;
		for (int n:locs)
		{
			String temp=testLine.substring(i,n);
			i=n;
			temp=temp.trim();
			System.out.println(temp);
		}
		
		System.out.println(split.length);*/
	}
	
	public void ReadRaceHeader()
	{
		
	}
}
