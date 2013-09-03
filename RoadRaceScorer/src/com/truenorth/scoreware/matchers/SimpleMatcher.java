package com.truenorth.scoreware.matchers;

import com.truenorth.scoreware.Racer;

public class SimpleMatcher implements Matcher
{
	public double Match(Racer member, Racer racer)
	{
		double match=0.0;
		
		if (racer.getFirstName().equals(member.getFirstName()))
		{
			match=match+1.0;
		}
		
		if (racer.getLastName().equals(member.getLastName()))
		{
			match=match+1.0;
			
		}
		
		if (racer.getAge()==member.getAge())
		{
			match=match+1.0;
		}
		
		if (racer.getCity()==member.getCity())
		{
			match=match+1.0;
		}
		
		if (racer.getSex()==member.getSex())
		{
			match=match+1.0;
		}
		
		return match;
	}
	
	public double minMatch()
	{
		return 3.9;
	}
}
