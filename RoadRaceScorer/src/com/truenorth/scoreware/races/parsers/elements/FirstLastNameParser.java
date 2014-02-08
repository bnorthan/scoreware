package com.truenorth.scoreware.races.parsers.elements;

import com.truenorth.scoreware.races.parsers.elements.FullNameParser;

public class FirstLastNameParser extends FullNameParser
{
	public void Parse(String fullName)
	{
		String[] split=fullName.split("\\s");
		
		firstName=split[0];
		
		for (int i=1;i<split.length;i++)
		{
			if (i==1)
			{
				lastName=split[i];
			}
			else
			{
				lastName+=" "+split[i];
			}
		}
	}
}
