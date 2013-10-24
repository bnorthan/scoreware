package com.truenorth.scoreware.races.parsers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.Racer.Sex;
import com.truenorth.scoreware.races.parsers.StringResultParser;

public class WineGlass2013Parser extends StringResultParser
{
	public Result parseResultFromLine(String[] split)
	{
		try
		{
			Result result=new Result();
			int n=0;
			
			// first entry should be place
			int place=Integer.parseInt(split[n++]);
			result.setOverallPlace(place);
					
			// 99% of the time we get first then last name
			result.getRacer().setLastName(split[n++]);
			result.getRacer().setFirstName(split[n++]);
			
			// next should be male or female
			if (split[n].equals("M"))
			{
				result.getRacer().setSex(Sex.MALE);
			}
			else if (split[n].equals("F"))
			{
				result.getRacer().setSex(Sex.FEMALE);
			}
			else
			{
				// if we didn't parse sex it was likely because there were 3 names
				// parse the last name again and then go for number...
				result.getRacer().setFirstName(result.getRacer().getFirstName()+" "+split[n++]);
				if (split[n].equals("M"))
				{
					result.getRacer().setSex(Sex.MALE);
				}
				else if (split[n].equals("F"))
				{
					result.getRacer().setSex(Sex.FEMALE);
				}
			}
			n++;
			
			result.getRacer().setFirstName(result.getRacer().getFirstName().replace(",", ""));
			result.getRacer().setLastName(result.getRacer().getLastName().replace(",", ""));
			
			int age=-1;
			// next should be age so try and parse that
			try
			{
				result.getRacer().setAge(Integer.parseInt(split[n]));
				n++;
			}
			catch (Exception ex)
			{
				
			}
			
			// in this case overall time is always the last one
			String chipTime=split[split.length-1];
			chipTime=chipTime.substring(1,8);
			result.setChipTimeString(chipTime);
			
			return result;
		}
		catch(Exception ex)
		{
			System.out.println("Parsing failed");
			return null;
		}
	}


}

