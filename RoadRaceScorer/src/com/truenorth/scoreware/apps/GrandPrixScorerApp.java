package com.truenorth.scoreware.apps;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.membership.readers.MembershipReader;
import com.truenorth.scoreware.membership.readers.MembershipReaderFactory;

import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.scoring.schemes.MaleFemaleScore;

public class GrandPrixScorerApp 
{
	String membershipSourceName;
	String raceSourceName;
	
	MembershipReader membershipReader;
	RaceReader raceReader;
		
	public void setMembershipSourceName(String membershipSourceName)
	{
		this.membershipSourceName=membershipSourceName;
	}
	
	public void setRaceSourceName(String raceSourceName)
	{
		this.raceSourceName=raceSourceName;
	}
	
	public void Score()
	{
		// use the membership source name to get a membership reader
		membershipReader=MembershipReaderFactory.getMembershipReader(membershipSourceName);
		membershipReader.read();
		
		// use the race source name to get a race reader
		raceReader=RaceReaderFactory.getRaceReader(raceSourceName);
		raceReader.read();
		
		// divide into male and female
		MaleFemaleScore sexScore=new MaleFemaleScore();
		
		sexScore.Score(raceReader.getResults());
		
		ArrayList<Result> females=sexScore.getFemales();
		ArrayList<Result> males=sexScore.getMales();
		
		System.out.println("num womens "+females.size());
		System.out.println("num mens "+males.size());
		
		System.out.println("finished");
		
	}

}
