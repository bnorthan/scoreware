package com.truenorth.scoreware.extractors;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;

/**
 * Converts html to text
 * @author bnorthan
 *
 */
public class HtmlExtractor implements TextExtractor
{
	boolean keepSpaces=false;
	boolean online=true;
	
	public ArrayList<String> extractText(String name)
	{
		try
		{
			ArrayList<String> strings=new ArrayList<String>();
			
			BufferedReader in;
			
			if (online)
			{
				URL url = new URL(name);
				URLConnection yc = url.openConnection();
		
				in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			}
			else
			{
				in = new BufferedReader(new FileReader(name));
			}
			
			String inputLine;
		
			String html="";
		
			int line=1;
	
			while ((inputLine=in.readLine())!=null)
			{
				String noHTMLString;
				
				// if it is desired to keep the spaces then use the regex method to parse out html
				if (keepSpaces)
				{
					noHTMLString = inputLine.replaceAll("\\<.*?\\>", "");
				}
				// otherwise use Jsoup which parses out html and whitespace
				else
				{
					noHTMLString = Jsoup.parse(inputLine).text();
				}
					
				strings.add(noHTMLString);
				
				//new java.util.Scanner(System.in).nextLine();
			}
			
			return strings;
		}
		catch(Exception ex)
		{
			System.out.println("problem occurred!");
			return null;
		}
	}
	
	public void setKeepSpaces(boolean keepSpaces)
	{
		this.keepSpaces=keepSpaces;
	}
	
	public void setOnline(boolean online)
	{
		this.online=online;
	}
}
