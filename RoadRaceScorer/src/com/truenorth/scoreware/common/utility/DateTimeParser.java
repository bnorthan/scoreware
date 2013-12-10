package com.truenorth.scoreware.common.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tries to parse the date using a variety of possible formats
 * @author bnorthan
 *
 */
public class DateTimeParser 
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
	
	public static String makeSqlDateString(Date date)
	{
		try
		{
			DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String result = df.format(date);
			
			return result;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static int howOldInYears(Date birthday, Date today)
	{
		//System.out.println(birthday+" "+today);
		long howOldInMils=today.getTime()-birthday.getTime();
		int howOldInYears=(int)(howOldInMils/(1000*60*60*24*365.25));
		
		return howOldInYears;
	}
	
	public static Date getDateTimeFromSql(String dateTimeString)
	{
		Date result=null;
		
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			
			result =  df.parse(dateTimeString);
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static Date getTime(String timeString)
	{
		Date result=null;
		
		try
		{
			// look for the colon 
			int count=timeString.split(":").length-1;
					
			// if one ":" was found try to parse as mm:ss
			if (count==1)
			{
				DateFormat df = new SimpleDateFormat("mm:ss");
				result =  df.parse(timeString);
				return result;
			}
			// if two ":" are found try to parse as HH:mm:ss
			else if (count==2)
			{
				DateFormat df = new SimpleDateFormat("HH:mm:ss");
				result =  df.parse(timeString);
				return result;
			}
			// todo: other parse strategies
				
		}
		catch (Exception ex)
		{
			// if things go wrong return null
			return null;
		}
		
		return null;
	}
	
	public static String DateToMmSsString(Date date)
	{
		DateFormat format=new SimpleDateFormat("mm:ss");
	
		try
		{
			return (format.format(date));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String DateToHhMmSsString(Date date)
	{
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
	
		try
		{
			return (format.format(date));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String DateToTimeString(Date date)
	{
		DateFormat format_hhmmss=new SimpleDateFormat("HH:mm:ss");
		
		try
		{
			String hhmmss= format_hhmmss.format(date);
			
			String[] split=hhmmss.split(":");
			
			if (split[0].equals("00"))
			{
				DateFormat format_mmss=new SimpleDateFormat("mm:ss");
				
				return format_mmss.format(date);
			}
			
			return hhmmss;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	
	
}
