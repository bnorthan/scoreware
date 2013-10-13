package com.truenorth.scoreware.races.readers;

import com.truenorth.scoreware.races.readers.hmrrc.*;
import com.truenorth.scoreware.Enums.RacePatterns;

public class RaceReaderFactory 
{
	static public RaceReader getRaceReader(String raceSource)
	{
		return new Sefcu2013Reader(raceSource);
		//return new Voorhesville2013Reader(raceSource);
	}
	
	static public RaceReader getRaceReader(String raceSource, RacePatterns racePattern)
	{
		System.out.println(racePattern);
		if (racePattern.equals(RacePatterns.SEFCU2013))
		{
			return new Sefcu2013Reader(raceSource);
		}
		else if (racePattern.equals(RacePatterns.ANNIVERSARY2013))
		{
			return new Anniversary2013Reader(raceSource);
		}
		else if (racePattern.equals(RacePatterns.VOORHESVILLE2013))
		{
			return new Voorhesville2013Reader(raceSource);
		}
		else if (racePattern.equals(RacePatterns.HUDSONMOHAWK2012))
		{
			return new HudsonMohawk2012Reader(raceSource);
		}
		
		System.out.println("NOTHING!");
		
		return null;
	}
}
