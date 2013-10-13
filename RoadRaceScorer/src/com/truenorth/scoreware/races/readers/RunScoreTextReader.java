package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

public class RunScoreTextReader extends TextRaceReader
{
	
	public RunScoreTextReader(String source)
	{
		super(source);
	}
	
	@Override
	public void parseAsText()
	{
		// extract text from the source data (could be a PDF, web page, database, etc.)
		ArrayList<String> text=extractor.extractText(sourceName);
			
		if (text!=null)
		{
			System.out.println(sourceName+": lines read: "+text.size());
		}
		else
		{
			System.out.println("could not read file: ");
		}
	
		int maxMarkersLine=0;
		int maxMarkers=-1;
		String maxMarkersString="";
		char marker='=';
		int j=0;
		
		for (String s:text)
		{
			int counter = 0;
			for( int i=0; i<s.length(); i++ ) {
			    if( s.charAt(i) == marker ) {
			        counter++;
			    } 
			}
			
			System.out.println(counter);
			
			if (counter>maxMarkers)
			{
				maxMarkers=counter;
				maxMarkersLine=j;
				maxMarkersString=s;
			}
			j++;
		}
		
		String headerLine=text.get(maxMarkersLine-1);
		
		System.out.println(headerLine);
		System.out.println(maxMarkersString);
		
		String[] split=maxMarkersString.split(" ");
		
		int[] locs=new int[split.length];
		int i=0;
		j=0;
		
		for (String s:split)
		{
			
			j+=s.length()+1;
			locs[i]=j;
			System.out.println(s+" "+j+" "+i+" "+locs[i]);
			i++;
		}
		
		i=0;
		for (int n:locs)
		{
			String temp=headerLine.substring(i,n);
			i=n;
			System.out.println(temp);
		}
		
		System.out.println(split.length);
		
	
	}
}
