package com.truenorth.scoreware.apps;

import com.truenorth.scoreware.matchers.MatchSearcher;
import com.truenorth.scoreware.matchers.LevenshteinNameMatcher;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.scoring.schemes.Category;
import com.truenorth.scoreware.writers.JDBCSqlWriter;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer;

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
		JDBCSqlWriter database=new JDBCSqlWriter("sefcu_utop");
		
		for (Result res:utopians)
		{
			database.writeResult(res);
		}
	}
	

}
