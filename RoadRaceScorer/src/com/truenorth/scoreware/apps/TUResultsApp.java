package com.truenorth.scoreware.apps;

import com.truenorth.scoreware.matchers.MatchSearcher;
import com.truenorth.scoreware.matchers.LevenshteinNameMatcher;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.scoring.schemes.Category;
import com.truenorth.scoreware.writers.JDBCSqlWriter;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Race;

import java.util.ArrayList;

public class TUResultsApp extends ScoringApp
{
	
	ArrayList<Result> utopians;

	public void Score()
	{
		
		MatchSearcher searcher=new MatchSearcher();
		searcher.setMatcher(new LevenshteinNameMatcher());
	
		if (isRacerMember!=null)
		{
			searcher.setIsRacerMember(isRacerMember);
		}
		
		utopians=searcher.findMembers(raceReader.getResults(), membershipReader.getMembers(), 100000);
	
		for (Result result:utopians)
		{
			System.out.println(result);
		}
	}
	
	public void saveResults(String fileName)
	{
		
	}
	
	public void writeToDatabase()
	{
		System.out.println(race);
		
		JDBCSqlWriter database=new JDBCSqlWriter();
		
		database.CreateRacesTable("races");
		database.CreateRaceTable(race);
		
		for (Result res:utopians)
		{
			database.writeResult(res);
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
		raceReader.setHeaderInfo("sefcu_2013", "SEFCU 5k", "Albany", "NY", "USA", "2013-09-02");
		
		race=raceReader.getRace();
		
		for (Result result:raceReader.getResults())
		{
			System.out.println(result);
		}
		
		System.out.println(race);
	}
}
