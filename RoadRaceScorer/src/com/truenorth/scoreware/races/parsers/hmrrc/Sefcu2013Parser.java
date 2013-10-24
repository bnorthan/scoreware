package com.truenorth.scoreware.races.parsers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.Racer.Sex;
import com.truenorth.scoreware.races.parsers.elements.NameStateParser;
import com.truenorth.scoreware.races.parsers.StringResultParser;

/**
 * Parses lines of the result table from Sefcu 2013
 * @author bnorthan
 *
 */
public class Sefcu2013Parser extends StringResultParser
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
			result.getRacer().setFirstName(split[n++]);
			result.getRacer().setLastName(split[n++]);
			
			// next should be number so try and parse that
			try
			{
				result.setNumber(Integer.parseInt(split[n]));
				n++;
			}
			catch (Exception ex)
			{
				// if we didn't parse age it was likely because there were 3 names
				// parse the last name again and then go for number...
				result.getRacer().setLastName(result.getRacer().getLastName()+" "+split[n++]);
				Integer.parseInt(split[n++]);
				//result.getRacer().setAge(Integer.parseInt(split[n++]));
			}
			
			// next should be age
			try
			{
				result.getRacer().setAge(Integer.parseInt(split[n++]));
			}
			catch(Exception ex)
			{
				
			}
			
			// next should be male of female
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
				// something went wrong			
			}
			n++;
			
			// next should be category... just skip for now
			n++;
			
			String city=split[n++];
			
			// next should be either a y or a n
			if (split[n].equals("y")||split[n].equals("n"))
			{
				// finished with city
			}
			else
			{
				// the city has two words get the rest
				city=city+" "+split[n];
			}
			
			result.getRacer().setCity(city);
			
			result.getRacer().setState("**");
			
			return result;
		}
		catch(Exception ex)
		{
			System.out.println("Parse Exception: Scheme1 failed");
			return null;
		}
	}
}

