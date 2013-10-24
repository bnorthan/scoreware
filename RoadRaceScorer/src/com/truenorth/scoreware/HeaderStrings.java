package com.truenorth.scoreware;

import java.util.ArrayList;

public class HeaderStrings 
{
	static public ArrayList<String> getPlaceStrings()
	{
		ArrayList<String> placeStrings=new ArrayList<String>();
		
		placeStrings.add("place");
		
		return placeStrings;
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
		stateStrings.add(" st ");
		
		return stateStrings;
	}
	
	static public ArrayList<String> getChipTimeStrings()
	{
		ArrayList<String> chipTimeStrings=new ArrayList<String>();
		
		chipTimeStrings.add("NET_TIME");
		chipTimeStrings.add("chip time");
		
		return chipTimeStrings;
	}
	
	static public ArrayList<String> getGunTimeStrings()
	{
		ArrayList<String> gunTimeStrings=new ArrayList<String>();
		
		gunTimeStrings.add("GUN_TIME");
		gunTimeStrings.add("gun time");
		gunTimeStrings.add("Time");
		gunTimeStrings.add("Official time");
		
		return gunTimeStrings;
	}
	
	static public ArrayList<String> getAllStrings()
	{
		ArrayList<String> allStrings=new ArrayList<String>();
		
		for (String s:getPlaceStrings()) allStrings.add(s);
		for (String s:getAgeStrings()) allStrings.add(s);
		for (String s:getFirstNameStrings()) allStrings.add(s);
		for (String s:getLastNameStrings()) allStrings.add(s);
		for (String s:getGenderStrings()) allStrings.add(s);
		for (String s:getCityStrings()) allStrings.add(s);
		for (String s:getStateStrings()) allStrings.add(s);
		
		return allStrings;
		
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
	
}
