package com.truenorth.scoreware.apps;

import com.truenorth.scoreware.data.Race;
import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.matchers.MatchSearcher;
import com.truenorth.scoreware.matchers.LevenshteinNameMatcher;
import com.truenorth.scoreware.races.readers.RaceReaderFactory;
import com.truenorth.scoreware.scoring.schemes.Category;
import com.truenorth.scoreware.writers.SqlWriter;
import com.truenorth.scoreware.writers.JDBCSqlWriter;

import java.util.ArrayList;

public class TUResultsApp extends ScoringApp
{
	
	ArrayList<Result> utopians;

	public void Score()
	{
		
		MatchSearcher searcher=new MatchSearcher();
		searcher.setMatcher(new LevenshteinNameMatcher());
		searcher.setMemberString("utopia-usa");
	
		if (isRacerMember!=null)
		{
			searcher.setIsRacerMember(isRacerMember);
		}
		
		utopians=searcher.findMembers(race.getResults(), membershipReader.getMembers(), 100000);
	
		for (Result result:utopians)
		{
			System.out.println(result);
		}
	}
	
	public void saveResults(String fileName)
	{
		
	}
	
	public void updateDatabase(SqlWriter database)
	{
		
	}
}
