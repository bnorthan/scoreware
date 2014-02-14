package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.data.HeaderStrings;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Racer.Sex;

public class RunScoreResultParser extends ResultParser
{
	int[] dataLocations; 
	
	public void parseHeader(String markerLine, String headerLine)
	{
		System.out.println(headerLine);
		System.out.println(markerLine);
		
		// split the "maxMarkersLine" to find the number and size of headers
		String[] split=markerLine.split(" ");
		
		dataLocations=new int[split.length];
		int i=0;
		int j=0;
		
		for (String s:split)
		{
			
			j+=s.length()+1;
			dataLocations[i]=j;
			System.out.println(s+" "+j+" "+i+" "+dataLocations[i]);
			i++;
		}
		
		j=0;
		i=0;
		for (int n:dataLocations)
		{
			String temp=headerLine.substring(i,n);
			i=n;
			temp=temp.trim();
			System.out.println(temp);
			
			setHeaderIndex(temp, j);
			j++;
		}

	}
	
	public Result parseResultFromLine(Object line)
	{
		Result result=new Result();
		
		try
		{
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
			if (this.stateIndex!=-1)
			{
				String state=getSubStringFromIndex((String)line, stateIndex);
				
				state=state.trim();
				
				result.getRacer().setState(state);
			}
			if (this.gunTimeIndex!=-1)
			{
				String gunTime=getSubStringFromIndex((String)line, gunTimeIndex);
			
				gunTime=gunTime.trim();
			
				result.setGunTimeString(gunTime);
			}
			// if there is a chip time but no gun time use the chip time as the gun time
			if ( (this.chipTimeIndex!=-1) && (this.gunTimeIndex==-1) )
			{
				String gunTime=getSubStringFromIndex((String)line, chipTimeIndex);
			
				gunTime=gunTime.trim();
			
				result.setGunTimeString(gunTime);
			}
		}
		catch(Exception e)
		{
			return null;
		}
		
		return result;
	}
	
	private String getSubStringFromIndex(String string, int index)
	{
		if ( (index!=-1) && (index>0) )
		{
			int start=dataLocations[index-1];
			int end=dataLocations[index]-1;
			
			int size=string.length();
			
			if (end>size-1)
			{
				end=size-1;
			}
		
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
