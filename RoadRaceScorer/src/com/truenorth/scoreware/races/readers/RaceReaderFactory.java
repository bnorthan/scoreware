package com.truenorth.scoreware.races.readers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

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
		
		// if reading from a database
		if (racePattern.equals(RacePatterns.PROPERTIES))
		{
			return getRaceReaderFromProperties(race);
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
		
		if (racePattern.equals(RacePatterns.TABLE))
		{
			return new DOMRaceReader(race);
		}
		
		
		System.out.println("NOTHING!");
		
		return null;
	}
	
	static public RaceReader getRaceReaderFromProperties(Race race)
	{
		// the source should be a properties file telling us how to read the race
		Properties props=new Properties();
				
		try 
		{
			InputStream in=Files.newInputStream(Paths.get(race.getSourceName()));
					
			props.load(in);
		}
		catch(Exception e)
		{
					
		}
		
		String drivers=props.getProperty("jdbc.drivers");
		
		// if the sql drivers are there then the results are allready in a database return an SqlReader
		if (drivers!=null)
		{
			return new SqlRaceReader(race);
		}
		
		return null;
	}
	
	static public RaceReader getRaceReader(Race race)
	{
		return new Sefcu2013Reader(race);
		//return new Voorhesville2013Reader(raceSource);
	}

}
