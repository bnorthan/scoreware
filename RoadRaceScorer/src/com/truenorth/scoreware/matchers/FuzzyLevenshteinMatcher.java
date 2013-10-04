package com.truenorth.scoreware.matchers;

import com.truenorth.scoreware.Racer;

import com.truenorth.scoreware.common.utility.LevenshteinDistance;
import com.truenorth.scoreware.common.utility.FuzzyUtilities;

/**
 * A matching algorithm that uses fuzzy logic and the Levenshtein algorithm
 * to determine how closely two racers match.
 * @author bnorthan
 *
 */
public class FuzzyLevenshteinMatcher extends AbstractMatcher
{
	public double getMatchThreshold()  
	{
		return 3.4;
	}
	
	public double getCheckThreshold()
	{
		return 3.8;
	}
	
	public double getMaxMatch()
	{
		return 4.5;
	}
	
	public double Match(Racer member, Racer racer)
	{
		return Match(member, racer, false);
	}
	
	double Match(Racer member, Racer racer, boolean print)
	{
		double nameMatch=matchName(racer, member);
		
		double ageMatch=matchAge(racer, member);
		
		double sexMatch=matchSex(racer, member);
	
		double cityMatch;
		if (racer.getCity()!=null)
		{
			cityMatch=0.5*LevenshteinDistance.similarity(member.getCity(), racer.getCity());
		}
		else
		{
			cityMatch=0.5;
		}
		
		double match=nameMatch+ageMatch+sexMatch+cityMatch;
		
		info="";
		
		info="Summary: \n";
		info+="Racer Name: "+racer.getFirstName()+" "+racer.getLastName()+"\n";
		info+="Member Name: "+member.getFirstName()+" "+member.getLastName()+"\n";
		info+="name match: "+nameMatch+"\n";
	//	info+="age: "+racer.getAge()+" "+member.getAge()+" "+ageDif+" "+ageMatch+"\n";
		info+="sex: "+racer.getSex()+" "+member.getSex()+" "+sexMatch+"\n";
		info+="city: "+racer.getCity()+" "+member.getCity()+" "+cityMatch+"\n";
		info+="Total match: "+match+"/"+getMaxMatch()+"\n";
		
		return match;
	}
	
	double matchAge(Racer racer, Racer member)
	{
		double ageDif=Math.abs(member.getAge()-racer.getAge());
		
		return FuzzyUtilities.sigmoid(-ageDif, .1);
	}
	
	double matchSex(Racer racer, Racer member)
	{
		if (member.getSex()==racer.getSex())
		{
			return 0.5;
		}
		else
		{
			return 0.0;
		}
	}
	
	double matchName(Racer racer, Racer member)
	{
		// method 1 is firstname match + lastname match times 2
		double firstNameMatch=LevenshteinDistance.similarity(member.getFirstName(), racer.getFirstName());
		
		double lastNameMatch=LevenshteinDistance.similarity(member.getLastName(), racer.getLastName());
		
		double total1=firstNameMatch+2*lastNameMatch;
		
		// method 2 is just the total match of last+first (this handles the case of possible middle names or 
		// two last names better. 
		
		String racerFullName=racer.getFirstName()+" "+racer.getLastName();
		String memberFullName=member.getFirstName()+" "+member.getLastName();
		
		double total2=3*LevenshteinDistance.similarity(memberFullName, racerFullName);
		
		// return the max of method one or two
		return Math.max(total1, total2);
	}
}
