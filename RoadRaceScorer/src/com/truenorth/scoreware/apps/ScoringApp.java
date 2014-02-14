package com.truenorth.scoreware.apps;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Enums.RacePatterns;
import com.truenorth.scoreware.matchers.IsRacerMember;
import com.truenorth.scoreware.membership.readers.MembershipReader;
import com.truenorth.scoreware.membership.readers.MembershipReaderFactory;
import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.sql.RunwareQuery;
import com.truenorth.scoreware.writers.SqlWriter;
import com.truenorth.scoreware.writers.JDBCSqlWriter;

import java.io.*;

/**
 * Abstract base class for a scoring app
 * @author bnorthan
 *
 */
abstract public class ScoringApp 
{
	// class that reads the membership data
	MembershipReader membershipReader;
	
	// class that reads the race data
	RaceReader raceReader;
	
	RacePatterns racePattern;
	
	IsRacerMember isRacerMember=null;
	
	Race race;
	
	File workingDirectory;
	
	JDBCSqlWriter databaseWriter;
	
	public ScoringApp()
	{
		race=new Race();
	}
	
	public void setIsRacerMember(IsRacerMember isRacerMember)
	{
		this.isRacerMember=isRacerMember;
	}
	
	public void setRacePattern(RacePatterns racePattern)
	{
		this.racePattern=racePattern;
	}
	
	public void ReadMembership(String membershipSourceName)
	{
		// use the membership source name to get a membership reader
		membershipReader=MembershipReaderFactory.getMembershipReader(membershipSourceName);
		
		if (membershipReader!=null)
		{
			membershipReader.read();
		}
		else
		{
			System.out.println("no membership reader available for this type of file.");
		}
	}
	
	public void ReadRace(String raceSourceName)
	{
		System.out.println("Race Pattern: "+racePattern);
		
		race.setSourceName(raceSourceName);
		// use the race source name to get a race reader
		raceReader=RaceReaderFactory.getRaceReader(race, racePattern);
		
		System.out.println("Race Reader: "+raceReader.getClass().toString());
		
		raceReader.read();
				
		race=raceReader.getRace();
		
		for (Result result:race.getResults())
		{
			System.out.println(result);
		}
		
		raceReader.runChecks();
	}
	
	public void setRaceInfo(String id,
			String name,
			String date,
			String city,
			String state,
			String country,
			String timedBy)
	{
		race.setIdentifier(id);
		race.setName(name);
		
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date theDate=new Date();
		
		try
		{
			theDate=format.parse(date);
			race.setDate(theDate);
		}
		catch(Exception e)
		{
			System.out.println("can't format string");
			race.setDate(new Date());
		}
		//race.setDate(date);
		race.setCity(city);
		race.setState(state);
		race.setCountry(country);
		race.setTimedBy(timedBy);
	}
	
	public void initializeDatabase()
	{
		if (databaseWriter==null)
		{
			databaseWriter=new JDBCSqlWriter("D:\\Brian2012\\hmrrc\\data\\2014\\Database\\Scoreware3.properties"); 
		}
		
		System.out.println(race);
		
		databaseWriter.CreateRacesTable("races");
		databaseWriter.CreateRaceTable(race);
		
		writeResults();
	}
	
	public void writeResults()
	{
		if (databaseWriter!=null)
		{
			databaseWriter.writeResults2(race.getResults());
		}
	}
	
	public void transferToDatabase(String propertiesFile)
	{
		SqlWriter writer=new JDBCSqlWriter(propertiesFile);
		
		writer.setPropertiesFile(propertiesFile);
		
		writer.CreateRacesTable("races");
		writer.CreateRaceTable(race);	
		writer.writeResults2(race.getResults());
	}
	
	public void updateDatabase()
	{
		if (databaseWriter==null)
		{
			databaseWriter=new JDBCSqlWriter("D:\\Brian2012\\hmrrc\\data\\2014\\Database\\Scoreware3.properties");
		}
		
		String test=race.getIdentifier();
		
		databaseWriter.setTableName(race.getIdentifier());
		RunwareQuery queryClass = new RunwareQuery()
		{
			public String makeResultQuery(Result result, String tableName)
			{
				return "UPDATE "+tableName+" SET Points='"+result.getPoints()
						+"', Category='"+result.getCategoryString()
						+"', Club='"+result.getRacer().getCurrentClub()+"' WHERE Place='"
							+result.getOverallPlace()+"' AND LastName='"+result.getRacer().getLastName()+"'";
			}
			
		};
		
		databaseWriter.RunQueriesOnResults(race.getResults(), queryClass);
	}
	
	abstract public void Score();  
	
	abstract public void saveResults(String fileName);
	
	public void setWorkingDirectory(File workingDirectory)
	{
		this.workingDirectory=workingDirectory;
		race.setOutputPath(workingDirectory.getAbsolutePath());
	}
	
	public Race getRace()
	{
		return this.race;
	}
	
}
