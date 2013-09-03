package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.overall.OverallFromSizeAndOrder;
import com.truenorth.scoreware.races.parsers.hmrrc.WinterSeries2013Parser;

import com.truenorth.scoreware.races.readers.RaceReader;

public class WinterSeries2013Reader extends RaceReader
{
	public WinterSeries2013Reader(String sourceName)
	{
		super(sourceName);
		
		order.add(ResultHeader.PLACE);
		order.add(ResultHeader.FIRSTNAME);
		order.add(ResultHeader.LASTNAME);
		order.add(ResultHeader.AGE);
		order.add(ResultHeader.SEX);
		order.add(ResultHeader.CITY);
		order.add(ResultHeader.STATE);
		order.add(ResultHeader.TIME);
		order.add(ResultHeader.PACE);
		
		overallExtractor=new OverallFromSizeAndOrder(order);
		resultParser=new WinterSeries2013Parser(order);	
		
		results=new ArrayList<Result>();
	}

}
