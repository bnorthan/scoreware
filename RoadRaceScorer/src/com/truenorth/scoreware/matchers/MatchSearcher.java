package com.truenorth.scoreware.matchers;

import java.util.ArrayList;

import com.truenorth.scoreware.Racer;

public class MatchSearcher 
{
	Matcher matcher;
	
	public MatchSearcher()
	{
		matcher=new SimpleMatcher();
	}
	public Racer searchForMatch(Racer racer, ArrayList<Racer> members)
	{
		Racer match=null;
		double bestMatch=-1.0;
		
		for(Racer member:members)
		{
			double matchNum=matcher.Match(member, racer);
			
			System.out.println(racer.getLastName()+ " matches "+member.getLastName()+" to degree "+matchNum);
			
			double minMatch=matcher.minMatch();
			
			if ( (matchNum>bestMatch) && (matchNum>minMatch) )
			{
				bestMatch=matchNum;
				match=member;
				
				System.out.println("Member: ");
				System.out.println(member);
				System.out.println();
				
				System.out.println("Racer: ");
				System.out.println(racer);
				System.out.println();
				
				
			//	new java.util.Scanner(System.in).nextLine();
			//	System.out.println("best match is: "+match.getLastName());
				
			}
			
			
		}
		
		return match;
	}

}
