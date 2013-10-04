package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.ScorewareReader;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;

/**
 * Extends Reader to implement a RaceReader.  RaceReader reads
 * race results
 * @author bnorthan
 *
 */
public abstract class RaceReader extends ScorewareReader 
{
	public RaceReader(String sourceName)
	{
		super(sourceName);
		
		results=new ArrayList<Result>();
	}
	
	protected ArrayList<ResultHeader> order=new ArrayList<ResultHeader>();
	protected ArrayList<Result> results;
	protected ResultParser resultParser;
	
	public ArrayList<Result> getResults()
	{
		return results;
	}
}