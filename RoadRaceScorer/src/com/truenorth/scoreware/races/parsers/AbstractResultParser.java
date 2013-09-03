package com.truenorth.scoreware.races.parsers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;

public abstract class AbstractResultParser implements ResultParser
{
	protected ArrayList<ResultHeader> order;

	public AbstractResultParser(ArrayList<ResultHeader> order)
	{
		this.order=order;	
	}
	
	public Result parseResultFromLine(String line)
	{	
		String[] split=line.split("\\s+");
		
		int n=split.length;
		
		boolean parsed=false;
		
		Result result=null;
		
		result=parseResultFromLine(split);
			
		if (result!=null)
		{
			System.out.println("YES: "+n+":"+line);
			return result;
		}
		else
		{
			System.out.println("NO: "+n+":"+line);
			return null;
		}
		
	}
	
	public abstract Result parseResultFromLine(String[] split);

}
