package com.truenorth.scoreware.extractors.overall;

import java.util.ArrayList;

import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.TextExtractor;

public class OverallFromSizeAndOrder implements OverallExtractor
{
	ArrayList<ResultHeader> order;
	
	public OverallFromSizeAndOrder(ArrayList<ResultHeader> order)
	{
		this.order=order;
	}
	
	public ArrayList<String> extractText(ArrayList<String> lines)
	{
		ArrayList<String> overall=new ArrayList<String>();
		
		int expectedPlace=1;
		
		for(String line:lines)
		{
			String[] split=line.split("\\s+");
			
			int n=split.length;
			
			//if (n>=order.size()-1)
			{
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
		}
		
		return overall;
	}
}
