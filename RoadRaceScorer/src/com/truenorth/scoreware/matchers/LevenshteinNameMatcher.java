package com.truenorth.scoreware.matchers;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.common.utility.FuzzyUtilities;
import com.truenorth.scoreware.common.utility.LevenshteinDistance;

public class LevenshteinNameMatcher extends AbstractMatcher
{
	public double getMatchThreshold()  
	{
		return .8;
	}
	
	public double getCheckThreshold()
	{
		return .9;
	}
	
	public double getMaxMatch()
	{
		return 1.0;
	}
	
	public double Match(Racer member, Racer racer)
	{
		return Match(member, racer, false);
	}
	
	double Match(Racer member, Racer racer, boolean print)
	{
		double nameMatch=matchName(racer, member);
		
		info="";
		
		info="Summary: \n";
		info+="Racer Name: "+racer.getFirstName()+" "+racer.getLastName()+"\n";
		info+="Member Name: "+member.getFirstName()+" "+member.getLastName()+"\n";
		info+="name match: "+nameMatch+"\n";
			
		System.out.println("Match: "+nameMatch);
		
		return nameMatch;
	}
	
	double matchName(Racer racer, Racer member)
	{
		// method 1 is firstname match + lastname match times 2
		double firstNameMatch=LevenshteinDistance.similarity(member.getFirstName().toLowerCase(), racer.getFirstName().toLowerCase());
		
		double lastNameMatch=LevenshteinDistance.similarity(member.getLastName().toLowerCase(), racer.getLastName().toLowerCase());
		
		double total1=(firstNameMatch+2*lastNameMatch)/3;
		
		// method 2 is just the total match of last+first (this handles the case of possible middle names or 
		// two last names better. 
		
		String racerFullName=racer.getFirstName().toLowerCase()+" "+racer.getLastName().toLowerCase();
		String memberFullName=member.getFirstName().toLowerCase()+" "+member.getLastName().toLowerCase();
		
		double total2=LevenshteinDistance.similarity(memberFullName, racerFullName);
		
		// return the max of method one or two
		return Math.max(total1, total2);
	}
	
}
