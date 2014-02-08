package com.truenorth.scoreware.races.parsers;

import java.sql.*;

import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Racer.Sex;

public class SqlResultParser extends ResultParser
{
	public SqlResultParser()
	{
		placeIndex=3;
		
		firstNameIndex=1;
		lastNameIndex=2;
		nameIndex=-1;
		
		ageIndex=4;
		genderIndex=5;
		
		categoryIndex=8;
		
		cityIndex=9;
		stateIndex=10;
		
		pointsIndex=11;
		clubIndex=12;
		
		chipTimeIndex=-1;
		gunTimeIndex=6;

	}
	
	public Result parseResultFromLine(Object line)
	{
		ResultSet rs;
		
		// try casting as a result set
		try 
		{
			rs=(ResultSet)line;
		}
		catch (Exception e)
		{
			return null;
		}
		
		Result result=new Result();
		
		if (placeIndex!=-1)
		{
			try
			{
				String strPlace=rs.getString(placeIndex);
				
				int place=Integer.parseInt(strPlace);
				
				result.setOverallPlace(place);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
		}
		
		if (firstNameIndex!=-1)
		{
			try
			{
				String strFirstName=rs.getString(firstNameIndex);
				
				result.getRacer().setFirstName(strFirstName);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
		}
		if (lastNameIndex!=-1)
		{
			try
			{
				String strLastName=rs.getString(lastNameIndex);
				
				result.getRacer().setLastName(strLastName);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
			
		}
		if (nameIndex!=-1)
		{
			
		}
		
		if (ageIndex!=-1)
		{
			try
			{
				String strAge=rs.getString(ageIndex);
				
				int age=Integer.parseInt(strAge);
				
				result.getRacer().setAge(age);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
			
		}
		
		if (pointsIndex!=-1)
		{
			try
			{
				String strPoints=rs.getString(pointsIndex);
				
				int points=Integer.parseInt(strPoints);
				
				result.setPoints(points);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
			
		}
		
		if (genderIndex!=-1)
		{
			try
			{
				String strGender=rs.getString(genderIndex);
				
				if (strGender.equals("M"))
				{
					result.getRacer().setSex(Sex.MALE);
				}
				else if (strGender.equals("F"))
				{
					result.getRacer().setSex(Sex.FEMALE);
				}
			}
			catch(Exception e)
			{
				
			}
	
		}
		
		if (cityIndex!=-1)
		{
			try
			{
				String strCity=rs.getString(cityIndex);
				
				result.getRacer().setCity(strCity);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
		}
		
		if (clubIndex!=-1)
		{
			try
			{
				String strClub=rs.getString(clubIndex);
				
				result.getRacer().setCurrentClub(strClub);
			}
			catch(Exception ex)
			{
				// continue even if parsing club didn't work
			}
		}
		
		if (categoryIndex!=-1)
		{
			try
			{
				String strCategory=rs.getString(categoryIndex);
				
				result.setCategoryString(strCategory);
			}
			catch(Exception ex)
			{
				// continue even if parsing club didn't work
			}
		}
		
		if (stateIndex!=-1)
		{
			try
			{
				String strState=rs.getString(stateIndex);
				
				result.getRacer().setState(strState);
			}
			catch (Exception ex)
			{
				// continue even if parsing place didn't work
			}
		}
		
		if (chipTimeIndex!=-1)
		{
			
		}
		if (gunTimeIndex!=-1)
		{
			try
			{
				String strTime=rs.getString(gunTimeIndex);
				
				result.setGunTime(DateTimeParser.getDateTimeFromSql(strTime));
				
			}
			catch (Exception e)
			{
				
			}
			
		}
				
		return result;
	}

}
