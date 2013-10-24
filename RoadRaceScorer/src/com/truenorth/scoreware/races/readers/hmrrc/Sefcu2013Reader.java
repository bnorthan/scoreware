package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.overall.OverallFromSizeAndOrder;
import com.truenorth.scoreware.races.parsers.hmrrc.Sefcu2013Parser;
import com.truenorth.scoreware.races.parsers.RunScoreResultParser;
import com.truenorth.scoreware.races.readers.RaceReader;

import com.truenorth.scoreware.races.readers.RunScoreTextReader;

import com.truenorth.scoreware.Race;

/**
 * Extends RaceReader to read Sefcu 2013 results
 * @author bnorthan
 *
 */
public class Sefcu2013Reader extends RunScoreTextReader
{
	public Sefcu2013Reader(Race race)
	{
		super(race);
		
		overallExtractor=new OverallFromSizeAndOrder();
		//resultParser=new Sefcu2013Parser(order);	
		resultParser=new RunScoreResultParser();
		
		extractor.setKeepSpaces(true);
	}
}
