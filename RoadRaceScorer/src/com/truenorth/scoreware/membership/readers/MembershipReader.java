package com.truenorth.scoreware.membership.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.ScorewareReader;

/**
 * Abstract class used to read a membership list
 * @author bnorthan
 *
 */
public abstract class MembershipReader extends ScorewareReader 
{
	public MembershipReader(String sourceName)
	{
		super(sourceName);
		members = new ArrayList<Racer>();
	}
	protected ArrayList<Racer> members;
	
	public ArrayList<Racer> getMembers()
	{
		return members;
	}
}
