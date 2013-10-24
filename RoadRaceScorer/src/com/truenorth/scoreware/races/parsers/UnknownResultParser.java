package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Enums;
import com.truenorth.scoreware.HeaderStrings;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;

import com.truenorth.scoreware.common.utility.ValueComparator;

public class UnknownResultParser extends ResultParser
{
	TreeMap<Enums.ResultHeader, Integer> sorted_map;
	
	public void parseHeader(String headerLine)
	{
		Map<Enums.ResultHeader, Integer> map=new HashMap<Enums.ResultHeader, Integer>();
		
		int test=findHeaderPosition(headerLine, HeaderStrings.getPlaceStrings());	
		map.put(Enums.ResultHeader.PLACE, test);	
		System.out.println("header: place: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getFirstNameStrings());
		map.put(Enums.ResultHeader.FIRSTNAME, test);
		System.out.println("header: fname: "+test);
		
		test=findHeaderPosition(headerLine, HeaderStrings.getLastNameStrings());
		map.put(Enums.ResultHeader.LASTNAME, test);	
		System.out.println("header: lname: "+test);
		
		//test=findHeaderPosition(headerLine, HeaderStrings.getFullNameStrings());
		//map.put(Enums.ResultHeader.FIRSTNAME, test);	
		//System.out.println("header: full name: "+test);
		
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
		
		Iterator it=sorted_map.entrySet().iterator();
	    
		String string=(String)line;
		string=string.trim();
		String split[]=string.split("\\s+");
		
		int i=0;
		
		try
		{
			while (it.hasNext())
			{
				Map.Entry pairs = (Map.Entry)it.next();
        	
				Enums.ResultHeader rh=(Enums.ResultHeader)(pairs.getKey());
        	
				if (rh==Enums.ResultHeader.PLACE)
				{
					result.setOverallPlace(Integer.parseInt(split[i]));
				}
				else if (rh==Enums.ResultHeader.FIRSTNAME)
				{
					result.getRacer().setFirstName(split[i]);
				}
				else if (rh==Enums.ResultHeader.LASTNAME)
				{
					result.getRacer().setLastName(split[i]);
				}
				else if (rh==Enums.ResultHeader.AGE)
				{
					result.getRacer().setAge(Integer.parseInt(split[i]));
				}
				else if (rh==Enums.ResultHeader.SEX)
				{
					if (split[i].equals("M"))
					{
						result.getRacer().setSex(Racer.Sex.MALE);
					}
					else if (split[i].equals("F"))
					{
						result.getRacer().setSex(Racer.Sex.FEMALE);
					}
				}
				else if (rh==Enums.ResultHeader.CITY)
				{
					result.getRacer().setCity(split[i]);
				}
				else if (rh==Enums.ResultHeader.STATE)
				{
					result.getRacer().setState(split[i]);
				}
				else if (rh==Enums.ResultHeader.GUN_TIME)
				{
					result.setChipTimeString(split[i]);
				}
				
				i++;
			}
        }
		catch(Exception ex)
		{
			return null;
		}
		
		return result;
	}
}
