package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;

public interface ResultParser 
{
	Result parseResultFromLine(String line);
}

