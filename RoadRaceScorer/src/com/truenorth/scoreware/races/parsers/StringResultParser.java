package com.truenorth.scoreware.races.parsers;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;

/**
 * Used to parse a line of text representing a race result
 * @author bnorthan
 *
 */
public abstract class StringResultParser extends ResultParser
{
	/**
	 * Given a line of text representing a result parses that information
	 * and returns a "Result". 
	 */
	public Result parseResultFromLine(Object line)
	{
		String s;
		try
		{
			s=(String)line;
		}
		catch (Exception ex)
		{
			System.out.println("Problem: "+ex.getMessage());
			return null;
		}
		
		// This approach splits the line into separate elements
		//
		// Todo: keep in mind other approaches.  Such as results tables 
		String[] split=s.split("\\s+");
		
		return(parseResultFromLine(split));
	}
	
	public abstract Result parseResultFromLine(String[] split);

}
