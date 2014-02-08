package com.truenorth.scoreware.scoring.schemes;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Result;

/**
 * Extracts all Racers of an age group from a larger list of results
 * @author bnorthan
 *
 */
public class AgeGroupScorer implements ScoringScheme
{
	public static ArrayList<Result> ScoreAge(ArrayList<Result> results, int start, int finish)
	{
		AgeGroup ageGroup=new AgeGroup(start, finish);
		AgeGroupScorer scorer=new AgeGroupScorer(ageGroup);
		
		scorer.Score(results);
		
		return scorer.getAgeResults();
	} 
	
	ArrayList<Result> ageResults;
	
	AgeGroup ageGroup;
	public AgeGroupScorer(AgeGroup ageGroup)
	{
		this.ageGroup=ageGroup;
		ageResults=new ArrayList<Result>();
	}
	
	public void Score(ArrayList<Result> results)
	{
		for (Result result:results)
		{
			if ( (ageGroup.getStart()<=result.getRacer().getAge())
				&& (result.getRacer().getAge()<=ageGroup.getFinish()) )
			{
				ageResults.add(result);
			}
		}
	}
	
	public ArrayList<Result> getAgeResults()
	{
		return ageResults;
	}
}
