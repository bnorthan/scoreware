package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;

import com.truenorth.scoreware.extractors.TableExtractor;

import org.jsoup.select.Elements;

/**
 * Extends Reader to implement a RaceReader.  RaceReader reads
 * race results
 * @author bnorthan
 *
 */
public abstract class HtmlRaceReader extends RaceReader 
{
	protected boolean table=false;
	
	public HtmlRaceReader(String sourceName)
	{
		super(sourceName);
		
		results=new ArrayList<Result>();
	}
	
	protected OverallExtractor overallExtractor;
	
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
			resultParser.parseResultFromLine(table.get(i));
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
				
		// extract the overall results table
		if (overallExtractor!=null)
		{
			text=overallExtractor.extractText(text);
		}
				
		System.out.println("number parsed: "+text.size());
				
		// loop through each line of text in the overall results
		for(String line:text)
		{
			System.out.println(line);
					
			// parse the result
			Result result=resultParser.parseResultFromLine(line);
					
			// add the result to the list
			results.add(result);
		}		
				
		/*for(Result result:results)
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