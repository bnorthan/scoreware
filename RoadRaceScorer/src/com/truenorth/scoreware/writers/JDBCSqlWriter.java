package com.truenorth.scoreware.writers;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;

import java.util.Date;
import java.text.SimpleDateFormat;

public class JDBCSqlWriter extends SqlWriter
{
	//String url="jdbc:mysql://bnorthan2.db.10408148.hostedresource.com:3306/bnorthan2";
	//String username="bnorthan2";
	//String password="Ba3542b36!";
	
	String url="jdbc:mysql://scoreware2.db.11972874.hostedresource.com:3306/scoreware3";
	String username="scoreware3";
	String password="Ba3542b36!";
	
	public JDBCSqlWriter()
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
	
	}
	
	
	public Connection getConnection() throws SQLException, IOException
	{
		System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}
}
