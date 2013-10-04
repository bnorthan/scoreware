package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;

import org.jsoup.nodes.Element;

abstract public class TableResultParser implements ResultParser 
{
	protected Element elements;
	
	public Result parseResultFromLine(Object line)
	{
		elements=(Element)line;
		
		return parseElements();
	}
	
	public abstract Result parseElements();
}
