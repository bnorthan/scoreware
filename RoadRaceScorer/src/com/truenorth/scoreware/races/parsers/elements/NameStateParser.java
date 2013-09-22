package com.truenorth.scoreware.races.parsers.elements;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.races.parsers.ElementParser;

/**
 * Parses name and state in format "name" "SS" where SS is the state
 * 
 * deals with various cases.  Sometimes city is one word (Albany NY) sometimes
 * city is two word (Clifton Park NY)
 * @author bnorthan
 *
 */
public class NameStateParser implements ElementParser
{
	static public int parse(String[] elements, int startElement, Result result)
	{
		return new NameStateParser().parseElement(elements, startElement, result);
	}
	
	public int parseElement(String[] elements, int startElement, Result result)
	{
		int n=startElement;
		int maxn=startElement+3;
		
		boolean parsed=false;
		
		String city="";
		String state="";
		
		// city name should be first
		city=elements[n++];
		
		int i=0;
		
		// try and parse the state
		if (elements[n].length()==2)
		{
			if (elements[n].toUpperCase().equals(elements[n]))
			{
				state=elements[n];
				System.out.println("found state: "+state);
			}
				
			parsed=true;
		}
		
		// if can't parse state yet append to city name
		if (!parsed)
		{
			city=city+" "+elements[n++];
			
			// try and parse state again
			if (elements[n].length()==2)
			{
				if (elements[n].toUpperCase().equals(elements[n]))
				{
					state=elements[n];
					System.out.println("found state: "+state);
					parsed=true;
					n++;
				}
			}
		}
			
		result.getRacer().setCity(city);
		
		if (parsed)
		{
			result.getRacer().setState(state);
		}
		else
		{
			result.getRacer().setState("unknown");
		}
		
		return n;
	}
}
