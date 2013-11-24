package com.truenorth.scoreware;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.truenorth.scoreware.Enums.ResultHeader;

public class DataFormats 
{
	public enum DataTypes
	{
		N, // integer number
		MM_SS, // mm_ss time,
		LETTERS,
		CHARS_COMMA,
		CHARS // characters		
	}
	
	public static Enums.ResultHeader whatHeader(String s, DataTypes dt)
	{
		if (dt==DataTypes.LETTERS)
		{
			// is it a state/province (2 capitol letters)?
			Pattern namePattern=Pattern.compile("^[A-Z]{2}$");
			Matcher nameMatcher=namePattern.matcher(s);
			
			if (nameMatcher.matches())
			{
				return ResultHeader.STATE;
			}
			
			// is it gender??
			namePattern=Pattern.compile("^[MF]$");
			nameMatcher=namePattern.matcher(s);
			
			if (nameMatcher.matches())
			{
				return ResultHeader.SEX;
			}
			
		}
		
		// not sure
		return null;
	}
	
	public static DataTypes whatType(String s)
	{
		boolean success=false;
		
		// is it a integer??? try and parse as int
		try
		{
			Integer.parseInt(s);
			return DataTypes.N;
		}
		catch(Exception e)
		{
			// if this didn't work keep going
		}
		
		// is it a minute:seconds time??
		if (!success)
		{
			DateFormat format_mmss=new SimpleDateFormat("mm:ss");
			Date test=null;
			try
			{
				test=format_mmss.parse(s);
			}
			catch(Exception e)
			{
				
			}
			
			if (test!=null)
			{
				return DataTypes.MM_SS;
			}
		}
		
		// is it a name
		Pattern namePattern=Pattern.compile("^[A-Za-z]+$");
		Matcher nameMatcher=namePattern.matcher(s);
		
		if (nameMatcher.matches())
		{
			return DataTypes.LETTERS;
		}
		
		// is it a state??
		if (!success)
		{
		
		}
		
		// is it just a character string??
		if (!success)
		{
			
			// comma deliniminted??
			if (s.charAt(s.length()-1)==',')
			{
				return DataTypes.CHARS_COMMA;
			}
		}
		
		// default to just a char
		return DataTypes.CHARS;
		
	}
}
