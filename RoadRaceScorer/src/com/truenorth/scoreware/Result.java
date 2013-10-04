package com.truenorth.scoreware;

import java.util.Date;

public class Result 
{
	Racer racer;
	int overallPlace;
	int categoryPlace;
	
	String categoryString;
	
	String time;
	String pace;
	
	int number;
	
	int points;
	
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
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points=points;
	}
	
	public void setCategoryString(String categoryString)
	{
		this.categoryString=categoryString;
	}
	
	public String getCategoryString()
	{
		return categoryString;
	}
	
	@Override
	public String toString()
	{
		
		String str= overallPlace+" "+racer+" "+categoryPlace+" "+number+" "+time+" "+pace+" ";
		
		return str;
	}

	
	
}
