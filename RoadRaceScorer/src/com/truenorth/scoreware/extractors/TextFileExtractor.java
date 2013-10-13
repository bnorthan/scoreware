package com.truenorth.scoreware.extractors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;

public class TextFileExtractor extends TextExtractor
{
	public ArrayList<String> extractText(String name)
	{
		try
		{
			ArrayList<String> strings=new ArrayList<String>();
			
			BufferedReader in;
		
			in = new BufferedReader(new FileReader(name));
			
			String inputLine;
		
			int line=1;
	
			while ((inputLine=in.readLine())!=null)
			{		
				strings.add(inputLine);
				System.out.println(inputLine);
			}
			
			return strings;
		}
		catch(Exception ex)
		{
			System.out.println("problem occurred!");
			return null;
		}
	}
	

}
