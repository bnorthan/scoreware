package com.truenorth.scoreware.data;

import com.truenorth.scoreware.extractors.TextExtractor;
import com.truenorth.scoreware.extractors.TextExtractorFactory;

public abstract class ScorewareReader 
{
	// name of the source (file name, web page, etc)
	protected String sourceName;
	
	public ScorewareReader(String sourceName)
	{
		this.sourceName=sourceName;	
	}
	
	public abstract void read();
	
}
