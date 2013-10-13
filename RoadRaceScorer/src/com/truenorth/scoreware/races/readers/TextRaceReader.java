package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;
import com.truenorth.scoreware.extractors.TableExtractor;
import com.truenorth.scoreware.extractors.TextExtractor;
import com.truenorth.scoreware.extractors.TextExtractorFactory;

import org.jsoup.select.Elements;

/**
 * Extends Reader to implement a RaceReader.  RaceReader reads
 * race results
 * @author bnorthan
 *
 */
public class TextRaceReader extends RaceReader
{
	protected boolean table=false;
	
	// utility to extract the source as text
	protected TextExtractor extractor;
	
	protected OverallExtractor overallExtractor;
	
	public TextRaceReader(String sourceName)
	{
		super(sourceName);
		
		// use the TextExtractorFactory to create the appropriate text extractor
		// based on the type of source
		this.extractor=TextExtractorFactory.MakeExtractor(sourceName);
		
		results=new ArrayList<Result>();
	}
	
	public void read()
	{
		if (table)
		{
			parseAsTable();
		}
		else
		{
			parseAsText();
		}
	}
	
	public void parseAsTable()
	{
		Elements table=TableExtractor.getTable(sourceName, false);
		
		for (int i=0;i<table.size();i++)
		{
			Result result=resultParser.parseResultFromLine(table.get(i));
			results.add(result);
		}
	}
	
	public void parseAsText()
	{
		// extract text from the source data (could be a PDF, web page, database, etc.)
		ArrayList<String> text=extractor.extractText(sourceName);
			
		if (text!=null)
		{
			System.out.println(sourceName+": lines read: "+text.size());
		}
		else
		{
			System.out.println("could not read file: ");
		}
		
		

	/*	for (String s:text)
		{
			
			int n=4;
		
			if (s.length()>90)
			{
				String num=s.substring(0,5);
				System.out.println(num);
			}
			
			System.out.println(s.length()+" "+s);
		}*/
	
				
		// extract the overall results table
		if (overallExtractor!=null)
		{
			text=overallExtractor.extractText(text);
		}
		
				
		System.out.println("number parsed: "+text.size());
				
	/*	// loop through each line of text in the overall results
		for(String line:text)
		{
			System.out.println(line);
					
			// parse the result
			Result result=resultParser.parseResultFromLine(line);
					
			// add the result to the list
			results.add(result);
		}		
				
		for(Result result:results)
		{
			System.out.println(result);//.getOverallPlace()+": "+result.getRacer().getFirstName()+
				//" "+result.getRacer().getLastName()+" "+result.getRacer().getAge()+" "+result.getRacer().getSex()
				//+" "+result.getRacer().getCity()+" "+result.getRacer().getState());
		}*/

	}
	
	public void setTable(boolean table)
	{
		this.table=table;
	}
}
