package com.truenorth.scoreware.extractors;

import java.util.ArrayList;

/**
 * 
 * @author bnorthan
 *
 * an interface used to extract text from a source   
 */
public abstract class TextExtractor 
{
	boolean keepSpaces=false;
	
	/**
	 * extract text from a source
	 * @param name
	 * name of the source (could be a file, webpage, etc)
	 * @return
	 * an arraylist of strings.  Each String represents a line of text
	 */
	public abstract ArrayList<String> extractText(String name);
	
	public void setKeepSpaces(boolean keepSpaces)
	{
		this.keepSpaces=keepSpaces;
	}
	
}
