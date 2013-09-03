package com.truenorth.scoreware.matchers;

import com.truenorth.scoreware.Racer;

public interface Matcher 
{
	double Match(Racer member, Racer racer);
	
	double minMatch();
}
