package com.truenorth.scoreware.races.parsers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;

import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.common.utility.ValueComparator;
import com.truenorth.scoreware.data.Enums;
import com.truenorth.scoreware.data.HeaderStrings;
import com.truenorth.scoreware.data.MatchStrings;
import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.Result;
import com.truenorth.scoreware.data.Enums.ResultHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.*;

import org.apache.commons.lang3.ArrayUtils;


public class UnknownResultParser extends ResultParser
{
	TreeMap<Enums.ResultHeader, Integer> sorted_map;
	ArrayList<Enums.ResultHeader> resultHeader=new ArrayList<Enums.ResultHeader>();
	String[] split;
	
	/**
	 * **********************************************************************************
	 * The header is the structure that explains the data IE
	 * Place Age Sex City Time 
	 * 
	 * The header may or may not be present.  The header may or may not be complete
	 * 
	 * this program uses 3 ways to try and determine the header
	 * 
	 * 1.  Find a line in the form of "Place Age Sex City Time" and simply parse that line
	 * 2.  In case of partial header try and parse the first line and make corrections to the header on the fly
	 * 3.  Analyze the entire data table to try and figure out what each collumn is (for example a collumn
	 *  with numbers from 8 to 84 following a Guassian with median 45 is likely age.  A collumn with number 1 to 99
	 *  in ascending order is likely place.  etc.
	 */
	
