package com.truenorth.scoreware.extractors.overall;
import java.util.ArrayList;

/**
 * from an arraylist of strings representing each line of a file
 * extract the overall results.  (file will also contain other information about the
 * race, category results, etc.)
 * @author bnorthan
 *
 */
public interface OverallExtractor 
{
	ArrayList<String> extractText(ArrayList<String> name);
}

