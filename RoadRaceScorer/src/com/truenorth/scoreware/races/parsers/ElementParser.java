package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.data.Result;

/**
 * Given a list of String "elements" parses out (possibly) related information
 * for example City and State
 * @author bnorthan
 *
 */
public interface ElementParser 
{
	int parseElement(String[] elements, int startElement, Result result);
}
