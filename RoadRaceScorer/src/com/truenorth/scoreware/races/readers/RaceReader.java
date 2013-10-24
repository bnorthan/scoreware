package com.truenorth.scoreware.races.readers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.races.parsers.ResultParser;

/**
 * Extends Reader to implement a RaceReader.  RaceReader reads
 * race results
 * @author bnorthan
 *
 */
public abstract class RaceReader 
{
	public RaceReader(Race race)
	{
		this.race=race;
		results=new ArrayList<Result>();
	}
	
	// Race class contains race meta data
	protected Race race;

	// an array containing list of results
	protected ArrayList<Result> results;
	
	// parser used to read a result
	protected ResultParser resultParser;
	
	public ArrayList<Result> getResults()
	{
		return results;
	}
	
	public Race getRace()
	{
		return race;
	}
	
	// manually read the header info.  This is used for the case where the header cannot be read for some reason
	public void setHeaderInfo(String id, String name, String city, String state, String country, String dateString)
	{
		race.setName(name);
		race.setCity(city);
		
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		Date date=new Date();
		
		try
		{
			date=format.parse(dateString);
			race.setDate(date);
		}
		catch(Exception e)
		{
			System.out.println("can't format string");
			race.setDate(new Date());
		}
		
		race.setState(state);
		race.setCountry(country);
		race.setIdentifier(id);
	}
	
	// read the header
	abstract public void ReadRaceHeader();
	
	abstract public void read();
}