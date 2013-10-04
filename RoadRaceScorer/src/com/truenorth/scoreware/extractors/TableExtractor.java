package com.truenorth.scoreware.extractors;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableExtractor 
{
	public static Elements getTable(String sourceName, boolean onLine)
	{
		System.out.println("Table extractor!");
		
		Document doc;
		
		try
		{
			if (onLine)
			{
				doc=Jsoup.connect(sourceName).get();
			}
			else
			{
				File in=new File(sourceName);
				doc=Jsoup.parse(in,"UTF-8","");
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}

		Elements elements=doc.select("table");
		System.out.println("num tables are "+elements.size());
		
		Elements tableHeaders=elements.select("thead tr th");
		System.out.println("num headers are: "+tableHeaders.size());
		
		for (int i=0;i<tableHeaders.size();i++)
		{
			System.out.println(tableHeaders.get(i).text());
		}
		
		Elements notHeaders=elements.select(":not(thead) tr");
		System.out.println("num rows are: "+notHeaders.size());
		
		for (int i=0;i<1;i++)
		{
			System.out.println(notHeaders.get(i).text());
		}
		
		return notHeaders;
	}
}
