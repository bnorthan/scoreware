package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Reader;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.extractors.TextExtractorFactory;
import com.truenorth.scoreware.races.parsers.ResultParser;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;
import com.truenorth.scoreware.extractors.overall.OverallFromSizeAndOrder;
import com.truenorth.scoreware.races.parsers.generic.GenericResultParser;
import com.truenorth.scoreware.races.parsers.hmrrc.WinterSeries2013Parser;

public abstract class RaceReader extends Reader 
{
	public RaceReader(String sourceName)
	{
		super(sourceName);
	}
	
	protected ArrayList<ResultHeader> order=new ArrayList<ResultHeader>();
	protected ArrayList<Result> results;
	protected OverallExtractor overallExtractor;
	protected ResultParser resultParser;
	
	public void read()
	{
		ArrayList<String> text=extractor.extractText(sourceName);
		text=overallExtractor.extractText(text);
		
		for(String line:text)
		{
			System.out.println(line);
			
			Result result=resultParser.parseResultFromLine(line);
			results.add(result);
		}		
		
		for(Result result:results)
		{
			System.out.println(result);//.getOverallPlace()+": "+result.getRacer().getFirstName()+
				//" "+result.getRacer().getLastName()+" "+result.getRacer().getAge()+" "+result.getRacer().getSex()
				//+" "+result.getRacer().getCity()+" "+result.getRacer().getState());
		}
	}
	
	public ArrayList<Result> getResults()
	{
		return results;
	}
}