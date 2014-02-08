package com.truenorth.scoreware.data;

import java.util.*;

public class MatchStrings 
{
	
	public static ArrayList<String> getCityStrings()
	{
		ArrayList<String> cityList=new ArrayList<String>();
		
		cityList.add("Albany");
		cityList.add("Altamont");
		cityList.add("Ballston Spa");
		cityList.add("Ballston Lake");
		cityList.add("Castleton");
		cityList.add("Clifton Park");
		cityList.add("Colonie");
		cityList.add("Cooperstown");
		cityList.add("Delmar");
		cityList.add("Glenmont");
		cityList.add("Glenville");
		cityList.add("Green Island");
		cityList.add("Guilderland");
		cityList.add("Hudson");
		cityList.add("Latham");
		cityList.add("Mechanicville");
		cityList.add("Menands");
		cityList.add("Nassau");
		cityList.add("Niskayuna");
		cityList.add("Rotterdam");
		cityList.add("Saratoga Sptrings");
		cityList.add("Schenectady");
		cityList.add("Schorarie");
		cityList.add("Scotia");
		cityList.add("Slingerlands");
		cityList.add("Troy");
		
		return cityList;
	}
	
	// giant list of first names 
	// put this in a database eventually
	public static ArrayList<String> getFirstNameStrings()
	{
		
		ArrayList<String> firstNameList=new ArrayList<String>();
		
		firstNameList.add("Pete");
		firstNameList.add("Peter");
		firstNameList.add("Andrew");
		firstNameList.add("Joseph");
		firstNameList.add("Ryan");
		firstNameList.add("Ben");
		firstNameList.add("Andy");
		firstNameList.add("Dan");
		firstNameList.add("Janne");
		firstNameList.add("Joe");
		firstNameList.add("Joshua");
		firstNameList.add("Josh");
		firstNameList.add("Matthieu"); 
		firstNameList.add("Brian");
		firstNameList.add("Terry");
		firstNameList.add("Julie");
		firstNameList.add("Jerome");
		firstNameList.add("Vance");
		firstNameList.add("Herbie");
		firstNameList.add("Steve");
		firstNameList.add("Tim ");
		firstNameList.add("Peter");
		firstNameList.add("Rebecca");
		firstNameList.add("Mason");
		firstNameList.add("Dean");
		firstNameList.add("Andreas");
		firstNameList.add("Ian");
		firstNameList.add("Wayne");
		firstNameList.add("Andrew");
		firstNameList.add("Kim");
		firstNameList.add("Larry");
		firstNameList.add("Evelyne");
		firstNameList.add("Denis");
		firstNameList.add("Kate");
		firstNameList.add("Doug");
		firstNameList.add("Brian");
		firstNameList.add("John");
		firstNameList.add("Martin");
		firstNameList.add("Alex");
		firstNameList.add("John");
		firstNameList.add("Aneta");
		firstNameList.add("Krista");
		firstNameList.add("Ron");
		firstNameList.add("Andrew"); 
		firstNameList.add("Florence");
		firstNameList.add("Suzanne");
		firstNameList.add("Lee");
		firstNameList.add("Julia");
		firstNameList.add("Eric");
		firstNameList.add("Carol");
		firstNameList.add("Michelle");
		firstNameList.add("Stacey");
		firstNameList.add("Craig");
		firstNameList.add("Gary");
		firstNameList.add("James");
		firstNameList.add("Judy");
		firstNameList.add("Bryan");
		
		
		return firstNameList;
	}
	
	public static boolean isListOfCities(ArrayList<String> cityList)
	{
		int numCities=0;
		
		for (String s:MatchStrings.getCityStrings())
		{
			for (String s2:cityList)
			{
				String s_lower=s.toLowerCase();
				String s2_lower=s2.toLowerCase();
				
				int index=s2_lower.indexOf(s_lower);
				
				if (index>-1)
				{
					numCities++;
				}
			}
		}
		
		float ratio=(float)numCities/(float)cityList.size();
		
		System.out.println("number data points "+cityList.size());
		System.out.println("number first names "+numCities);
		System.out.println("ratio: "+ratio);
		System.out.println();
		
		if (ratio>0.15)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isListOfFirstNames(ArrayList<String> stringList)
	{
		int numFirstNames=0;
		
		for (String s:MatchStrings.getFirstNameStrings())
		{
			for (String s2:stringList)
			{
				String s_lower=s.toLowerCase();
				String s2_lower=s2.toLowerCase();
				
				int index=s2_lower.indexOf(s_lower);
			
				if (index>-1)
				{
					numFirstNames++;
				}
			}
		}
	
		float ratio=(float)numFirstNames/(float)stringList.size();
		
		System.out.println("number data points "+stringList.size());
		System.out.println("number first names "+numFirstNames);
		System.out.println("ratio: "+ratio);
		System.out.println();
		
		if (ratio>0.15)
		{
			return true;
		}
		
		return false;
	}
	
}
