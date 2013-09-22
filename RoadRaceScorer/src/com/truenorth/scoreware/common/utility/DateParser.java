package com.truenorth.scoreware.common.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tries to parse the date using a variety of possible formats
 * @author bnorthan
 *
 */
public class DateParser 
{
	public static Date getDate(String dateString)
	{
		if (dateString.equals(""))
		{
			Date date=new Date();
			return date;
		}
		dateString=dateString.toLowerCase();
		dateString=dateString.replace("sept ", "sep ");
		
		Date result=null;
		try
		{
			DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
			result =  df.parse(dateString);
			return result;
		}
		catch (Exception ex)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd MMM. yyyy");
			result =  df.parse(dateString);
			return result;
		}
		catch (Exception ex)
		{
		}
		
		// if we got this far need to try something else
		try
		{
			DateFormat df = new SimpleDateFormat("MMMMMMMM dd, yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MM//dd/yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("ddMMMyyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MMMMMMMMMM dd yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd, MMMMMM, yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MMddyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MMddyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("ddMMM.yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("MMM. dd yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd MMMyyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd-MMMMMMyyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		try
		{
			DateFormat df = new SimpleDateFormat("dd MMMMMM yyyy");
			result = df.parse(dateString);
			return result;
		}
		catch (Exception ex2)
		{
		}
		
		//System.out.print("****"+"nothing worked!!"+"****");
		
		return null;
	}
	
	public static int howOldInYears(Date birthday, Date today)
	{
		//System.out.println(birthday+" "+today);
		long howOldInMils=today.getTime()-birthday.getTime();
		int howOldInYears=(int)(howOldInMils/(1000*60*60*24*365.25));
		
		return howOldInYears;
	}
	
}
