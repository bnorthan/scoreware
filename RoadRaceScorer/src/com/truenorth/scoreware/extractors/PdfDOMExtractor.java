package com.truenorth.scoreware.extractors;

import java.net.URL;
import java.util.ArrayList;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.graphics.text.LineText;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.truenorth.scoreware.data.HeaderStrings;

public class PdfDOMExtractor extends TableExtractor
{
	public Elements getMainTable()
	{
		try
		{
			System.out.println("pdf detected!");
			
			ArrayList<String> text=new ArrayList<String>();
			
			// open the document
			URL documentURL = new URL("file:/"+sourceName);
			Document document = new Document();
			document.setUrl( documentURL);
			   
			// create an output file
			//FileOutputStream fileOutputStream = new FileOutputStream( "extracted.txt");
			   
			int i=0;
			int np=document.getNumberOfPages();
			PageText pageText;
			
			// we will place the extracted race info in an elements structure
			Elements elements=new Elements();
			boolean headerFound=false;
		    Element headerRow=null;

		    // loop through all pages of the document
			while ( (pageText = document.getPageText(i) ) != null)
			{
				System.out.print(i+"... ");
				
				// extract text from this page
				pageText = document.getPageText(i);
				ArrayList<LineText> lineText = pageText.getPageLines();
			   
				String s="";
				
				// start a new row
				Element row=new Element(Tag.valueOf("tr"),"");
				
				// keep track of y position of the text (we will start a new line when the y position changes)
				float lastY=lineText.get(0).getBounds().y;
				
				// start a new line
			    ArrayList<LineText> line=new ArrayList<LineText>();
			    
			    // Array list to put the header lines in
			    ArrayList<LineText> headerLine=null;
			    
				// first go through each line on the page until we find the header
				for (LineText lt:lineText)
				{
					line.add(lt);
					
					// if we are at a new y location
					if (lastY!=lt.getBounds().y)
					{
						text.add(s);
		
						if (headerRow!=null)
						{
							// the case where there isn't enough information
							if (row.childNodes().size()<headerRow.childNodes().size())
							{
								System.out.println(s);
								System.out.println(row);
								
								row=padElement(headerRow, row);
								System.out.println(row);
								int stop=5;
							}
						}
						
						// add the previous row to the table
						elements.add(row);
						
						// check to see if previous line was the header
						if (!headerFound)
						{
							if (ParseHeader(s, line))
							{
								System.out.println(row);
								headerFound=true;
								headerLine=line;
								headerRow=row;
							}
						}
						
						// start a new line
						s="";
						// start a new row
						row=new Element(Tag.valueOf("tr"),"");
						
						lastY=lt.getBounds().y;
						line.clear();
					}
					else
					{
						s+=" ";
					}
					//System.out.println(lt.getBounds());
					//System.out.println(lt.toString());
			   
					String words=""; 
					for (Object obj:lt.getWords().toArray())
					{
						words+=obj.toString();
					}
				   
					Element e=new Element(Tag.valueOf("td"),"");
					
					System.out.println(lt.toString());
					System.out.println(words);
					System.out.println(e);
					
					e.append(words);
					
					System.out.println("new: "+e);
					
					row.appendChild(e);
					
					s+=words;
				}
			   
				// add the last row
				text.add(s);
				
				// 
				if (row.childNodes().size()<headerRow.childNodes().size())
				{
						
					row=padElement(headerRow, row);
				}
				
				elements.add(row);
				i++;
			   
			}
			return elements;
		}
		catch (Throwable e) 
		{
		   e.printStackTrace();
		   return null;
		} 
	}
	
	private Element padElement(Element header, Element row)
	{
		int headerSize=header.childNodes().size();
		int rowSize=row.childNodes().size();
		
		int rowSize2=row.children().size();
		
		
		
		Element paddedRow=new Element(Tag.valueOf("tr"),"");
		
		int i;
		for (i=0;i<rowSize-1;i++)
		{
			int test=row.children().size();
			
			Element e=row.children().get(i);
			
			paddedRow.appendChild(e.clone());
			//Element node=row.childNodes().get(i);
			//row.ch
			//paddedRow.appendChild(row.childNodes().get(i));
		}
		
		int j;
		for(j=i;j<headerSize-1;j++)
		{
			Element e=new Element(Tag.valueOf("td"),"");
			paddedRow.appendChild(e);
		}
		
		Element e=row.children().get(i);
		paddedRow.appendChild(e.clone());
		
		return paddedRow;
		
		
	}
	
	private boolean ParseHeader(String s, ArrayList<LineText> ln)
	{
		int test=HeaderStrings.headerStringOccurrences(s);
		
		float fuzzy=HeaderStrings.fuzzyIsHeader(s);
		
		if (fuzzy>1)
		{
			return true;
		}
		
		return false;
	}
	
	private void ParseHeader(LineText lt)
	{
		
	}
}
