package com.truenorth.scoreware;

import java.util.Date;
import java.util.ArrayList;

public class Race 
{
	String sourceName=null;
	String outputName=null;
	String outputPath=null;
	
	String name=null;
	Date date=null;
	String city=null;
	String state=null;
	String country=null;
	String timedBy=null;
	
	ArrayList<Result> results;
	
	String identifier;
	
	public Race()
	{
		results=new ArrayList<Result>();
	}
	
	public void setSourceName(String sourceName)
	{
		this.sourceName=sourceName;
	}
	
	public String getSourceName()
	{
		return sourceName;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setDate(Date date)
	{
		this.date=date;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setCity(String city)
	{
		this.city=city;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setState(String state)
	{
		this.state=state;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setCountry(String country)
	{
		this.country=country;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setTimedBy(String timedBy)
	{
		this.timedBy=timedBy;
	}
	
	public String getTimedBy()
	{
		return this.timedBy;
	}
	
	public void setIdentifier(String identifier)
	{
		this.identifier=identifier;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public void setOutputPath(String outputPath)
	{
		this.outputPath=outputPath;
	}
	
	public String getOutputPath()
	{
		return this.outputPath;
	}
	
	public ArrayList<Result> getResults()
	{
		return results;
	}
	
	@Override
	public String toString()
	{
		return identifier+" "+name+" "+date.toString()+" "+city+" "+state+" "+country;
	}
}
