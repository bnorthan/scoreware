package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.overall.OverallFromSizeAndOrder;
import com.truenorth.scoreware.races.parsers.hmrrc.Anniversary2013Parser;
import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.readers.TextRaceReader;

/**
 * Extends RaceReader to read Anniversarry 2013 results
 * @author bnorthan
 *
 */
public class Anniversary2013Reader extends TextRaceReader
{
	public Anniversary2013Reader(Race race)
	{
		super(race);
		
		overallExtractor=new OverallFromSizeAndOrder();
		resultParser=new Anniversary2013Parser();	
		
		results=new ArrayList<Result>();
	}
}
