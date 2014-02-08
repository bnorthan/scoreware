package com.truenorth.scoreware.races.readers.hmrrc;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.races.readers.DOMRaceReader;
import com.truenorth.scoreware.races.parsers.hmrrc.HudsonMohawk2013Parser;

public class HudsonMohawk2012Reader extends DOMRaceReader
{
	public HudsonMohawk2012Reader(Race race)
	{
		super(race);
		
		resultParser=new HudsonMohawk2013Parser(); 
	}
	

}
