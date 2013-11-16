package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Enums;
import com.truenorth.scoreware.HeaderStrings;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;

import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.common.utility.ValueComparator;

import java.util.Date;

public class UnknownResultParser extends ResultParser
{
	TreeMap<Enums.ResultHeader, Integer> sorted_map;
	
	public void parseHeader(String headerLine)
	{
		Map<Enums.ResultHeader, Integer> map=new HashMap<Enums.ResultHeader, Integer>();
		
		int test=findHeaderPosition(headerLine, HeaderStrings.getPlaceStrings());	
		map.put(Enums.ResultHeader.PLACE, test);	
		System.out.println("header: place: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getNumberStrings());	
		map.put(Enums.ResultHeader.NUMBER, test);	
		System.out.println("header: number: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getFirstNameStrings());
		map.put(Enums.ResultHeader.FIRSTNAME, test);
		System.out.println("header: fname: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getLastNameStrings());
		map.put(Enums.ResultHeader.LASTNAME, test);	
		System.out.println("header: lname: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getFullNameStrings());
		map.put(Enums.ResultHeader.FULLNAME, test);	
		System.out.println("header: full name: "+test);
	
		test=findHeaderPosition(headerLine, HeaderStrings.getGenderStrings());
		map.put(Enums.ResultHeader.SEX, test);	
		System.out.println("header: gender: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getAgeStrings());
		map.put(Enums.ResultHeader.AGE, test);	
		System.out.println("header: age: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getCityStrings());
		map.put(Enums.ResultHeader.CITY, test);	
		System.out.println("header: city: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getStateStrings());
		map.put(Enums.ResultHeader.STATE, test);	
		System.out.println("header: state: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getGunTimeStrings());
		map.put(Enums.ResultHeader.GUN_TIME, test);	
		System.out.println("header: gtime: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getChipTimeStrings());
		map.put(Enums.ResultHeader.CHIP_TIME, test);	
		System.out.println("header: ctime: "+test);
	
		System.out.println(map);
		
		ValueComparator bvc =  new ValueComparator(map);
        sorted_map = new TreeMap<Enums.ResultHeader, Integer>(bvc);

        System.out.println("unsorted map: "+map);
        sorted_map.putAll(map);
        
        Iterator it=sorted_map.entrySet().iterator();
       
        while (it.hasNext())
        {
        	Map.Entry pairs = (Map.Entry)it.next();
        	
        	if ((Integer)(pairs.getValue())==-1)
        	{
        		it.remove();
        	}
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
        }
      
        System.out.println("results: "+sorted_map);
	
	}
	
	public Result parseResultFromLine(Object line)
	{
		Result result=new Result();
		
		result.setDummyValues();
		
		Iterator it=sorted_map.entrySet().iterator();
	    
		String string=(String)line;
		string=string.trim();
		String split[]=string.split("\\s+");
		
		int expectedSize=sorted_map.size();
		int size=split.length;
		
		int i=0;
		
		Map.Entry pairs=null;
		Enums.ResultHeader rh=null;
		Enums.ResultHeader lastRh=null;
			
		boolean success=true;
		
		try
		{
			while (it.hasNext())
			{
				// if last header was successfully parsed go on to the next one
				if (success)
				{
					lastRh=rh;
					pairs = (Map.Entry)it.next();
					rh=(Enums.ResultHeader)(pairs.getKey());
				}
				        	
				if (rh==Enums.ResultHeader.PLACE)
				{
					result.setOverallPlace(Integer.parseInt(split[i]));
					success=true;
				}
				else if (rh==Enums.ResultHeader.FIRSTNAME)
				{
					result.getRacer().setFirstName(split[i]);
					success=true;
				}
				else if (rh==Enums.ResultHeader.LASTNAME)
				{
					result.getRacer().setLastName(split[i]);
					success=true;
				}
				// if the header indicates full name need to parse first and last
				else if (rh==Enums.ResultHeader.FULLNAME)
				{
					result.getRacer().setFirstName(split[i]);
					i++;
					result.getRacer().setLastName(split[i]);
					success=true;
				}
				else if (rh==Enums.ResultHeader.AGE)
				{
					try
					{
						result.getRacer().setAge(Integer.parseInt(split[i]));
						success=true;
					}
					catch (Exception e)
					{
						success=false;
						System.out.println("age not parsed from: "+line);
					}
				}
				else if (rh==Enums.ResultHeader.SEX)
				{
					if (split[i].equals("M"))
					{
						result.getRacer().setSex(Racer.Sex.MALE);
						success=true;
					}
					else if (split[i].equals("F"))
					{
						result.getRacer().setSex(Racer.Sex.FEMALE);
						success=true;
					}
					else
					{
						System.out.println("sex not parsed from: "+line);
						success=false;
					}
				}
				else if (rh==Enums.ResultHeader.CITY)
				{
					result.getRacer().setCity(split[i]);
					success=true;
				}
				else if (rh==Enums.ResultHeader.STATE)
				{
					if (split[i].length()==2)
					{
						result.getRacer().setState(split[i]);
						success=true;
					}
					else
					{
						System.out.println("state not parsed from: "+line);
						success=false;
					}
				}
				else if (rh==Enums.ResultHeader.GUN_TIME)
				{
					Date date=DateTimeParser.getTime(split[i]);
					
					if (date!=null)
					{
						result.setChipTimeString(split[i]);
						success=true;
					}
					else
					{
						System.out.println("time not parsed from: "+line);
						success=false;
						//result.setChipTimeString("none");
					}
				}
				
				// if it did not succeed it could of been because the name or city was 2 words
				// so check the last header and reparse
				if (success==false)
				{
					if (lastRh==Enums.ResultHeader.LASTNAME)
					{
						String lastName=result.getRacer().getLastName()+" "+split[i];
						result.getRacer().setLastName(lastName);
					}
					else if (lastRh==Enums.ResultHeader.CITY)
					{
						String city=result.getRacer().getCity()+" "+split[i];
						result.getRacer().setCity(city);
					}
				}
				
				i++;
			}
        }
		catch(Exception ex)
		{
			// if we partially parsed it try and recover
			if (i>2)
			{
				return result;
				//return recover(result, split);
			}
			return null;
		}
		
		return result;
	}
	
	private Result recover(Result result, String[] split)
	{
		
		
		return result;
	}
}
