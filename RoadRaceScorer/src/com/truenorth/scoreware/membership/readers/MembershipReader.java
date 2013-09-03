package com.truenorth.scoreware.membership.readers;

import java.util.ArrayList;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Reader;

public abstract class MembershipReader extends Reader 
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
