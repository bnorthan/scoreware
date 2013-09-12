package com.truenorth.scoreware.scoring.schemes;

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

}
