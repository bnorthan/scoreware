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
import com.truenorth.scoreware.Enums.RacePatterns;

public class GrandPrixScorerApp extends ScoringApp
{
	GrandPrixScoringScheme scheme;
	
	public void Score()
	{
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
	
	public void writeToDatabase()
	{
		
		JDBCSqlWriter database=new JDBCSqlWriter();
		
		database.CreateRacesTable("gp_races");
		database.CreateRaceTable(race);
		
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
	
	@Override
	public void ReadRace(String raceSourceName)
	{
		System.out.println("Race Pattern: "+racePattern);
		
		race.setSourceName(raceSourceName);
		// use the race source name to get a race reader
		raceReader=RaceReaderFactory.getRaceReader(race, racePattern);
		
		System.out.println("Race Reader: "+raceReader.getClass().toString());
		
		raceReader.read();
		//raceReader.setHeaderInfo("mhrHalfm_2013", "Hudson Mohawk Half Marathon", "Albany", "NY", "USA", "2013-10-13");
		//raceReader.setHeaderInfo("mhrm_2013", "Hudson Mohawk Marathon", "Albany", "NY", "USA", "2013-10-13");
		//raceReader.setHeaderInfo("sefcu_2013", "SEFCU 5k", "Albany", "NY", "USA", "2013-09-02");
		raceReader.setHeaderInfo("mhrm_gp_2013", "Hudson Mohawk Marathon", "Albany", "NY", "USA", "2013-10-13");
		
		race=raceReader.getRace();
		
		for (Result result:raceReader.getResults())
		{
			System.out.println(result);
		}
		
		System.out.println(race);
	}
}
