package com.truenorth.scoreware.races.parsers.hmrrc;

import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Racer.Sex;
import com.truenorth.scoreware.races.parsers.TableResultParser;

public class HudsonMohawk2013Parser extends TableResultParser
{
	@Override
	public Result parseElements()
	{
		try
		{
			Result result=new Result();
		
			System.out.println(elements.text());
		
	/*		result.getRacer().setFirstName(elements.child(2).text());
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
			
			result.getRacer().setState(elements.child(7).text());
			
			int place=Integer.parseInt(elements.child(0).text());
			result.setOverallPlace(place);
			
			System.out.println(result.getRacer());
			System.out.println(result);*/
	
			result.getRacer().setFirstName(elements.child(firstNameIndex).text());
			result.getRacer().setLastName(elements.child(lastNameIndex).text());
	
			int age=Integer.parseInt(elements.child(ageIndex).text());
			result.getRacer().setAge(age);
			
			if (elements.child(genderIndex).text().equals("M"))
			{
				result.getRacer().setSex(Sex.MALE);
			}
			else
			{
				result.getRacer().setSex(Sex.FEMALE);
			}
			
			result.getRacer().setCity(elements.child(cityIndex).text());
			
			result.getRacer().setState(elements.child(stateIndex).text());
			
			int place=Integer.parseInt(elements.child(placeIndex).text());
			result.setOverallPlace(place);
			
			String chipTime=elements.child(chipTimeIndex).text();
			result.setChipTimeString(chipTime);
			
			System.out.println(result.getRacer());
			System.out.println(result);
	
			return result;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
