package com.truenorth.scoreware;

import java.util.Date;

public class Result 
{
	Racer racer;
	int overallPlace;
	int categoryPlace;
	
	String time;
	String pace;
	
	int number;
	
	public Result()
	{
		racer=new Racer();
	}
	
	public void setOverallPlace(int overallPlace)
	{
		this.overallPlace=overallPlace;
	}
	public int getOverallPlace()
	{
		return overallPlace;
	}
	
	public void setCategoryPlace(int categoryPlace)
	{
		this.categoryPlace=categoryPlace;
	}
	public int getCategoryPlace()
	{
		return categoryPlace;
	}
	
	public void setNumber(int number)
	{
		this.number=number;
	}
	public int getNumber()
	{
		return(number);
	}
	
	public Racer getRacer()
	{
		return racer;
	}
	
	@Override
	public String toString()
	{
		
		String str= overallPlace+" "+racer+" "+categoryPlace+" "+number+" "+time+" "+pace+" ";
		
		return str;
	}

	
	
}
