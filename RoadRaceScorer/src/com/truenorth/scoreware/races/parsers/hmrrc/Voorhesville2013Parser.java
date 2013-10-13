package com.truenorth.scoreware.races.parsers.hmrrc;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.Racer.Sex;
import com.truenorth.scoreware.races.parsers.StringResultParser;

public class Voorhesville2013Parser extends StringResultParser
{
	public Voorhesville2013Parser(ArrayList<ResultHeader> order)
	{
		super(order);	
	}
	
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
			
			int age=-1;
			// next should be age so try and parse that
			try
			{
				result.getRacer().setAge(Integer.parseInt(split[n]));
				n++;
			}
			catch (Exception ex)
			{
				// if we didn't parse age it was likely because there were 3 names
				// parse the last name again and then go for number...
				result.getRacer().setLastName(result.getRacer().getLastName()+" "+split[n++]);
				result.getRacer().setAge(Integer.parseInt(split[n++]));
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
			
			return result;
		}
		catch(Exception ex)
		{
			System.out.println("Parse Exception: Voorhesville failed");
			return null;
		}
	}


}