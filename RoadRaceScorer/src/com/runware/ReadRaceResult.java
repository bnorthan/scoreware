package com.runware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;

public class ReadRaceResult 
{
	public void Read(String strURL)
	{
		try
		{
			URL url = new URL(strURL);
			
			URLConnection yc = url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			
			String inputLine;
			
			String html="";
			
			int line=1;
			
			while ((inputLine=in.readLine())!=null)
			{
				
				//System.out.println(line+": "+inputLine);
				System.out.println(line+": "+Jsoup.parse(inputLine).text());
				
				html=html+inputLine+"\n";
				
				line++;
			
			}
			
			System.out.println();
			System.out.println("parsing HTML");
			System.out.println();
			
			String parsed = Jsoup.parse(html).text();
			
			System.out.print(parsed);
		}
		catch (Exception e)
		{
			
		}
		
	}

}
