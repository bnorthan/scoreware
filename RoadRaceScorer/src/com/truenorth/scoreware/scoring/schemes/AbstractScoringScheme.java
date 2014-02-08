package com.truenorth.scoreware.scoring.schemes;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Racer.Sex;

public abstract class AbstractScoringScheme implements ScoringScheme
{

	protected int numInCategory;	
	protected int[] pointScheme;
	
	protected ArrayList<Category> categories=new ArrayList<Category>();
	
	public void AddAgeGroup(int start, int finish)
	{
		// add male and female for this age
		Category male=new Category(start, finish, Sex.MALE);
		Category female=new Category(start, finish, Sex.FEMALE);
		
		categories.add(male);
		categories.add(female);
	}
	
}
