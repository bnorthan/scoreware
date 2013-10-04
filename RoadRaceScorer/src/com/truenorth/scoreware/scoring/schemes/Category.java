package com.truenorth.scoreware.scoring.schemes;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer.Sex;

public class Category 
{
	AgeGroup ageGroup;
	Sex sex;
	
	String categoryString;
	
	ArrayList<Result> racers;
	
	public Category(AgeGroup ageGroup, Sex sex)
	{
		this.ageGroup=ageGroup;
		this.sex=sex;
		this.categoryString=""+sex+ageGroup.getStart()+"_"+ageGroup.getFinish();
	}
	
	public Category(int start, int finish, Sex sex)
	{
		ageGroup=new AgeGroup(start, finish);
		this.sex=sex;
		this.categoryString=""+sex+" "+start+"-"+finish;
	}
	
	public AgeGroup getAgeGroup()
	{
		return ageGroup;
	}
	
	public Sex getSex()
	{
		return sex;
	}
	
	public String getCategoryString()
	{
		return categoryString;
	}
	
	public void setResults(ArrayList<Result> racers)
	{
		this.racers=racers;
	}
	
	public ArrayList<Result> getResults()
	{
		return racers;
	}
}
