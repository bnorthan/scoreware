package com.truenorth.scoreware.races.parsers;

import java.util.ArrayList;

import com.truenorth.scoreware.data.HeaderStrings;
import com.truenorth.scoreware.data.Result;

import java.util.Map;
import java.util.HashMap;


/**
 * Given a line of text representing a result, parses out the information (place, name, 
 * city, sex, etc.)
 * @author bnorthan
 *
 */
public abstract class ResultParser 
{
	protected int placeIndex=-1;
	
	protected int bibIndex=-1;
	
	protected int firstNameIndex=-1;
	protected int lastNameIndex=-1;
	protected int nameIndex=-1;
	
	protected int ageIndex=-1;
	protected int genderIndex=-1;
	
	protected int cityIndex=-1;
	protected int stateIndex=-1;
	
	protected int chipTimeIndex=-1;
	protected int gunTimeIndex=-1;
	
	protected int paceIndex=-1;
	protected int categoryIndex=-1;
	protected int pointsIndex=-1;
	protected int clubIndex=-1;
	
	Map<String, Integer> splitIndexes;
	Map<String, Integer> metaIndexes;
	
	public ResultParser()
	{
		splitIndexes=new HashMap<String, Integer>();
		metaIndexes=new HashMap<String, Integer>();
	}
	
	public abstract Result parseResultFromLine(Object line);
	
	int findHeaderPosition(String text, ArrayList<String> headers)
	{
		text=text.toLowerCase();
		
		for (String s:headers)
		{
			if (s.length()==1)
			{
				s=" "+s+" ";
			}
			int location=text.indexOf(s.toLowerCase());
			
			if (location!=-1) return location;
		}
		
		return -1;
	}
	
	boolean setHeaderIndex(String headerString, int i)
	{
		headerString=headerString.trim();
		
		if (HeaderStrings.matchHeader(headerString, HeaderStrings.getPlaceStrings()))
		{
			System.out.println("place found");
			placeIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getFirstNameStrings()))
		{
			System.out.println("first name found");
			firstNameIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getLastNameStrings()))
		{
			System.out.println("last name found");
			lastNameIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getFullNameStrings()))
		{
			System.out.println("full name found at "+i);
			nameIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getAgeStrings()))
		{
			System.out.println("age found");
			ageIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getGenderStrings()))
		{
			System.out.println("gender found");
			genderIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getCityStrings()))
		{
			System.out.println("city found");
			cityIndex=i;
			return true;
		}
		else if (HeaderStrings.matchHeader(headerString, HeaderStrings.getStateStrings()))
		{
			System.out.println("state found");
			stateIndex=i;
			return true;
		}
		else if (HeaderStrings.containsHeader(headerString, HeaderStrings.getChipTimeStrings()))
		{
			System.out.println("chip time found");
			chipTimeIndex=i;
			return true;
		}
		else if (HeaderStrings.containsHeader(headerString, HeaderStrings.getGunTimeStrings()))
		{
			System.out.println("gun time found");
			gunTimeIndex=i;
			return true;
		}
		else if (HeaderStrings.containsHeader(headerString, HeaderStrings.getSplitStrings()))
		{
			// record the split
			this.splitIndexes.put(headerString, i);
			return true;
		}
		else
		{
			// otherwise put this into the meta data Map
			this.metaIndexes.put(headerString, i);
			return false;
		}
		
	}
	
	public boolean verify(Object line)
	{
		return true;
	}
	
}

