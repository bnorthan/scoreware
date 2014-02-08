package com.truenorth.scoreware.races.readers;

import org.jsoup.select.Elements;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.extractors.TableExtractor;
import com.truenorth.scoreware.extractors.TableExtractorFactory;
import com.truenorth.scoreware.races.parsers.TableResultParser;

public class DOMRaceReader extends RaceReader
{
	public DOMRaceReader(Race race)
	{
		super(race);
		
		resultParser=new TableResultParser();
	}
	
	public void read()
	{
		// make a DOM Extractor and extract the race results table and it's header 
		TableExtractor extractor=TableExtractorFactory.MakeExtractor(race.getSourceName());
		Elements table=extractor.getTable(race.getSourceName(), false);
		Elements header=extractor.getTableHeaders();
			
		boolean headerPresent=false;
		
		if (header!=null)
		{
			// parse the header
			int numParsed=((TableResultParser)resultParser).parseHeader(extractor.getTableHeaders());
			if (numParsed>0)
			{
				headerPresent=true;
			}
		}
		
		
		if (!headerPresent)
		{
			DataAnalyzer analyzer=new DataAnalyzer(table);
			
			analyzer.addResults(race.getResults());
			
			return;
		}
		
		int numHeaders=extractor.getTableHeaders().size();
		
		
		for (int i=0;i<table.size();i++)
		{
			Result result=resultParser.parseResultFromLine(table.get(i));
			
			if (result!=null)
			{
				results.add(result);
			}
		}
	}
	
	public void ReadRaceHeader()
	{
		
	}
}