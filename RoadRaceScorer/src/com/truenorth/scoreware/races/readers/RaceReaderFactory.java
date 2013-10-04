package com.truenorth.scoreware.races.readers;

import com.truenorth.scoreware.races.readers.hmrrc.*;
import com.truenorth.scoreware.Enums.HmrrcRaces;

public class RaceReaderFactory 
{
	static public RaceReader getRaceReader(String raceSource)
	{
		return new Sefcu2013Reader(raceSource);
		//return new Voorhesville2013Reader(raceSource);
	}
	
	static public RaceReader getRaceReader(String raceSource, HmrrcRaces racePattern)
	{
		System.out.println(racePattern);
		if (racePattern.equals(HmrrcRaces.SEFCU2013))
		{
			return new Sefcu2013Reader(raceSource);
		}
		else if (racePattern.equals(HmrrcRaces.ANNIVERSARY2013))
		{
			return new Anniversary2013Reader(raceSource);
		}
		else if (racePattern.equals(HmrrcRaces.VOORHESVILLE2013))
		{
			return new Voorhesville2013Reader(raceSource);
		}
		else if (racePattern.equals(HmrrcRaces.HUDSONMOHAWK2012))
		{
			return new HudsonMohawk2012Reader(raceSource);
		}
		
		System.out.println("NOTHING!");
		
		return null;
		//return new Voorhesville2013Reader(raceSource);
	}
}
