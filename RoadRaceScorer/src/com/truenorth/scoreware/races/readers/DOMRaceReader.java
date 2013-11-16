package com.truenorth.scoreware.races.readers;

import org.jsoup.select.Elements;

import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.Result;
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
			
		// parse the header
		((TableResultParser)resultParser).parseHeader(extractor.getTableHeaders());
		
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