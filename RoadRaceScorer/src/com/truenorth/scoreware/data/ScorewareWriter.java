package com.truenorth.scoreware.data;

import java.io.*;

public abstract class ScorewareWriter 
{
	String destination;
	
	protected PrintStream out;
	
	public ScorewareWriter(String destination)
	{
		this.destination=destination;
		out=System.out;
	}
	
	// implement some default functions that just write 
	// to the system out
	
	public void writeRacer(Racer racer)
	{
		out.println(racer);
	}
	
	public void writeResult(Result result)
	{
		out.println(result);
	}
	
	public void writeString(String string)
	{
		out.print(string);
	}
	
	public void writeLine(String string)
	{
		out.println(string);
	}
}
