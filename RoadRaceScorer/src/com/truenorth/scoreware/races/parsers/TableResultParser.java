package com.truenorth.scoreware.races.parsers;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.data.HeaderStrings;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Racer.Sex;
import com.truenorth.scoreware.races.parsers.elements.FullNameParser;
import com.truenorth.scoreware.races.parsers.elements.LastFirstNameParser;


public class TableResultParser extends ResultParser 
{
	protected Element elements;
	
	protected FullNameParser fullNameParser=null;
	
	public Result parseResultFromLine(Object line)
	{
		elements=(Element)line;
		
		return parseElements();
	}
	
	public int parseHeader(Elements header)
	{
		int i=0;
		int numParsed=0;
		for (int j=0;j<header.size();j++)
		{
			String headerString=header.get(i).text();
			
			System.out.println(headerString);
			
			if (setHeaderIndex(headerString, i))
			{
				numParsed++;
			}
				
			i++;
		}
		
		int stop=5;
		
		return numParsed;
	}
	
	public Result parseElements()
	{
		try
		{
			Result result=new Result();
			
			System.out.println(elements.text());
		
			if (nameIndex!=-1)
			{
				
				
				String name=elements.child(nameIndex).text();
				
				fullNameParser=new LastFirstNameParser();
				
				fullNameParser.Parse(name);
				
				String firstName=fullNameParser.getFirstName();
				String lastName=fullNameParser.getLastName();
				/*
				String[] split=name.split("\\s+");
				
				String firstName="";
				
				for (int i=0;i<split.length-1;i++)
				{
					firstName+=split[i];
				}
				
				String lastName=split[split.length-1];
				*/
				result.getRacer().setFirstName(firstName);
				result.getRacer().setLastName(lastName);
				
				
			
			
			}
			
			if (firstNameIndex!=-1)
			{
				result.getRacer().setFirstName(elements.child(firstNameIndex).text());
			}
			
			if (lastNameIndex!=-1)
			{
				result.getRacer().setLastName(elements.child(lastNameIndex).text());
			}
	
			if (ageIndex!=-1)
			{
				String ageString = elements.child(ageIndex).text();
				
				
				int age=Integer.parseInt(elements.child(ageIndex).text());
				result.getRacer().setAge(age);
			}
			
			if (genderIndex!=-1)
			{
				if (elements.child(genderIndex).text().equals("M"))
				{
					result.getRacer().setSex(Sex.MALE);
				}
				else
				{
					result.getRacer().setSex(Sex.FEMALE);
				}
			}
			
			if (cityIndex!=-1)
			{
				result.getRacer().setCity(elements.child(cityIndex).text());
			}
			
			if (stateIndex!=-1)
			{
				result.getRacer().setState(elements.child(stateIndex).text());
			}
			
			if (placeIndex!=-1)
			{	
				int place=Integer.parseInt(elements.child(placeIndex).text());
				result.setOverallPlace(place);
			}
			
			if (gunTimeIndex!=-1)
			{
				String gunTime=elements.child(gunTimeIndex).text();
				result.setGunTimeString(gunTime);
			}
			
			if (chipTimeIndex!=-1)
			{
				String chipTime=elements.child(chipTimeIndex).text();
				result.setChipTimeString(chipTime);
			}
			
			// handle splits and metaData
			
			Iterator it=splitIndexes.entrySet().iterator();
			
			try
			{
				while (it.hasNext())
				{
					Map.Entry<String, Integer> pair=(Map.Entry<String, Integer>)it.next();
					String metaKey=pair.getKey();
					int metaIndex=pair.getValue();
				
					String metaString=elements.child(metaIndex).text();
				
					Date time=DateTimeParser.getTime(metaString);
					result.getSplits().add(time);
				
					System.out.println("Split: "+metaKey+":"+metaIndex);
				}
			
				it=metaIndexes.entrySet().iterator();
			
				while (it.hasNext())
				{
					Map.Entry<String, Integer> pair=(Map.Entry<String, Integer>)it.next();
					String metaKey=pair.getKey();
					int metaIndex=pair.getValue();
				
					String metaString=elements.child(metaIndex).text();
					result.getMetaData().add(metaString);
				
					System.out.println(metaKey+":"+metaIndex);
				}
			}
			catch(Exception e)
			{
				// if something went wrong parsing splits or meta data don't hose the whole thing
				// just continue
			}
			
			System.out.println(result.getRacer());
			System.out.println(result);
	
			return result;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
