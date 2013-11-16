package com.truenorth.scoreware.races.parsers.elements;

import com.truenorth.scoreware.races.parsers.elements.FullNameParser;

public class LastFirstNameParser extends FullNameParser
{
	String d=null;
	
	public void Parse(String fullName)
	{
		String[] split=fullName.split(",");
		
		split[0]=split[0].trim();
		String[] lastSplit=split[0].split("\\s+");
		
		lastName="";
		
		int i=0;
		for (String s:lastSplit)
		{
			if (i>0)
			{
				lastName+=" ";
			}
			i++;
			lastName+=s;
		}
		
		split[1]=split[1].trim();
		String[] firstSplit=split[1].split("\\s+");
		
		firstName="";
		
		i=0;
		for (String s:firstSplit)
		{
			if (i>0)
			{
				firstName+=" ";
			}
			i++;
			firstName+=s;
		}
	}
	
}
