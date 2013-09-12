package com.truenorth.scoreware.scoring.schemes;

import java.util.ArrayList;
import com.truenorth.scoreware.Result;

import com.truenorth.scoreware.Racer.Sex;

public class MaleFemaleScore implements ScoringScheme
{
	ArrayList<Result> females;
	ArrayList<Result> males;
	
	public MaleFemaleScore()
	{
		females=new ArrayList<Result>();
		males=new ArrayList<Result>();
	}
	public void Score(ArrayList<Result> results)
	{
		for (Result result:results)
		{
			if (result.getRacer().getSex()==Sex.FEMALE)
			{
				females.add(result);
			}
			else if (result.getRacer().getSex()==Sex.MALE)
			{
				males.add(result);
			}
		}
	}
	
	public ArrayList<Result> getFemales()
	{
		return females;
	}
	
	public ArrayList<Result> getMales()
	{
		return males;
	}
}
