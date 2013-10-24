package com.truenorth.scoreware.races.readers.hmrrc;

import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.parsers.hmrrc.Voorhesville2013Parser;

import com.truenorth.scoreware.races.readers.TextRaceReader;

import com.truenorth.scoreware.Race;

public class Voorhesville2013Reader extends TextRaceReader
{
	public Voorhesville2013Reader(Race race)
	{
		super(race);
		
		resultParser=new Voorhesville2013Parser();
	}

}
