package com.truenorth.scoreware.apps;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.ScorewareWriter;
import com.truenorth.scoreware.data.Enums.RacePatterns;
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
import com.truenorth.scoreware.writers.CommandLineWriter;
import com.truenorth.scoreware.writers.FileWriter;
import com.truenorth.scoreware.writers.SqlWriter;
import com.truenorth.scoreware.writers.JDBCSqlWriter;
import com.truenorth.scoreware.sql.RunwareQuery;

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
		
		scheme.Score(race.getResults(), membershipReader.getMembers(), searcher);;
		
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
	
	/*public void initializeDatabase(SqlWriter database)
	{
		if (database!=null)
		{
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
		else
		{
			
		}
	}*/
	
	
}
