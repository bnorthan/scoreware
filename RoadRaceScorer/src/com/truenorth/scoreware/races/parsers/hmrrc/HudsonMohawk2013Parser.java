package com.truenorth.scoreware.races.parsers.hmrrc;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.races.parsers.TableResultParser;
import com.truenorth.scoreware.Racer.Sex;

public class HudsonMohawk2013Parser extends TableResultParser
{
	public Result parseElements()
	{
		try
		{
			Result result=new Result();
		
			System.out.println(elements.text());
		
		
			result.getRacer().setFirstName(elements.child(2).text());
			result.getRacer().setLastName(elements.child(3).text());
	
			int age=Integer.parseInt(elements.child(4).text());
			result.getRacer().setAge(age);
			
			if (elements.child(5).text().equals("M"))
			{
				result.getRacer().setSex(Sex.MALE);
			}
			else
			{
				result.getRacer().setSex(Sex.FEMALE);
			}
			
			result.getRacer().setCity(elements.child(6).text());
			System.out.println(result.getRacer());
		
			result.getRacer().setState(elements.child(7).text());
			return null;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
