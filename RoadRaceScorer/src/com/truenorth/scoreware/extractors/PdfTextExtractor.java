package com.truenorth.scoreware.extractors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import java.io.*;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.icepdf.core.pobjects.graphics.text.LineText;

public class PdfTextExtractor extends TextExtractor
{
	public ArrayList<String> extractText(String name)
	{
		try
		{
			System.out.println("pdf detected!");
			
			ArrayList<String> text=new ArrayList<String>();
			
			   URL documentURL = new URL("file:/"+name);
			   Document document = new Document();
			   document.setUrl( documentURL);
			   
			   // create an output file
			   //FileOutputStream fileOutputStream = new FileOutputStream( "extracted.txt");
			   
			   int i=0;
			   
			   int np=document.getNumberOfPages();
			   
			   PageText pageText;
			   
			   while ( (pageText = document.getPageText(i) ) != null)
			   {
				   System.out.print(i+"... ");
				   pageText = document.getPageText(i);
				   
				   ArrayList<LineText> lineText = pageText.getPageLines();
				   
				   String s="";
				   float lastY=lineText.get(0).getBounds().y;
				   
				   // go through each line on the page
				   for (LineText lt:lineText)
				   {
					   if (lastY!=lt.getBounds().y)
					   {
						   System.out.println(s);
						   text.add(s);
						   s="";
						   lastY=lt.getBounds().y;
					   }
					   else
					   {
						   s+=" ";
					   }
					   //System.out.println(lt.getBounds());
					   //System.out.println(lt.toString());
					   
					   String t=""; 
					   for (Object obj:lt.getWords().toArray())
					   {
						   t+=obj.toString();
					   }
					   
					   s+=t;
				   }
				   
				   // add the last one
				   text.add(s);
				   //System.out.println(pageText);
				   /*String stringText=pageText.toString();
				   
				   String split[]=stringText.split("[\\r\\n]+");
				  
				   for (String s:split)
				   {
					   text.add(s);
				   }*/
				   
				   i++;
				   
				   
			   }
			   return text;
			   			} 
			catch (Throwable e) 
			{
			   e.printStackTrace();
			   return null;
			} 
		
		}
}
