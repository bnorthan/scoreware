package com.truenorth.scoreware.apps;

import java.util.ArrayList;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.matchers.IsRacerMember;
import com.truenorth.scoreware.matchers.MatchSearcher;
import com.truenorth.scoreware.membership.readers.MembershipReader;
import com.truenorth.scoreware.membership.readers.MembershipReaderFactory;

import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.scoring.schemes.AgeGroupScorer;
import com.truenorth.scoreware.scoring.schemes.MaleFemaleScore;

import com.truenorth.scoreware.scoring.schemes.Category;
import com.truenorth.scoreware.scoring.schemes.hmrrc.GrandPrixScoringScheme;

import com.truenorth.scoreware.ScorewareWriter;
import com.truenorth.scoreware.writers.CommandLineWriter;
import com.truenorth.scoreware.writers.FileWriter;
import com.truenorth.scoreware.writers.JDBCSqlWriter;

import com.truenorth.scoreware.Enums.HmrrcRaces;

public class GrandPrixScorerApp 
{
	String membershipSourceName;
	String raceSourceName;
	
	MembershipReader membershipReader;
	RaceReader raceReader;
	GrandPrixScoringScheme scheme;
	
	IsRacerMember isRacerMember=null;
	
	HmrrcRaces racePattern;
		
	public void setMembershipSourceName(String membershipSourceName)
	{
		this.membershipSourceName=membershipSourceName;
	}
	
	public void setRaceSourceName(String raceSourceName)
	{
		this.raceSourceName=raceSourceName;
	}
	
	public void setIsRacerMember(IsRacerMember isRacerMember)
	{
		this.isRacerMember=isRacerMember;
	}
	
	public void ReadMembership()
	{
		// use the membership source name to get a membership reader
		membershipReader=MembershipReaderFactory.getMembershipReader(membershipSourceName);
		membershipReader.read();			
	}
	
	public void Score()
	{
		//ReadMembership();
		
		System.out.println("Race Pattern: "+racePattern);
		
		// use the race source name to get a race reader
		raceReader=RaceReaderFactory.getRaceReader(raceSourceName, racePattern);
		
		System.out.println("Race Reader: "+raceReader.getClass().toString());
		
		raceReader.read();
			
		scheme=new GrandPrixScoringScheme();
		
		scheme.AddAgeGroup(1, 29);
		scheme.AddAgeGroup(30, 39);
		scheme.AddAgeGroup(40, 49);
		scheme.AddAgeGroup(50, 59);
		scheme.AddAgeGroup(60, 69);
		scheme.AddAgeGroup(70, 99);
		
		MatchSearcher searcher=new MatchSearcher();
		
		if (isRacerMember!=null)
		{
			searcher.setIsRacerMember(isRacerMember);
		}
		
		scheme.Score(raceReader.getResults(), membershipReader.getMembers(), searcher);;
		
		CommandLineWriter writer=new CommandLineWriter("");
		
		WriteGrandPrixResults(writer);
	}
	
	void WriteGrandPrixResults(ScorewareWriter writer)
	{
		for (Category category: scheme.getCategories())
		{
			writer.writeLine("");
			writer.writeLine(category.getSex()+" Age: "+category.getAgeGroup());
			
			int n=0;
			
			printLoop:
			for (Result result: category.getResults())
			{
				writer.writeLine((n+1)+". "+result.getRacer().getFirstName()+" "
						+result.getRacer().getLastName()+" "+result.getCategoryString()
						+" "+result.getPoints());
								
				n++;
				
				if (n==scheme.getNumInCategory())
				{
					break printLoop;
				}
			}
		}	
	}
	
	public void saveResults(String fileName)
	{
		FileWriter writer=new FileWriter(fileName);
		WriteGrandPrixResults(writer);
	}
	
	public void setRacePattern(HmrrcRaces racePattern)
	{
		this.racePattern=racePattern;
	}
	
	public void writeToDatabase()
	{
		JDBCSqlWriter database=new JDBCSqlWriter("sefcu");
		
		//// TEST
		
		int max=scheme.getCategories().size();
		
		for (int i=0;i<max;i++)
		{
			Category testCategory=scheme.getCategories().get(i);
			ArrayList<Result> testResult=testCategory.getResults();
		
			for (Result res:testResult)
			{
				database.writeResult(res);
			}
		}
	}
}
