package com.truenorth.scoreware.races.readers.hmrrc;

import com.truenorth.scoreware.races.readers.HtmlRaceReader;
import com.truenorth.scoreware.races.parsers.hmrrc.HudsonMohawk2013Parser;

public class HudsonMohawk2012Reader extends HtmlRaceReader
{
	public HudsonMohawk2012Reader(String sourceName)
	{
		super(sourceName);
		
		resultParser=new HudsonMohawk2013Parser();
		table=true;
	}
}
