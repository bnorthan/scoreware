package com.truenorth.scoreware.extractors.overall;

import java.util.ArrayList;

import com.truenorth.scoreware.data.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.TextExtractor;

/**
 * Simple approach to find the overall results table. Siimply searches for the first 
 * occurence of 1, then 2, 3, 4 etc. 
 * @author bnorthan
 *
 */
public class SimpleGetOverallResults implements OverallExtractor
{	
	public ArrayList<String> extractText(ArrayList<String> lines)
	{
		ArrayList<String> overall=new ArrayList<String>();
		
		int expectedPlace=1;
		
		
		for(String line:lines)
		{
			String trimmed=line.trim();
			String[] split=trimmed.split("\\s+");
			
			int n=split.length;
			
			try
			{
				int place=Integer.parseInt(split[0]);
				System.out.println("place/expected: "+place+" : "+expectedPlace);
				
				// check if the place equals the expected place...  if so continue building the results table.
				// Note:  Also accept the case where place = expectedPlace-1... this is done because I've noticed in
				// large tables of lives results there can be repeat places.... keep an eye on this and rework logic 
				// as needed....
				if ( (place==expectedPlace) || (place==expectedPlace-1) )
				{
					overall.add(line);
					expectedPlace++;
				}
			}
			catch(Exception ex)
			{
				System.out.println("Parse Exception");
			}
				
		}
		
		return overall;
	}
}

