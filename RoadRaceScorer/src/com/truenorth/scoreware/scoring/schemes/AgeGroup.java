package com.truenorth.scoreware.scoring.schemes;

import java.util.ArrayList;

import com.truenorth.scoreware.Racer;

/**
 * represents an age group
 * @author bnorthan
 *
 */
public class AgeGroup 
{
	int start;
	int finish;
	
	public AgeGroup(int start, int finish)
	{
		this.start=start;
		this.finish=finish;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public int getFinish()
	{
		return finish;
	}
	
	@Override
	public String toString()
	{
		return start + " to "+finish;
	}
}
