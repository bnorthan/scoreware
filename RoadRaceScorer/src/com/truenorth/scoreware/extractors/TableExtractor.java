package com.truenorth.scoreware.extractors;

import org.jsoup.select.Elements;

import com.truenorth.scoreware.data.HeaderStrings;

public abstract class TableExtractor 
{
	protected boolean onLine;
	protected Elements tableHeaders;
	protected Elements tableRows;
	
	protected String sourceName;
	
	abstract public Elements getMainTable();
	
	public Elements getTable(String sourceName, boolean onLine)
	{
		System.out.println("Table extractor!");
		
		this.sourceName=sourceName;
		
		Elements elements=getMainTable();
		
		// get table rows
		tableRows=elements.select("tr");
			
		// at this point we could just extract the header -- however there are some
		// cases where the headers elements are just placed in the first or second row
		// and not marked as header
		
		int nHeaderLine=-1;
		int nMaxHeaders=-1;
		
		// search for the header
		for (int i=0;i<tableRows.size();i++)
		{
			System.out.println("row: "+i+" :"+tableRows.get(i).text());
			
			int nData=tableRows.get(i).children().size();
			
			int nTemp=HeaderStrings.headerStringOccurrences(tableRows.get(i).text());
			
			if ( (nTemp>nMaxHeaders) && (nTemp/nData>0.5) )
			{
				nMaxHeaders=nTemp;
				nHeaderLine=i;
			}
		}
		
		if (nHeaderLine!=-1)
		{
			System.out.println("header found at: "+nHeaderLine+" :"+tableRows.get(nHeaderLine).text());
			tableHeaders=tableRows.get(nHeaderLine).children();
			tableRows.remove(nHeaderLine);
		}
		
		if (true) return tableRows;
		
		// lets print out the number of tables...  
		System.out.println("num tables are "+elements.size());
		
		// select the header elements...
		tableHeaders=elements.select("thead tr th");
		
		// print out the size of the header
		System.out.println("num headers are: "+tableHeaders.size());
		
		int nHeaderMarks;
		
		// for now, for debugging purposes print out the header
		for (int i=0;i<tableHeaders.size();i++)
		{
			System.out.println(i+": "+tableHeaders.get(i).text());
			
			// check to see if this is a race result header....  this routine checks for the presence of known
			// race result headers (place, name, time, sex, etc.)
			nHeaderMarks=HeaderStrings.headerStringOccurrences(tableHeaders.get(i).text());
			System.out.println("header marks: "+nHeaderMarks);
		}
		
		tableRows=elements.select(":not(thead) tr");
		System.out.println("num rows are: "+tableRows.size());
		
		// header not found
		//if (nHeaderMarks<3)
		//{
		// for debugging purposes print out the first 10 rows
		for (int i=0;i<10;i++)
		{
			System.out.println("row: "+i+" :"+tableRows.get(i).text());
			
			int nTemp=HeaderStrings.headerStringOccurrences(tableRows.get(i).text());
			
			System.out.println("Header Marks: "+nTemp);
		}
		
		Elements test=new Elements();
		
		test.add(tableRows.get(1));
		
		System.out.println("header_built: "+test.get(0).text());
		
		// if no table headers are found
		if (tableHeaders.size()==0)
		{
			System.out.println("no rows found!");
		}
				
		
		return tableRows;
	}
	
	public Elements getTableHeaders()
	{
		return tableHeaders;
	}

}