	/**
	 * takes a quick first pass at parsing the header by simply looking
	 * for certain strings.  If it works great...  later (after extracting the 
	 * data table) there will be a second pass at forming a header.  
	 * 
	 * If we have a clean header the first pass should work... if not we need to 
	 * actually look at the data to correct ommissions and mistakes in the header.
	 * @param headerLine
	 */
	public void parseHeaderWithHeader(String headerLine)
	{
		Map<Enums.ResultHeader, Integer> map=new HashMap<Enums.ResultHeader, Integer>();
		
		// find place
		int index=findHeaderPosition(headerLine, HeaderStrings.getPlaceStrings());	
		
		// make assumption that place is first if it is not found...
		// (when we find a case that this is not true... and will have to make 
		//  this logic more robust)
		if (index==-1) index=0;
		
		map.put(Enums.ResultHeader.PLACE, index);	
		System.out.println("header: place: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getNumberStrings());	
		map.put(Enums.ResultHeader.NUMBER, index);	
		System.out.println("header: number: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getFirstNameStrings());
		map.put(Enums.ResultHeader.FIRSTNAME, index);
		System.out.println("header: fname: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getLastNameStrings());
		map.put(Enums.ResultHeader.LASTNAME, index);	
		System.out.println("header: lname: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getFullNameStrings());
		map.put(Enums.ResultHeader.FULLNAME, index);	
		System.out.println("header: full name: "+index);
	
		index=findHeaderPosition(headerLine, HeaderStrings.getGenderStrings());
		map.put(Enums.ResultHeader.SEX, index);	
		System.out.println("header: gender: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getAgeStrings());
		map.put(Enums.ResultHeader.AGE, index);	
		System.out.println("header: age: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getCityStrings());
		map.put(Enums.ResultHeader.CITY, index);	
		System.out.println("header: city: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getStateStrings());
		map.put(Enums.ResultHeader.STATE, index);	
		System.out.println("header: state: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getGunTimeStrings());
		map.put(Enums.ResultHeader.GUN_TIME, index);	
		System.out.println("header: gtime: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getChipTimeStrings());
		map.put(Enums.ResultHeader.CHIP_TIME, index);	
		System.out.println("header: ctime: "+index);
		
		index=findHeaderPosition(headerLine, HeaderStrings.getPaceStrings());
		map.put(Enums.ResultHeader.PACE, index);	
		System.out.println("header: pace: "+index);
	
		System.out.println(map);
		
		ValueComparator bvc =  new ValueComparator(map);
        sorted_map = new TreeMap<Enums.ResultHeader, Integer>(bvc);

        System.out.println("unsorted map: "+map);
        sorted_map.putAll(map);
        
        Iterator it=sorted_map.entrySet().iterator();
       
        while (it.hasNext())
        {
        	Map.Entry pairs = (Map.Entry)it.next();
        	
        	if ((Integer)(pairs.getValue())==-1)
        	{
        		it.remove();
        	}
        	else
        	{
        		resultHeader.add((Enums.ResultHeader)pairs.getKey());
        	}
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
        }
        
        
        System.out.println("results: "+sorted_map);
	
	}

	// verifies the result can be parsed, if not attempts to change the parsing map
	public boolean verify(Object line)
	{
		Result result=new Result();	
		result.setDummyValues();
	
		Iterator it=sorted_map.entrySet().iterator();
	    
		String string=(String)line;
		string=string.trim();
		split=string.split("\\s+");
		
		int i=0;
		int passes=0;
		
		Map.Entry pairs=null;
		Enums.ResultHeader rh=null;
		Enums.ResultHeader lastRh=null;
			
		boolean success=true;
		
		ArrayList<Enums.ResultHeader> newHeaderList=new ArrayList<Enums.ResultHeader>();
		
		try
		{
			while(split.length!=0)
			//while (it.hasNext())
			{
				if (passes>20) return false;
				// if last header was successfully parsed go on to the next one
				if (success)
				{
					lastRh=rh;
					//pairs = (Map.Entry)it.next();
					//rh=(Enums.ResultHeader)(pairs.getKey());
					rh=resultHeader.get(i);
				}
				
				// if we have full name it can be in different formats so check
				if (rh==Enums.ResultHeader.FULLNAME)
				{
					rh=fullNameVerify();
					
					if (rh==Enums.ResultHeader.FULLNAME)
					{
						resultHeader.set(i, Enums.ResultHeader.FIRSTNAME);
						resultHeader.add(i+1, ResultHeader.LASTNAME);
					
					}
					else if (rh==Enums.ResultHeader.LAST_COMMA_FIRSTNAME)
					{
						resultHeader.set(i, Enums.ResultHeader.LASTNAME);
						resultHeader.add(i+1, ResultHeader.FIRSTNAME);
					
					}
				}
				        	
				success=parseSubString(rh, result, split[0], string);
				
				if (success)
				{
					split=ArrayUtils.remove(split, 0);
					newHeaderList.add(rh);
				}
				
				if (success==false)
				{
					// we could be missing data or we could have to much...
					
					// first off let's check if we can identify exactly what it is
					Enums.ResultHeader q=this.whatIsIt();
					
					if (q!=null)
					{
						resultHeader.add(i,q);
						newHeaderList.add(q);
						
						Enums.ResultHeader t=resultHeader.get(i);
						
						success=parseSubString(q, result, split[0], string);
						
						if (success)
						{
							i++;
							split=ArrayUtils.remove(split, 0);
						}
						else
						{
							// Todo: handle the failure at this point
						}
					}
				}
				else
				{
					i++;
				}
				
				passes++;
			}
        }
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	
	public Result parseResultFromLine(Object line)
	{
		Result result=new Result();
		
		result.setDummyValues();
		
		//Iterator it=sorted_map.entrySet().iterator();
	    
		String string=(String)line;
		string=string.trim();
		split=string.split("\\s+");
		
		int expectedSize=resultHeader.size();
		int actualSize=split.length;
		
		int i=0;
		int passes=0;
		
	//	Map.Entry pairs=null;
		Enums.ResultHeader rh=null;
		Enums.ResultHeader lastRh=null;
			
		boolean success=true;
		
		try
		{
			while(split.length!=0)
			//while (it.hasNext())
			{
				if (passes>expectedSize+10) break;
				// if last header was successfully parsed go on to the next one
				if (success)
				{
					lastRh=rh;
					//pairs = (Map.Entry)it.next();
					//rh=(Enums.ResultHeader)(pairs.getKey());
					rh=resultHeader.get(i);
				}
					        	
				success=parseSubString(rh, result, split[0], string);
					
				if (success)
				{
					split=ArrayUtils.remove(split, 0);
				}
			
				if (success==false)
				{
					// we could be missing data or we could have too much...
					
					// first off let's check if we can identify  what it actually is
					Enums.ResultHeader actualRh=this.whatIsIt();
					
					// also see if we can find out the type
					dataFormats df=this.whatType(split[0]);
					
					Enums.ResultHeader nextRh=null;
					
					//3 cases not enough data... to much data... or the right amount
					
					// see what we would be expecting next
					if (i+1<resultHeader.size())
					{
						nextRh=resultHeader.get(i+1);
					}
					
					// 1 not enough data
					if (actualSize<expectedSize)
					{
						// if next exists (not at end) try parsing this
						if (nextRh!=null)
						{
							success = parseSubString(nextRh, result, split[0], string);
							
							// if it worked
							if (success) i++;
						}
					}
					// to much data
					else if (actualSize>expectedSize)
					{
					
						if ( (lastRh==Enums.ResultHeader.LASTNAME) || (lastRh==Enums.ResultHeader.FULLNAME))
						{
							String lastName=result.getRacer().getLastName()+" "+split[0];
							result.getRacer().setLastName(lastName);
							split=ArrayUtils.remove(split, 0);
						}
						else if (lastRh==Enums.ResultHeader.CITY)
						{
							String city=result.getRacer().getCity()+" "+split[0];
							result.getRacer().setCity(city);
							split=ArrayUtils.remove(split, 0);
						}
						else
						{
							// just keep going
							split=ArrayUtils.remove(split, 0);
						}
					}
					else
					{
						
					}
				}
				else
				{
					// on to next
					i++;
				}
				
				passes++;
			}// end while
        }// end try
		catch(Exception ex)
		{
			// if we partially parsed it try and recover
			if (i>2)
			{
				return result;
				//return recover(result, split);
			}
			return null;
		}
		
		return result;
	}
	
	boolean parseSubString(Enums.ResultHeader rh, Result result, String s, String line)
	{
		boolean success= true;
		
		if (rh==Enums.ResultHeader.PLACE)
		{
			result.setOverallPlace(Integer.parseInt(s));
			success=true;
		}
		else if (rh==Enums.ResultHeader.FIRSTNAME)
		{
			s=s.replace(",","");
			result.getRacer().setFirstName(s);
			success=true;
		}
		else if (rh==Enums.ResultHeader.LASTNAME)
		{
			s=s.replace(",","");
			result.getRacer().setLastName(s);
			success=true;
		}
		else if (rh==Enums.ResultHeader.AGE)
		{
			try
			{
				result.getRacer().setAge(Integer.parseInt(s));
				success=true;
			}
			catch (Exception e)
			{
				success=false;
				System.out.println("age not parsed from: "+line);
			}
		}
		else if (rh==Enums.ResultHeader.SEX)
		{
			if (s.equals("M"))
			{
				result.getRacer().setSex(Racer.Sex.MALE);
				success=true;
			}
			else if (s.equals("F"))
			{
				result.getRacer().setSex(Racer.Sex.FEMALE);
				success=true;
			}
			else
			{
				System.out.println("sex not parsed from: "+line);
				success=false;
			}
		}
		else if (rh==Enums.ResultHeader.SEXAGE)
		{
			String noWhite=s.trim();
			
			String sex=noWhite.substring(0,1);
			
			String age=noWhite.substring(1,noWhite.length());
			
			if (sex.toLowerCase().equals("w") || sex.toLowerCase().equals("f"))
			{
				result.getRacer().setSex(Racer.Sex.FEMALE);
			}
			else if (sex.toLowerCase().equals("m"))
			{
				result.getRacer().setSex(Racer.Sex.MALE);
			}
			
			try
			{
				int intAge=Integer.parseInt(age);
				
				result.getRacer().setAge(intAge);
			}
			catch(Exception e)
			{
				System.out.println("sexage not parsed from: "+line);
				success=false;
			}
		}
		else if (rh==Enums.ResultHeader.CITY)
		{
			result.getRacer().setCity(s);
			success=true;
		}
		else if (rh==Enums.ResultHeader.STATE)
		{
			if (s.length()==2)
			{
				result.getRacer().setState(s);
				success=true;
			}
			else
			{
				System.out.println("state not parsed from: "+line);
				success=false;
			}
		}
		else if (rh==Enums.ResultHeader.GUN_TIME)
		{
			Date date=DateTimeParser.getTime(s);
			
			if (date!=null)
			{
				result.setGunTimeString(s);
				success=true;
			}
			else
			{
				System.out.println("time not parsed from: "+line);
				success=false;
				//result.setChipTimeString("none");
			}
		}
		else
		{
			// we don't no how to parse this so just skip
		}
		
		return success;
	}
	
	private Result recover(Result result, String[] split)
	{
		return result;
	}
	
	Enums.ResultHeader whatIsIt()
	{
		String splitString="";
		
		for (String s:split)
		{
			splitString+=s+" ";
		}
		
		splitString=splitString.trim();
		
		dataFormats[] df=whatType(split);
		// try and see if this is SEXAGE together
		// looking for a format SXX (sex age, M35, W28, M71)
		if (split[0].length()==3)
		{
			Pattern p=Pattern.compile("^[MFWmfw][0-9]{2}$");
			
			Matcher m=p.matcher(split[0]);
			
			if (m.matches())
			{
				return Enums.ResultHeader.SEXAGE;
			}
		}
		
		// test to see if it a city
		for (String s:MatchStrings.getCityStrings())
		{
			int index=splitString.indexOf(s);
			
			if (index==0)
			{
				return Enums.ResultHeader.CITY;
			}
		}
		
		// test to see if it is state
		// (2 capitol letters for now, more complex search later)
		if (split[0].length()==2)
		{
			Pattern p=Pattern.compile("^[A-Z]{2}$");
			
			Matcher m=p.matcher(split[0]);
			
			
			
			if (m.matches())
			{
				return Enums.ResultHeader.STATE;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Goes through each piece of data and attempts to identify what it is.
	 * Default is that the data is string, tries to find first name, last names, cities, states
	 * numbers, times.
	 * @param split
	 * @return
	 */
	dataFormats[] whatType(String[] split)
	{
		dataFormats[] df=new dataFormats[split.length];
		
		int i=0;
		for (String s:split)
		{
			df[i]=whatType(s);
			
			i++;
		}
		
		return df;
	}
	
	private Enums.ResultHeader fullNameVerify()
	{
		String splitString="";
		
		for (String s:split)
		{
			splitString+=s+" ";
		}
		
		boolean comma=false;
		
		// test to see if it is comma delimineted
		char test=split[0].charAt(split[0].length()-1);
		if (test==',')
		{
			comma=true;
		}
		
		boolean lastFirst=false;
			
		// test to see where the first name is
		for (String s:MatchStrings.getFirstNameStrings())
		{
			int index=splitString.indexOf(s);
					
			if (index==0)
			{
				break;
			}
			else if (index>0)
			{
				lastFirst=true;
				break;
			}
		}
		
		if ( (comma)&&(lastFirst) )
		{
			return Enums.ResultHeader.LAST_COMMA_FIRSTNAME;
		}
		
		return Enums.ResultHeader.FULLNAME;
	}
	
	dataFormats whatType(String s)
	{
		boolean success=false;
		
		// is it a integer??? try and parse as int
		try
		{
			Integer.parseInt(s);
			return dataFormats.n;
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
				return dataFormats.mm_ss;
			}
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
				return dataFormats.chars_comma;
			}
		}
		
		// default to just a char
		return dataFormats.chars;
		
	}
	
	enum dataFormats
	{
		n, // integer number
		mm_ss, // mm_ss time,
		chars_comma,
		chars // characters		
	}
}
