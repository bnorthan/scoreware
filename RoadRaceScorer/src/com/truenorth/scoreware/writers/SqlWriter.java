package com.truenorth.scoreware.writers;

import java.sql.Connection;
import java.sql.Statement;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.ScorewareWriter;
import com.truenorth.scoreware.Race;

public abstract class SqlWriter extends ScorewareWriter
{
	Connection connection=null;
	String tableName=null;
	
	public SqlWriter()
	{
		super(null);
	}
	
	public void CreateRacesTable(String tableName)
	{
		try
		{
			if (connection!=null)
			{
				Statement statement=connection.createStatement();
			
				//String query = "CREATE TABLE "+tableName+ " (Name CHAR(50), CAT CHAR(20), POINTS INT)";
				String query = "CREATE TABLE "+tableName+ " (Identifier CHAR(20), Name CHAR(50), City CHAR(50), "
						+ "State CHAR(2), Country CHAR(30), Date DATETIME)";
				
				statement.execute(query);
			}
		}
		catch(Exception e)
		{
			System.out.println("could not create races table: "+e.getMessage());
		}
		
	}
	
	public void CreateRaceTable(Race race)
	{
		if (connection!=null)
		{
			try
			{
				Statement statement=connection.createStatement();
				
	//			String query = "CREATE TABLE "+race.getIdentifier()+ " (Name CHAR(50), Place INT, Time CHAR(7), Category CHAR(20), Points INT)";
				String query = "CREATE TABLE "+race.getIdentifier()+ " (FirstName CHAR(50), LastName CHAR(50), Place INT, Age INT, Time DATETIME, Pace DATETIME, Category CHAR(20), City CHAR(40), State CHAR(2), Points INT)";
				
				System.out.println(query);
				
				try
				{
					statement.execute(query);
				}
				catch(Exception e)
				{
					System.out.println("could not create race table: "+e.getMessage());
				}
				
				String picturesTable=race.getIdentifier()+"_pics";
				
				query = "CREATE TABLE "+picturesTable+ " (Name CHAR(200))";
				System.out.println(query);
				
				try
				{
					statement.execute(query);
				}
				catch(Exception e)
				{
					System.out.println("could not create pictures table: "+e.getMessage());
				}
				
				tableName=race.getIdentifier();
				
				//int year=race.getDate().getYear()+1900;
				String dateString="2013-10-13";//year+"-"+race.getDate().getMonth()+"-"+race.getDate().getDay();
				
				query = "INSERT INTO "+"races"+" VALUES (";
				query+="'"+race.getIdentifier()+"', '"+race.getName()+"', '"+race.getCity()+"', '"
					+race.getState()+"', '"+race.getCountry()+"', '"+dateString+"')";
				
				try
				{
					statement.execute(query);
				}
				catch(Exception e)
				{
					System.out.println("could not insert race: "+e.getMessage());
				}
			}
			catch(Exception e)
			{
				System.out.println("sql error!");
			}
		}
	}
	
	public void writeResult(Result result)
	{
		try
		{
			Statement statement=connection.createStatement();
			
			/*String racerName=result.getRacer().getFirstName()+" "+result.getRacer().getLastName();
			
			//String query="INSERT INTO "+tableName+" VALUES (";
			//query+="'"+racerName+"', '"+result.getCategoryString()+"', '"+ result.getPoints()+"')";
			
			String query="INSERT INTO "+tableName+" VALUES (";
			
			query+="'"+racerName+"', '"+result.getOverallPlace()+"', '"+result.getChipTime()+"', '"
					+result.getCategoryString()+"', '"+result.getPoints()+"')";*/
			
			String firstName=result.getRacer().getFirstName();
			String lastName=result.getRacer().getLastName();
			int place=result.getOverallPlace();
			int age=result.getRacer().getAge();
			
			DateFormat format=new SimpleDateFormat("H:mm:ss");
			String time;
			
			if (result.getChipTime()!=null)
			{
				time=format.format(result.getChipTime());
			}
			else
			{
				time=format.format(result.getGunTime());
			}
			
			System.out.println("time");
			
			String city=result.getRacer().getCity();
			String state=result.getRacer().getState();
			
			String query="INSERT INTO "+tableName+" VALUES (";
			
			query+="'"+firstName+"', '"+lastName+"', '"+place+"', '"+age+"', '"+time+"', '"
					+null+"', '"+result.getCategoryString()+"', '"+city+"', '"+state+"', '"+result.getPoints()+"')";
			
			System.out.println(query);
			//String query = "CREATE TABLE "+race.getIdentifier()+ " (FirstName CHAR(50), LastName CHAR(50), Place INT, Age INT, Time DATETIME, Pace DATETIME, Category CHAR(20), City CHAR(40), State CHAR(2), Points INT)";
			
			statement.execute(query);
		}
		catch(Exception e)
		{
			System.out.println("some bad thing happened!"+e.getMessage());
		}
		
	}


	public abstract Connection getConnection() throws SQLException, IOException;
}
