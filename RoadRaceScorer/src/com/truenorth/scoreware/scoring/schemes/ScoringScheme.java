package com.truenorth.scoreware.scoring.schemes;

import com.truenorth.scoreware.Result;
import java.util.ArrayList;

public interface ScoringScheme 
{
	void Score(ArrayList<Result> results);
}
