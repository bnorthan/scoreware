package com.truenorth.scoreware.races.readers;

import java.util.ArrayList;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Reader;
import com.truenorth.scoreware.Enums.ResultHeader;
import com.truenorth.scoreware.races.parsers.ResultParser;
import com.truenorth.scoreware.extractors.overall.OverallExtractor;

/**
 * Extends Reader to implement a RaceReader.  RaceReader reads
 * race results
 * @author bnorthan
 *
 */
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
		// extract text from the source data (could be a PDF, web page, database, etc.)
		ArrayList<String> text=extractor.extractText(sourceName);
		
		// extract the overall results table
		text=overallExtractor.extractText(text);
		
		// loop through each line of text in the overall results
		for(String line:text)
		{
	//		System.out.println(line);
			
			// parse the result
			Result result=resultParser.parseResultFromLine(line);
			
			// add the result to the list
			results.add(result);
		}		
		
		/*for(Result result:results)
		{
			System.out.println(result);//.getOverallPlace()+": "+result.getRacer().getFirstName()+
				//" "+result.getRacer().getLastName()+" "+result.getRacer().getAge()+" "+result.getRacer().getSex()
				//+" "+result.getRacer().getCity()+" "+result.getRacer().getState());
		}*/
	}
	
	public ArrayList<Result> getResults()
	{
		return results;
	}
}