package com.truenorth.scoreware.races.parsers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;

/**
 * Used to parse a line of text representing a race result
 * @author bnorthan
 *
 */
public abstract class AbstractResultParser implements ResultParser
{
	protected ArrayList<ResultHeader> order;

	public AbstractResultParser(ArrayList<ResultHeader> order)
	{
		this.order=order;	
	}
	
	/**
	 * Given a line of text representing a result parses that information
	 * and returns a "Result". 
	 */
	public Result parseResultFromLine(String line)
	{	
		// This approach splits the line into separate elements
		//
		// Todo: keep in mind other approaches.  Such as results tables 
		String[] split=line.split("\\s+");
		
		return(parseResultFromLine(split));
	}
	
	public abstract Result parseResultFromLine(String[] split);

}
