package com.truenorth.scoreware.matchers;

import java.util.ArrayList;

import com.truenorth.scoreware.Racer;

import com.truenorth.scoreware.Report;

public class MatchSearcher 
{
	Matcher matcher;
	boolean verbose=false;
	boolean interactive;
	
	double matchThreshold=0;
	double checkThreshold=0;
	double maxThreshold=0;
	
	Report report=null;
	
	IsRacerMember isRacerMember;
	
	public MatchSearcher()
	{
		matcher=new FuzzyLevenshteinMatcher();
		
		matchThreshold=matcher.getMatchThreshold();
		checkThreshold=matcher.getCheckThreshold();
		maxThreshold=matcher.getMaxMatch();
	}
	
	public void setMatchThreshold(double percentMax)
	{
		matchThreshold=maxThreshold*percentMax/100.0;
	}
	
	public void setCheckThreshold(double percentMax)
	{
		checkThreshold=maxThreshold*percentMax/100.0;
	}
	
	public Racer searchForMatch(Racer racer, ArrayList<Racer> members)
	{
		Racer match=null;
		
		double bestMatch=-1.0;
		//System.out.println(racer);
	
		// loop through all members
		for(Racer member:members)
		{
			// check for a match
			double matchNum=matcher.Match(member, racer);
			
			/*if ( (member.getLastName().equals("Munson")) &&
					(racer.getLastName().equals("Munson")) )
			//if (result.getRacer().getLastName().equals("O'Connor T"))
			{
				
				System.out.println(matcher.getInfo());
				System.out.println("matchNum: "+matchNum);
				new java.util.Scanner(System.in).nextLine();
			}*/
			
			//System.out.println(racer.getLastName()+ " matches "+member.getLastName()+" to degree "+matchNum);
			
			// if the match is better then the best so far and higher then both the match threshold
			// and the check threshold then just accept it
			if ( (matchNum>bestMatch) && (matchNum>matchThreshold) && (matchNum>checkThreshold) )
			{
				bestMatch=matchNum;
				match=member;
				
				if (verbose)
			//	if (matchNum<checkThreshold)
				{
					printMatch(matcher.getInfo());;
					new java.util.Scanner(System.in).nextLine();
				}
			}
			// if the match is between the match trheshold and the check threshold
			else if ( (matchNum>bestMatch)&&(matchNum>matchThreshold)&&(matchNum<=checkThreshold))
			{
			/*	if (interactive)
				{
					printMatch(matcher.getInfo());
					boolean acceptMatch=acceptMatch();
				
					if (acceptMatch)
					{
						bestMatch=matchNum;
						match=member;
					}
				}
				else*/
				{
					bestMatch=matchNum;
					match=member;
					
					if (verbose)
					{
						printMatch(matcher.getInfo());;
						new java.util.Scanner(System.in).nextLine();
					}
				}
			}
		}
		
		if ( (bestMatch>matchThreshold)&&(bestMatch<=checkThreshold))
		{
			if (interactive)
			{
				matcher.Match(match, racer);
				
				boolean acceptMatch=isRacerMember(racer, match);
				
				if (acceptMatch)
				{
					return match;
				}
				else
				{
					return null;
				}
			}
		}
		
		return match;
	}
	
	boolean isRacerMember(Racer racer, Racer member)
	{
		if (isRacerMember!=null)
		{
			return isRacerMember.IsRacerAMember(racer, member);
		}
		else
		{
			printMatch(matcher.getInfo());
			return acceptMatch();
		}
	}
	
	public void setVerbose(boolean verbose)
	{
		this.verbose=verbose;
	}
	
	public void setInteractive(boolean interactive)
	{
		this.interactive=interactive;
	}
	
	public void setIsRacerMember(IsRacerMember isRacerMember)
	{
		this.isRacerMember=isRacerMember;
	}
	
	protected void printMatch(String match)
	{
		System.out.print(match);
	}
	
	protected boolean acceptMatch()
	{
		System.out.println("accept match (y/n)?");
		String yesOrNo=new java.util.Scanner(System.in).next();
		
		if (yesOrNo.equals("y"))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
