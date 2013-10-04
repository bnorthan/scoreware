package com.truenorth.scoreware.writers;

import com.truenorth.scoreware.ScorewareWriter;

import java.io.*;

public class FileWriter extends ScorewareWriter
{
	public FileWriter(String destination)
	{
		super(destination);
		
		try
		{
			out=new PrintStream(new FileOutputStream(destination));
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	public void Write()
	{
		
	}
}
