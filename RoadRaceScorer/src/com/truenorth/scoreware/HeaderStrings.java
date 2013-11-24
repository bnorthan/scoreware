package com.truenorth.scoreware;

import java.util.ArrayList;
import com.truenorth.scoreware.common.utility.FuzzyUtilities;

public class HeaderStrings 
{
	static public ArrayList<String> getPlaceStrings()
	{
		ArrayList<String> placeStrings=new ArrayList<String>();
		
		placeStrings.add("place");
		
		return placeStrings;
	}
	
	static public ArrayList<String> getNumberStrings()
	{
		ArrayList<String> numberStrings=new ArrayList<String>();
		
		numberStrings.add("bib");
		numberStrings.add("comp#");
		numberStrings.add("No.");
		
		return numberStrings;
	}
	
	static public ArrayList<String> getFirstNameStrings()
	{
		ArrayList<String> fnameStrings=new ArrayList<String>();
		
		fnameStrings.add("fname");
		fnameStrings.add("First Name");
		
		return fnameStrings;
	}
	
	static public ArrayList<String> getLastNameStrings()
	{
		ArrayList<String> lnameStrings=new ArrayList<String>();
		
		lnameStrings.add("lname");
		lnameStrings.add("Last Name");
		
		return lnameStrings;
	}
	
	static public ArrayList<String> getFullNameStrings()
	{
		ArrayList<String> fullNameStrings=new ArrayList<String>();
		
		fullNameStrings.add("name");
		fullNameStrings.add("Full Name");
		
		return fullNameStrings;
	}
	
	static public ArrayList<String> getAgeStrings()
	{
		ArrayList<String> ageStrings=new ArrayList<String>();
		
		ageStrings.add("age");
		ageStrings.add("ag");
		
		return ageStrings;
	}

	
	static public ArrayList<String> getGenderStrings()
	{
		ArrayList<String> genderStrings=new ArrayList<String>();
		
		genderStrings.add("sex");
		genderStrings.add("gender");
		genderStrings.add("S");
		
		return genderStrings;
	}
	
	static public ArrayList<String> getCityStrings()
	{
		ArrayList<String> cityStrings=new ArrayList<String>();
		
		cityStrings.add("city");
		
		return cityStrings;
	}
	
	static public ArrayList<String> getStateStrings()
	{
		ArrayList<String> stateStrings=new ArrayList<String>();
		
		stateStrings.add("state");
		stateStrings.add("st");
		
		return stateStrings;
	}
	
	static public ArrayList<String> getChipTimeStrings()
	{
		ArrayList<String> chipTimeStrings=new ArrayList<String>();
		
		chipTimeStrings.add("nettime");
		chipTimeStrings.add("NET_TIME");
		chipTimeStrings.add("chip time");
		
		return chipTimeStrings;
	}
	
	static public ArrayList<String> getGunTimeStrings()
	{
		ArrayList<String> gunTimeStrings=new ArrayList<String>();
		
		gunTimeStrings.add("guntime");
		gunTimeStrings.add("GUN_TIME");
		gunTimeStrings.add("gun time");
		gunTimeStrings.add("Time");
		gunTimeStrings.add("Official time");
		
		return gunTimeStrings;
	}
	
	static public ArrayList<String> getSplitStrings()
	{
		ArrayList<String> splitStrings=new ArrayList<String>();
		
		splitStrings.add("13.1M");
		splitStrings.add("17.4M");
		splitStrings.add("0:split 1");
		splitStrings.add("0:split 2");
		
		return splitStrings;
	}
	
	static public ArrayList<String> getClubStrings()
	{
		ArrayList<String> clubStrings=new ArrayList<String>();
		
		clubStrings.add("club");
		clubStrings.add("team");
		
		return clubStrings;
		
	}
	
	static public ArrayList<String> getPaceStrings()
	{
		ArrayList<String> paceStrings=new ArrayList<String>();
		
		paceStrings.add("pace");
		
		return paceStrings;
	}
	
	static public ArrayList<String> getAllStrings()
	{
		ArrayList<String> allStrings=new ArrayList<String>();
		
		for (String s:getPlaceStrings()) allStrings.add(s);
		for (String s:getNumberStrings()) allStrings.add(s);
		for (String s:getAgeStrings()) allStrings.add(s);
		for (String s:getFirstNameStrings()) allStrings.add(s);
		for (String s:getLastNameStrings()) allStrings.add(s);
		for (String s:getFullNameStrings()) allStrings.add(s);
		for (String s:getGenderStrings()) allStrings.add(s);
		for (String s:getCityStrings()) allStrings.add(s);
		for (String s:getStateStrings()) allStrings.add(s);
		for (String s:getGunTimeStrings()) allStrings.add(s);
		for (String s:getChipTimeStrings()) allStrings.add(s);
		for (String s:getClubStrings()) allStrings.add(s);
		
		return allStrings;
		
	}
	
	// given a string will return true if string is a header
	static public boolean isHeader(String s)
	{
		return false;
	}
	
	static public float fuzzyIsHeader(String s)
	{
		float fuzzy=0.0f;
		
		// number of words in string
		int numWords=s.split("\\s+").length;
		
		int numHeaderWords=headerStringOccurrences(s);
		
		float ratio=(float)numHeaderWords/(float)numWords;
		
		// is number of header words above 4??
		double above4=FuzzyUtilities.sigmoid(numHeaderWords-4, 1);
		
		fuzzy=(float)above4+ratio;
		
		return fuzzy;
	}
	
	static public int headerStringOccurrences(String string)
	{
		int num=0;
		
		for (String s:HeaderStrings.getAllStrings())
		{
			if (string.toLowerCase().contains(s.toLowerCase())) num++;
		}
		
		return num;
	}
	
	static public boolean matchHeader(String text, ArrayList<String> headers)
	{
		text=text.toLowerCase();
		
		for (String s:headers)
		{
			if ( text.equals(s.toLowerCase()) ) return true;
		}
		
		return false;
	}
	
	static public boolean containsHeader(String text, ArrayList<String> headers)
	{
		text=text.toLowerCase();
		
		for (String s:headers)
		{
			if ( text.contains(s.toLowerCase()) ) return true;
		}
		
		return false;
	}
	
}
