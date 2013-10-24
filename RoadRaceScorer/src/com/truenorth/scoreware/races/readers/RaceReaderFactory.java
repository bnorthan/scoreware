package com.truenorth.scoreware.races.readers;

import com.truenorth.scoreware.races.readers.hmrrc.*;
import com.truenorth.scoreware.Enums.RacePatterns;
import com.truenorth.scoreware.Race;

/**
 * 
 * Used to construct and return an appropriate race reader
 * 
 * @author bnorthan
 *
 */
public class RaceReaderFactory 
{
	
	static public RaceReader getRaceReader(Race race, RacePatterns racePattern)
	{
		System.out.println(racePattern);
		
		if (racePattern.equals(RacePatterns.UNKNOWN))
		{
			return new UnknownTextReader(race);
		}
		
		// if run score return the run score reader
		
		if (racePattern.equals(RacePatterns.RUNSCORE))
		{
			return new RunScoreTextReader(race);
		}
		
		// if a specific known race return that reader
		
		if (racePattern.equals(RacePatterns.SEFCU2013))
		{
			return new Sefcu2013Reader(race);
		}
		else if (racePattern.equals(RacePatterns.ANNIVERSARY2013))
		{
			return new Anniversary2013Reader(race);
		}
		else if (racePattern.equals(RacePatterns.VOORHESVILLE2013))
		{
			return new Voorhesville2013Reader(race);
		}
		else if (racePattern.equals(RacePatterns.HUDSONMOHAWK2012))
		{
			return new HudsonMohawk2012Reader(race);
		}
		
		// if pdf (no known pattern)
		if (racePattern.equals(RacePatterns.PDF_TEXT))
		{
			// return something that extracts text from pdf
			return new UnknownTextReader(race);
		}
		
		if (racePattern.equals(RacePatterns.WINEGLASS2013))
		{
			// return something that extracts text from pdf
			return new WineGlass2013Reader(race);
		}
		
		
		System.out.println("NOTHING!");
		
		return null;
	}
	
	static public RaceReader getRaceReader(Race race)
	{
		return new Sefcu2013Reader(race);
		//return new Voorhesville2013Reader(raceSource);
	}

}
