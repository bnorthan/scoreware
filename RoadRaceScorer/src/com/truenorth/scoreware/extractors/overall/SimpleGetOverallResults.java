package com.truenorth.scoreware.extractors.overall;

import java.util.ArrayList;

import com.truenorth.scoreware.Enums.ResultHeader;
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
			String[] split=line.split("\\s+");
			
			int n=split.length;
			
			try
			{
				int place=Integer.parseInt(split[0]);
				System.out.println("place/expected: "+place+" : "+expectedPlace);
					
				if (place==expectedPlace)
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

