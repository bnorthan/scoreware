package com.truenorth.scoreware;

import com.truenorth.scoreware.extractors.TextExtractor;
import com.truenorth.scoreware.extractors.TextExtractorFactory;

public abstract class Reader 
{
	// name of the source (file name, web page, etc)
	protected String sourceName;
	
	// utility to extract the source as text
	protected TextExtractor extractor;
	
	public Reader(String sourceName)
	{
		this.sourceName=sourceName;
		
		// use the TextExtractorFactory to create the appropriate text extractor
		// based on the type of source
		this.extractor=TextExtractorFactory.MakeExtractor(sourceName);
	}
	
	public abstract void read();
	
}
