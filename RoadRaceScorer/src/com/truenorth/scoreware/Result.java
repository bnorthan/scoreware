package com.truenorth.scoreware;

import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.truenorth.scoreware.common.utility.DateTimeParser;

public class Result 
{
	Racer racer;
	
	int overallPlace;
	
	int categoryPlace;
	
	String categoryString;
		
	String pace;
	
	int number;
	
	int points;
	
	String chipTimeString;
	String gunTimeString;
	
	Date chipTime;
	Date gunTime;
	
	ArrayList<Date> splits;
	
	// meta data is used to store misc. tags
	ArrayList<String> metaData;
		
	public Result()
	{
		racer=new Racer();
		
		splits=new ArrayList<Date>();
		metaData=new ArrayList<String>();
		
		categoryString="unknown";
		points=0;
	}
	
	public void setDummyValues()
	{
		chipTimeString="unknown";
		gunTimeString="unknown";
		//chipTime=new Date();
		//gunTime=new Date();
		
		racer.setDummyValues();
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
	
	public void setChipTime(Date chipTime)
	{
		this.chipTime=chipTime;
	}
	
	public Date getChipTime()
	{
		return chipTime;
	}
	
	public void setGunTime(Date gunTime)
	{
		this.gunTime=gunTime;
	}
	
	public Date getGunTime()
	{
		return gunTime;
	}
	
	public void setChipTimeString(String chipTimeString)
	{
		this.chipTimeString=chipTimeString;
		
		chipTime=DateTimeParser.getTime(chipTimeString);
	}
	
	public String getChipTimeString()
	{
		return chipTimeString;
	}
	
	public void setGunTimeString(String gunTimeString)
	{
		this.gunTimeString=gunTimeString;
		
		gunTime=DateTimeParser.getTime(gunTimeString);
	}
	
	public String getGunTimeString()
	{
		return gunTimeString;
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
	
	public ArrayList<Date> getSplits()
	{
		return splits;
	}
	
	public ArrayList<String> getMetaData()
	{
		return metaData;
	}
	
	@Override
	public String toString()
	{	
		DateFormat format=new SimpleDateFormat("H:mm:ss");
		
		String chipTimeString=null;
		try
		{
			chipTimeString=format.format(chipTime);
		}
		catch(Exception e)
		{
		}
		
		String gunTimeString=null;
		try
		{
			gunTimeString=format.format(gunTime);
		}
		catch(Exception e)
		{
		}
	
		String str= overallPlace+" "+racer+" "+categoryPlace+" "+number+" "+chipTimeString
				+" "+gunTimeString+" "+pace+" ";
		
		if (splits.size()>0)
		{
			str+="\nsplits: ";
			for (Date date:splits)
			{
				str+=" "+DateTimeParser.DateToTimeString(date);
			}
		}
		
		if (metaData.size()>0)
		{
			str+="\nmeta data: ";
			for (String s:metaData)
			{
				str+=s.toString();
			}
		}
		
		return str;
	}

	
	
}
