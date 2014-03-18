package com.truenorth.scoreware.races.readers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
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
		results=race.getResults();
		//results=new ArrayList<Result>();
	}
	
	// Race class contains race meta data
	protected Race race;

	// an array containing list of results
	protected ArrayList<Result> results;
	
	// parser used to read a result
	protected ResultParser resultParser;
	
	/*public ArrayList<Result> getResults()
	{
		return results;
	}*/
	
	public Race getRace()
	{
		return race;
	}
	
	// explicitly set the header info.  This is used for the case where the header cannot be read for some reason
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
	
	public boolean runChecks()
	{
		int numRacers=results.size();
		System.out.println("Number: "+numRacers);
		
		int errors=0;
				
		if (numRacers>0)
		{
			Result first=results.get(0);
			int firstRacer=results.get(0).getOverallPlace();
			int lastRacer=results.get(numRacers-1).getOverallPlace();
		
			System.out.println("first: "+firstRacer+" last: "+lastRacer);
			
			int i=1;
			int j=0;
			
			Date lTime=null;
			//if (lastRacer!=numRacers)
			{
				for (Result result:results)
				{
					int place = result.getOverallPlace();
					
					if (place!=i)
					{
						System.out.println("Problem with place near: "+result);
						
						i=place+1;
						errors++;
					}
					else
					{
						i++;
					}
					
					Date time=result.getGunTime();
					
					if (j>0)
					{
						if (time!=null && lTime!=null)
						{	
							// if new time is before old time something went wrong
							if (time.before(lTime))
							{
								System.out.println("time order problem near: "+result);
							}
						}
						else
						{
							System.out.println("time null near: "+result);
							errors++;
						}
					}
					
					lTime=time;
					
					j++;
				}	
			}
		}
	
		float errorsPercentage = (float)errors/(float)numRacers;
		
		// abrtrary but return a fail if error rate is above 10%
		// (perhaps it sould be 1%)
		if (errorsPercentage>.10)
		{
			return false;
		}
		
		return true;
		
	}
	
	// read the header
	abstract public void ReadRaceHeader();
	
	abstract public void read();
}