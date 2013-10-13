package com.truenorth.scoreware.common.utility;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Racer.Sex;

public class SexParser 
{
	public static Sex parseSex(String sexString)
	{	
		sexString=sexString.toLowerCase();
		
		if (sexString.equals("male"))
		{
			return Sex.MALE;
		}
		else if (sexString.equals("m"))
		{
			return Racer.Sex.MALE;
		}
		else if (sexString.equals("female"))
		{
			return Racer.Sex.FEMALE;
		}
		else if (sexString.equals("f"))
		{
			return Racer.Sex.FEMALE;
		}
		else
		{
			return Racer.Sex.FEMALE;
		}
		
	}
}
