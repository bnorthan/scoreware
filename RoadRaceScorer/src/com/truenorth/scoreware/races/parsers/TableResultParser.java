package com.truenorth.scoreware.races.parsers;

import com.truenorth.scoreware.Result;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.truenorth.scoreware.HeaderStrings;

abstract public class TableResultParser extends ResultParser 
{
	protected Element elements;
	
	ArrayList<Integer> splits=new ArrayList<Integer>();
	
	public Result parseResultFromLine(Object line)
	{
		elements=(Element)line;
		
		return parseElements();
	}
	
	public void parseHeader(Elements header)
	{
		int i=0;
		for (int j=0;j<header.size();j++)
		{
			String headerString=header.get(i).text();
			
			System.out.println(headerString);
			
			setHeaderIndex(headerString, i);
				
			i++;
		}
		
		int stop=5;
	}
	
	

	public abstract Result parseElements();
}
