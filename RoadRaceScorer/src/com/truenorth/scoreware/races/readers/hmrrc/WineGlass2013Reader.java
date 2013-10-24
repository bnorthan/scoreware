package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.extractors.overall.SimpleGetOverallResults;
import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.races.readers.UnknownTextReader;
import com.truenorth.scoreware.races.parsers.hmrrc.WineGlass2013Parser;

public class WineGlass2013Reader extends UnknownTextReader
{
	public WineGlass2013Reader(Race race)
	{
		super(race);
		
		throwAwayLines=new ArrayList<String>();
		
		throwAwayLines.add("Corning Wine Glass");
		throwAwayLines.add(" Corning Wine Glass");
		
		throwAwayLines.add("Marathon Splits");
		
		overallExtractor=new SimpleGetOverallResults();
		
		resultParser= new WineGlass2013Parser();
		
	}

}
