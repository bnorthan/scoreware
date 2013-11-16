package com.truenorth.scoreware.writers;

import java.sql.Connection;
import java.sql.Statement;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.ScorewareWriter;
import com.truenorth.scoreware.Race;
import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.sql.RunwareQuery;

public abstract class SqlWriter extends ScorewareWriter
{
	Connection connection=null;
	String tableName=null;
	String picturesTable=null;
	String splitsTable=null;
	
	String propertiesFile=null;
	
	public SqlWriter()
	{
		super(null);
	}
	
	// creates the races table
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
	
	// checks to see if a race exists
	public boolean doesRaceExist(Race race)
	{
		String id=race.getIdentifier();
		
		String query = "SELECT * FROM races WHERE Identifier='"+id+"'";
		
		try
		{
			Statement statement=connection.createStatement();
			ResultSet rs=statement.executeQuery(query);
					
			while(rs.next())
			{
				return true;
			}
			
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	// creates the table that stores the race
	public void CreateRaceTable(Race race)
	{
		if (connection!=null)
		{
			try
			{
				Statement statement=connection.createStatement();
				
				String query = "CREATE TABLE "+race.getIdentifier()+ " (FirstName CHAR(50), LastName CHAR(50), Place INT, Age INT, Sex CHAR(1), Time DATETIME, Pace DATETIME, Category CHAR(20), City CHAR(40), State CHAR(2), Points INT, Club CHAR(40) )";
				
				System.out.println(query);
				
				try
				{
					statement.execute(query);
				}
				catch(Exception e)
				{
					System.out.println("could not create race table: "+e.getMessage());
				}
				
				picturesTable=race.getIdentifier()+"_pics";
				
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
				
				String raceName=race.getName();
				
				// replace single quotes with two single quotes for SQL
				raceName=raceName.replace("'", "''");
				
				//int year=race.getDate().getYear()+1900;
				
				//String dateString="2013-10-13";
				//String dateString=year+"-"+race.getDate().getMonth()+"-"+race.getDate().getDay();
				String dateString=DateTimeParser.makeSqlDateString(race.getDate());
				String timedBy=race.getTimedBy();
				
				query = "INSERT INTO "+"races"+" VALUES (";
				query+="'"+race.getIdentifier()+"', '"+raceName+"', '"+race.getCity()+"', '"
					+race.getState()+"', '"+race.getCountry()+"', '"+dateString+"', '0', '"+timedBy+"')";
				
				try
				{
					statement.execute(query);
				}
				catch(Exception e)
				{
					String q=e.getMessage();
					
					System.out.println("could not insert race: "+e.getMessage());
				}
				
				String propertiesDir=race.getOutputPath();
				
				if (propertiesDir!=null)
				{
					String fileName=propertiesDir+"\\"+race.getIdentifier()+".properties";
					
					Properties prop=getConnectionProperties();
					
					try
					{
						prop.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
						prop.setProperty("jdbc.url", "jdbc:mysql://scoreware3.db.11972874.hostedresource.com:3306/scoreware3");
						prop.setProperty("jdbc.username", "scoreware3");
						prop.setProperty("jdbc.password","Ba3542b36!");
						prop.setProperty("race",race.getIdentifier());
					
						prop.store(new FileOutputStream(fileName), null);
					}
					catch(Exception e)
					{
						
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("sql error!");
			}
		}
	}
	
	// writes the results to the race table
	public void writeResults(ArrayList<Result> results)
	{
		Statement statement;
		
		// try to create a statement
		try
		{
			statement=connection.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("sql connection error!");
			return;
		}
		
		// loop through all the results
		for (Result result:results)
		{	
			try
			{
				// for each result create a query
				String query=createResultQuery(result);
				// add query to the batch
				statement.addBatch(query);
			}
			catch(Exception e)
			{
				// if something went wrong diplay a message to the user 
				JOptionPane.showMessageDialog(null, "query not batched: "+e.getMessage());
			}
		}
		
		// execute the batch
		try
		{
			statement.executeBatch();
			statement.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "error executing batch: "+e.getMessage());
		}
	}
		
	// run given query on all the results
	public void RunQueriesOnResults(ArrayList<Result> results, RunwareQuery runwareQuery)
	{
		Statement statement;
		
		try
		{
			statement=connection.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("sql connection error!");
			return;
		}
		
		for (Result result:results)
		{
			
			// create the query for the result
			String query=runwareQuery.makeResultQuery(result, tableName);
			
			try
			{
				// add query to the batch
				statement.addBatch(query);
			}
			catch(Exception e)
			{
				
			}
		}
		
		// execute the batch
		try
		{
			statement.executeBatch();
			statement.close();
		}
		catch(Exception e)
		{
			System.out.println("batch update failed: "+e.getMessage());
		}
	}

	// write a single result to the database
	public void writeResult(Result result)
	{
		try
		{
			// create the query
			String query=createResultQuery(result);
			
			// 
			Statement statement=connection.createStatement();
			
			statement.execute(query);
		}
		catch(Exception e)
		{
			System.out.println("some bad thing happened!"+e.getMessage());
		}
		
	}
	
	// create a query for the result (this writes all data) 
	protected String createResultQuery(Result result)
	{
		String firstName=result.getRacer().getFirstName();
		String lastName=result.getRacer().getLastName();
		lastName=lastName.replace("'", "''");
		
		int place=result.getOverallPlace();
		int age=result.getRacer().getAge();
		
		String gender;
		
		if (result.getRacer().getSex()==Racer.Sex.MALE)
		{
			gender="M";
		}
		else
		{
			gender="F";
		}
		
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		String time;
		
		if (result.getChipTime()!=null)
		{
			time=format.format(result.getChipTime());
		}
		else
		{
			time=format.format(result.getGunTime());
		}
		
		time="2000-01-01 "+time;
		
		String pace="00:00:00";
		
		System.out.println("time");
		
		String city=result.getRacer().getCity();
		String state=result.getRacer().getState();
		
		if (state==null)
		{
			state="XX";
		}
		
		String club=result.getRacer().getCurrentClub();
		
		String query="INSERT INTO "+tableName+" VALUES (";
		
		query+="'"+firstName+"', '"+lastName+"', '"+place+"', '"+age+"', '"+gender+"', '"+time+"', '"
				+pace+"', '"+result.getCategoryString()+"', '"+city+"', '"+state+"', '"+result.getPoints()+"', '"+club+"')";
		
		System.out.println(query);
		//String query = "CREATE TABLE "+race.getIdentifier()+ " (FirstName CHAR(50), LastName CHAR(50), Place INT, Age INT, Time DATETIME, Pace DATETIME, Category CHAR(20), City CHAR(40), State CHAR(2), Points INT)";
		
		return query;
	}
	
	public void writePictureNames(String pictureNames[])
	{
		Statement statement;
		
		try
		{
			statement=connection.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("sql connection error!");
			return;
		}
	
		for (String pictureName:pictureNames)
		{
			String query="INSERT INTO "+picturesTable+" VALUES ('"+pictureName+"')";
		
			try
			{
				statement.addBatch(query);
			}
			catch(Exception e)
			{			
			}
		}
		
		try
		{
			statement.executeBatch();
			statement.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void setTableName(String tableName)
	{
		this.tableName=tableName;
	}
	
	public void setPropertiesFile(String propertiesFile)
	{
		this.propertiesFile=propertiesFile;
	}

	public abstract Connection getConnection() throws SQLException, IOException;
	
	public abstract Properties getConnectionProperties();
}
