package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.overall.OverallFromSizeAndOrder;
import com.truenorth.scoreware.races.parsers.hmrrc.Anniversary2013Parser;

import com.truenorth.scoreware.races.readers.RaceReader;

/**
 * Extends RaceReader to read Anniversarry 2013 results
 * @author bnorthan
 *
 */
public class Anniversary2013Reader extends RaceReader
{
	public Anniversary2013Reader(String sourceName)
	{
		super(sourceName);
		
		order.add(ResultHeader.PLACE);
		order.add(ResultHeader.FIRSTNAME);
		order.add(ResultHeader.LASTNAME);
		order.add(ResultHeader.AGE);
		order.add(ResultHeader.SEX);
		order.add(ResultHeader.CITY);
		
		overallExtractor=new OverallFromSizeAndOrder(order);
		resultParser=new Anniversary2013Parser(order);	
		
		results=new ArrayList<Result>();
	}
}
