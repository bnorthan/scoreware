package com.truenorth.scoreware.scoring.schemes.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.scoring.schemes.AgeGroupScorer;
import com.truenorth.scoreware.scoring.schemes.MaleFemaleScore;
import com.truenorth.scoreware.scoring.schemes.ScoringScheme;
import com.truenorth.scoreware.scoring.schemes.Category;
import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Racer.Sex;
import com.truenorth.scoreware.matchers.MatchSearcher;
import com.truenorth.scoreware.scoring.schemes.AbstractScoringScheme;

public class GrandPrixScoringScheme extends AbstractScoringScheme
{
	
	ArrayList<Racer> members=null;
	MatchSearcher matchSearcher=null;
	
	public GrandPrixScoringScheme()
	{
		numInCategory=10;	
		pointScheme= new int[]{12,10,8,7,6,5,4,3,2,1};
	}
	
	
	public void Score(ArrayList<Result> results, ArrayList<Racer> members,MatchSearcher matchSearcher)
	{
		this.members=members;
		this.matchSearcher=matchSearcher;
		Score(results);
	}
	
	public void Score(ArrayList<Result> results)
	{
		System.out.println("Grand Prix Scoring:");
		
		// divide into male and female
		MaleFemaleScore sexScore=new MaleFemaleScore();
		
		sexScore.Score(results);
		
		ArrayList<Result> females=sexScore.getFemales();
		ArrayList<Result> males=sexScore.getMales();
		
		for (Category category:categories)
		{
			if (category.getSex()==Sex.MALE)
			{
				category.setResults(AgeGroupScorer.ScoreAge(males, category.getAgeGroup().getStart(), category.getAgeGroup().getFinish()));
			}
			else if (category.getSex()==Sex.FEMALE)
			{
				category.setResults(AgeGroupScorer.ScoreAge(females, category.getAgeGroup().getStart(), category.getAgeGroup().getFinish()));
			}
		}
		
		if ( (matchSearcher!=null)&&(members!=null))
		{
			for (Category category:categories)
			{
				category.setResults(matchSearcher.findMembers(category.getResults(), members, 10));
				
				int place=0;
				
				for (Result result:category.getResults())
				{
					result.setPoints(pointScheme[place]);
					result.setCategoryString(category.getCategoryString());
					place++;
				}
			}
		}
	}
	
	public ArrayList<Category> getCategories()
	{
		return categories;
	}
	
	public void setMatchSearcher(MatchSearcher matchSearcher)
	{
		this.matchSearcher=matchSearcher;
	}
	
	public void setMembers(ArrayList<Racer> members)
	{
		this.members=members;
	}
	
	public int[] getPointScheme()
	{
		return pointScheme;
	}
	
	public int getNumInCategory()
	{
		return numInCategory;
	}
}

