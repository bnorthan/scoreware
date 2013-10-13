package com.truenorth.scoreware.apps;

import com.truenorth.scoreware.Enums.RacePatterns;
import com.truenorth.scoreware.matchers.IsRacerMember;
import com.truenorth.scoreware.membership.readers.MembershipReader;
import com.truenorth.scoreware.membership.readers.MembershipReaderFactory;
import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;

import com.truenorth.scoreware.Result;

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
		
		// use the race source name to get a race reader
		raceReader=RaceReaderFactory.getRaceReader(raceSourceName, racePattern);
		
		System.out.println("Race Reader: "+raceReader.getClass().toString());
		
		raceReader.read();
		
		for (Result result:raceReader.getResults())
		{
			System.out.println(result);
		}
	}
	
	abstract public void Score();
	
	abstract public void saveResults(String fileName);
	
	abstract public void writeToDatabase();
}