package com.truenorth.scoreware.matchers;

/**
 * Abstract implementation of matcher
 * @author bnorthan
 *
 */
public abstract class AbstractMatcher implements Matcher
{
	String info;
	
	public String getInfo()
	{
		return info;
	}
}
