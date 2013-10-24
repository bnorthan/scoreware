package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer.Sex;

public class AllignedResultParser extends ResultParser
{
int[] dataLocations; 
	
	public void parseHeader(String headerLine)
	{
		System.out.println(headerLine);
	}
	
	public Result parseResultFromLine(Object line)
	{
		Result result=new Result();
		
		if (this.placeIndex!=-1)
		{
			String place=getSubStringFromIndex((String)line, placeIndex);
			place=place.trim();
			result.setOverallPlace(Integer.parseInt(place));
		}
		if (this.nameIndex!=-1)
		{
			String name=getSubStringFromIndex((String)line, nameIndex);
			
			String[] split=name.split("\\s+");
			
			result.getRacer().setFirstName(split[0]);
			result.getRacer().setLastName(split[split.length-1]);
		}
		if (this.ageIndex!=-1)
		{
			String age=getSubStringFromIndex((String)line, ageIndex);
			
			age=age.trim();
			
			try
			{
				result.getRacer().setAge(Integer.parseInt(age));
			}
			catch(Exception e)
			{
				result.getRacer().setAge(0);
			}
		}
		if (this.genderIndex!=-1)
		{
			String sex=getSubStringFromIndex((String)line, genderIndex);
			
			sex=sex.trim();
			
			if (sex.equals("M"))
			{
				result.getRacer().setSex(Sex.MALE);
			}
			else
			{
				result.getRacer().setSex(Sex.FEMALE);
			}
		}
		if (this.cityIndex!=-1)
		{
			String city=getSubStringFromIndex((String)line, cityIndex);
			
			city=city.trim();
			
			result.getRacer().setCity(city);
		}
		if (this.gunTimeIndex!=-1)
		{
			String gunTime=getSubStringFromIndex((String)line, gunTimeIndex);
			
			gunTime=gunTime.trim();
			
			result.setChipTimeString(gunTime);
		}
		
		return result;
	}
	
	private String getSubStringFromIndex(String string, int index)
	{
		if ( (index!=-1) && (index>0) )
		{
			int start=dataLocations[index-1];
			int end=dataLocations[index]-1;
		
			return string.substring(start, end);
		}
		else if ( index==0)
		{
			int start=0;
			int end=dataLocations[index]-1;
			
			return string.substring(start, end);
		}
		else
		{
			return null;
		}
	}

}
