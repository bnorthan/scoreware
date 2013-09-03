package com.truenorth.scoreware.races.parsers.generic;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;
import com.truenorth.scoreware.Racer.Sex;

import com.truenorth.scoreware.races.parsers.AbstractResultParser;

import com.truenorth.scoreware.races.parsers.elements.NameStateParser;

public abstract class GenericResultParser extends AbstractResultParser
{
	public GenericResultParser(ArrayList<ResultHeader> order)
	{
		super(order);
	}
	
	}
