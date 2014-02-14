package com.truenorth.scoreware.writers;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import com.truenorth.scoreware.data.Racer;
import com.truenorth.scoreware.data.Result;

import java.util.Date;
import java.text.SimpleDateFormat;

public class JDBCSqlWriter extends SqlWriter
{
	//String url="jdbc:mysql://bnorthan2.db.10408148.hostedresource.com:3306/bnorthan2";
	//String username="bnorthan2";
	//String password="Ba3542b36!";
	
	String url="jdbc:mysql://scoreware3.db.11972874.hostedresource.com:3306/scoreware3";
	String username="scoreware3";
	String password="Ba3542b36!";
	
/*	public JDBCSqlWriter()
	{
		
		// open connection
		try
		{
			connection=getConnection();
			
			System.out.println("Opened database");
		}
		catch(Exception e)
		{
			System.out.println("Could not open database because: "+e.getMessage());
		}
	
	}*/
	
	public JDBCSqlWriter(String propertiesFile)
	{
		// read the database config from the properties file
		Properties props=new Properties();
				
		try 
		{
			InputStream in=Files.newInputStream(Paths.get(propertiesFile));
					
			props.load(in);
			
			String drivers=props.getProperty("jdbc.drivers");
			url=props.getProperty("jdbc.url");
			username=props.getProperty("jdbc.username");
			password=props.getProperty("jdbc.password");
			
			connection=getConnection();
			
			System.out.println("Opened database");
		
			
		}
		catch(Exception e)
		{
			System.out.println("Could not open database because: "+e.getMessage());
		}
				
	}
	
	public Connection getConnection() throws SQLException, IOException
	{
		System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}
	
	public Properties getConnectionProperties()
	{
		Properties prop=new Properties();
		prop.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		prop.setProperty("jdbc.url", url);
		prop.setProperty("jdbc.username", username);
		prop.setProperty("jdbc.password", password);
		
		return prop;
	}
}
