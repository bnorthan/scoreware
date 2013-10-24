package com.truenorth.scoreware.races.readers;

import org.jsoup.select.Elements;

import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.extractors.DOMExtractor;
import com.truenorth.scoreware.races.parsers.TableResultParser;

public class TableRaceReader extends RaceReader
{
	public TableRaceReader(Race race)
	{
		super(race);
	}
	
	public void read()
	{
		DOMExtractor extractor=new DOMExtractor();
		Elements table=extractor.getTable(race.getSourceName(), false);
			
		((TableResultParser)resultParser).parseHeader(extractor.getTableHeaders());
			
		for (int i=0;i<table.size();i++)
		{
			Result result=resultParser.parseResultFromLine(table.get(i));
			results.add(result);
		}
	}
	
	public void ReadRaceHeader()
	{
		
	}

}
