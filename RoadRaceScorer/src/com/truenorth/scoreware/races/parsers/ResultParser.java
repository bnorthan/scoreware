package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;

/**
 * Given a line of text representing a result, parses out the information (place, name, 
 * city, sex, etc.)
 * @author bnorthan
 *
 */
public interface ResultParser 
{
	Result parseResultFromLine(Object line);
}

