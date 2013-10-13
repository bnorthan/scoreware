package com.truenorth.scoreware.writers;

import java.sql.Connection;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;

public class JDBCSqlWriter extends SqlWriter
{
	String url="jdbc:mysql://bnorthan2.db.10408148.hostedresource.com:3306/bnorthan2";
	String username="bnorthan2";
	String password="Ba3542b36!";
	
	Connection connection=null;
	
	String tableName=null;
	
	public JDBCSqlWriter(String tableName)
	{
		super(null);
		
		this.tableName=tableName;
		
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
		
		makeTable();
	}
	
	
	
	public void makeTable()
	{
		try
		{
			if (connection!=null)
			{
				Statement statement=connection.createStatement();
			
				//String query = "CREATE TABLE "+tableName+ " (Name CHAR(50), CAT CHAR(20), POINTS INT)";
				String query = "CREATE TABLE "+tableName+ " (Name CHAR(50), PLACE INT)";
				
				statement.execute(query);
			}
		}
		catch(Exception e)
		{
			System.out.println("could not create table: "+e.getMessage());
		}
	}
	
	public void writeRacer(Racer racer)
	{
		try
		{
			Statement statement=connection.createStatement();
			
			String racerName=racer.getFirstName()+" "+racer.getLastName();
			
		
			String query="INSERT INTO "+tableName+" VALUES (";
			query+="'"+racer.getFirstName()+"', '"+racer.getLastName()+"', '"+racer.getCity()+"')";
			
			statement.execute(query);
		}
		catch(Exception e)
		{
			System.out.println("some bad thing happened! "+e.getMessage());
		}
		
	}
	
	public void writeResult(Result result)
	{
		try
		{
			Statement statement=connection.createStatement();
			
			String racerName=result.getRacer().getFirstName()+" "+result.getRacer().getLastName();
			
			//String query="INSERT INTO "+tableName+" VALUES (";
			//query+="'"+racerName+"', '"+result.getCategoryString()+"', '"+ result.getPoints()+"')";
			
			String query="INSERT INTO "+tableName+" VALUES (";
			query+="'"+racerName+"', '"+result.getOverallPlace()+"')";
			
			statement.execute(query);
		}
		catch(Exception e)
		{
			System.out.println("some bad thing happened!"+e.getMessage());
		}
		
	}


	public Connection getConnection() throws SQLException, IOException
	{
		System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);
	}
}
