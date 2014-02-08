package com.truenorth.scoreware.matchers;

import com.truenorth.scoreware.data.Racer;

/**
 * An interface used to match a member with a racer
 * 
 * a member is someone who appears on a membership list.  For example member of a club
 * 
 * a racer is someone who is in a list of race results. 
 * 
 * This interface would be used when trying to find the members of a group that participated
 * in the race
 * @author bnorthan
 *
 */
public interface Matcher 
{
	double Match(Racer member, Racer racer);
	
	double getMatchThreshold();
	
	double getCheckThreshold();
	
	double getMaxMatch();
	
	String getInfo();
}
