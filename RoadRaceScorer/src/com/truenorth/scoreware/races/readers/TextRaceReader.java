package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;
import com.truenorth.scoreware.extractors.DOMExtractor;
import com.truenorth.scoreware.extractors.TextExtractor;
import com.truenorth.scoreware.extractors.TextExtractorFactory;
import com.truenorth.scoreware.races.parsers.TableResultParser;
import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.common.utility.RunwareUtilities;

import org.jsoup.select.Elements;

/**
 * Extends RaceReader to implement a text based RaceReader.  
 * 
 * @author bnorthan
 *
 */
public class TextRaceReader extends RaceReader
{
	// utility to extract the source as text
	protected TextExtractor extractor;
	
	protected OverallExtractor overallExtractor;
	
	protected ArrayList<String> throwAwayLines; 
	
	protected ArrayList<String> text;
	
	public TextRaceReader(Race race)
	{
		super(race);
		
		// use the TextExtractorFactory to create the appropriate text extractor
		// based on the type of source.  The extractor will take the source (.pdf, .htm, etc)
		// and convert it to raw text
		this.extractor=TextExtractorFactory.MakeExtractor(race.getSourceName());
		
		// add "" to throwawaylines to get rid of any blank lines
		throwAwayLines=new ArrayList<String>();
		throwAwayLines.add("");
		
	}
		
	public void read()
	{
		// extract text from the source data (could be a PDF, web page, database, etc.)
		text=extractor.extractText(race.getSourceName());
		
		// at this point we should have the data as raw text...
		
		for (String s:text)
		{
			System.out.println(s);
		}
		
		if (true) return;
		
		// if there are any lines marked to be "thrown away" throw them away
		if (throwAwayLines!=null)
		{
			throwAway();
		}
		
		for (String s:text)
		{
			System.out.println(s);
		}
		
		
		// set things up for the next steps
		boolean initialized=initialize();
		
		
		
		if (!initialized)
		{
			return;// false;
		}
		
		// the overall results table may have to be extracted from the rest of the data
		if (overallExtractor!=null)
		{
			text=overallExtractor.extractText(text);
		}
		
		for (String line:text)
		{
			System.out.println(line);
		}
		
		System.out.println("size: "+text.size());
		
		//RunwareUtilities.Pause();
		
		// at this point we should just have a list of overall results.  Loop through each line 
		// and parse out the data
		if (text!=null)
		{
			for (String line:text)
			{
				Result result=resultParser.parseResultFromLine(line);
				
				if (result!=null)
				{
					results.add(result);
				}
			}
		}		
	}
	
	public void throwAway()
	{
		ArrayList<String> keepLines=new ArrayList<String>();
		for (String s:text)
		{
			boolean keep=true;
			
			for (String t:throwAwayLines)
			{
				if (t.equals(s))
				{
					keep = false;
				}
			}
			
			if (keep) 
			{
				keepLines.add(s);
			}
		}
		
		System.out.println("num lines: "+text.size());
		System.out.println("keep linesL "+keepLines.size());
		text=keepLines;
	}
	
	public boolean initialize()
	{
		return true;
	}
	
	public void ReadRaceHeader()
	{
		
	}
}
