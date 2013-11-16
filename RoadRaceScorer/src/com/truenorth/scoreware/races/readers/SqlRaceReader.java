package com.truenorth.scoreware.races.readers;

import com.truenorth.scoreware.Race;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.races.parsers.SqlResultParser;
import com.truenorth.scoreware.common.utility.DateTimeParser;

public class SqlRaceReader extends RaceReader
{
	Connection connection;
	
	public SqlRaceReader(Race race)
	{
		super(race);
		
		this.resultParser=new SqlResultParser();
	}
	
	public void read()
	{
		// the source should be a properties file telling us how to connect to the database
		Properties props=new Properties();
		
		try 
		{
			InputStream in=Files.newInputStream(Paths.get(race.getSourceName()));
			
			props.load(in);
		}
		catch(Exception e)
		{
			
		}
		
		String drivers=props.getProperty("jdbc.drivers");
		String url=props.getProperty("jdbc.url");
		String user=props.getProperty("jdbc.username");
		String password=props.getProperty("jdbc.password");
		String id=props.getProperty("race");
		
		System.out.println(drivers);
		System.out.println(url);
		System.out.println(user);
		System.out.println(password);
		System.out.println(id);
		
		race.setIdentifier(id);
		
		Statement statement;
		
		// connect to database
		try
		{
			System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(url, user, password);
			statement=connection.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Database error "+e.getMessage());
			return;
		}
		
		// attempt to get race info
		String query="SELECT * FROM races WHERE Identifier='"+id+"'";
		
		try
		{
			ResultSet rs=statement.executeQuery(query);
			
			while(rs.next())
			{
				race.setName(rs.getString("Name"));
				race.setCity(rs.getString("City"));
				race.setState(rs.getString("State"));
				race.setCountry(rs.getString("Country"));
				
				String strDate=rs.getString("Date");
				Date date=DateTimeParser.getDateTimeFromSql(strDate);
				race.setDate(date);
				
				String timedBy=rs.getString("Timer");
				race.setTimedBy(timedBy);
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		query="SELECT * FROM "+id;
		
		try
		{
			ResultSet rs=statement.executeQuery(query);
			
			while(rs.next())
			{
				Result result=resultParser.parseResultFromLine(rs);
				
				results.add(result);
			}
		}
		catch(Exception e)
		{
			System.out.println("could not read race results from database"+e.getLocalizedMessage());
			return;
		}
		
		for (Result result:results)
		{
			System.out.println(result);
		}
	
	}
	
	public void ReadRaceHeader()
	{
		
	}

}
