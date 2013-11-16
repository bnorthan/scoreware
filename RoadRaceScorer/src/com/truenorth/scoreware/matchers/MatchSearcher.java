package com.truenorth.scoreware.matchers;

import java.util.ArrayList;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;

public class MatchSearcher 
{
	Matcher matcher;
	boolean verbose=false;
	boolean interactive=true;
	
	double matchThreshold=0;
	double checkThreshold=0;
	double maxThreshold=0;

	IsRacerMember isRacerMember;
	
	String memberString="unknown";
	String notMemberString="unknown";
	
	public MatchSearcher()
	{
		matcher=new FuzzyLevenshteinMatcher();
		
		matchThreshold=matcher.getMatchThreshold();
		checkThreshold=matcher.getCheckThreshold();
		maxThreshold=matcher.getMaxMatch();
		
		isRacerMember=new IsRacerMemberCommandLine();
	}
	
	public void setMatchThreshold(double percentMax)
	{
		matchThreshold=maxThreshold*percentMax/100.0;
	}
	
	public void setCheckThreshold(double percentMax)
	{
		checkThreshold=maxThreshold*percentMax/100.0;
	}
	
	public void setMatcher(Matcher matcher)
	{
		this.matcher=matcher;
	}
	
	public ArrayList<Result> findMembers(ArrayList<Result> racers, ArrayList<Racer> membershipList, int num)
	{
		ArrayList<Result> members=new ArrayList<Result>();
		
		resultsLoop:
		for (Result result:racers)
		{
			try
			{
				Racer match=searchForMatch(result.getRacer(), membershipList);
		
				if (match!=null)
				{
					result.getRacer().setCurrentClub(memberString);
					members.add(result);
				}
			
			}
			catch (Exception objEx)
			{
			
			}
			
			if (members.size()==num)
			{
				break resultsLoop;
			}
		}
		
		return members;
	}
	
	
	public Racer searchForMatch(Racer racer, ArrayList<Racer> members)
	{
		Racer match=null;
		
		double bestMatch=-1.0;
		System.out.println(racer);
	
		// loop through all members
		for(Racer member:members)
		{
			// check for a match
			double matchNum=matcher.Match(member, racer);
			
			/*if ( (racer.getFirstName().equals("Linda")) &&
					(racer.getLastName().equals("Keeley")) &&
					(member.getLastName().equals("Keeley")))
			//if (result.getRacer().getLastName().equals("O'Connor T"))
			{
				
				System.out.println(matcher.getInfo());
				//System.out.println("matchNum: "+matchNum);
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
				System.out.println(matcher.getInfo());
				
				boolean acceptMatch=isRacerMember.IsRacerAMember(racer, match);
				
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
		
	/*	if ( (racer.getFirstName().equals("Shana")) &&
				(racer.getLastName().equals("Marra")) )
		//if (result.getRacer().getLastName().equals("O'Connor T"))
		{
			
			System.out.println(matcher.getInfo());
			//System.out.println("matchNum: "+matchNum);
			new java.util.Scanner(System.in).nextLine();
		}*/
		
		return match;
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
	
	public void setMemberString(String memberString)
	{
		this.memberString=memberString;
	}
	
	public void setNotMemberString(String notMemberString)
	{
		this.notMemberString=notMemberString;
	}
	
	protected void printMatch(String match)
	{
		System.out.print(match);
	}
	
	class IsRacerMemberCommandLine implements IsRacerMember
	{
		public boolean IsRacerAMember(Racer racer, Racer member)
		{
			System.out.println("************************");
			System.out.println("Is this person a member (y/n)?");
			System.out.println(racer);
			System.out.println("************************");
			
			System.out.println("(they are possibly this person)");
			System.out.println(member);
			
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
}
