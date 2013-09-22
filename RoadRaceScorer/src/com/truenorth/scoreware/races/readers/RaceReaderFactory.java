package com.truenorth.scoreware.races.readers;

import com.truenorth.scoreware.races.readers.hmrrc.*;

public class RaceReaderFactory 
{
	static public RaceReader getRaceReader(String raceSource)
	{
		return new Sefcu2013Reader(raceSource);
	}
}
