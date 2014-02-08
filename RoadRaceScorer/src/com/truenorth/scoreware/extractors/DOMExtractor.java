package com.truenorth.scoreware.extractors;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.truenorth.scoreware.data.HeaderStrings;

public class DOMExtractor extends TableExtractor
{
	public Elements getMainTable()
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
		
		// Todo: this routine needs some error checking for the case of
		// multiple tables
		
		// get the table elements
		Elements elements=doc.select("table");
		
		return elements;
	}
	
}

