package com.truenorth.scoreware.scoring.schemes;

import com.truenorth.scoreware.data.Result;

import java.util.ArrayList;

/**
 * given a list of results 
 * @author bnorthan
 *
 */
public interface ScoringScheme 
{
	void Score(ArrayList<Result> results);
}
