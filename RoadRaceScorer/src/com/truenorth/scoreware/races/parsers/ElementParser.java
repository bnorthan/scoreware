package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;

public interface ElementParser 
{
	int parseElement(String[] elements, int startElement, Result result);
}
