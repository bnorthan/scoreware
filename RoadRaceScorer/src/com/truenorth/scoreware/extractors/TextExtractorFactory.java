package com.truenorth.scoreware.extractors;

public class TextExtractorFactory 
{
	public static TextExtractor MakeExtractor(String name)
	{
		// 1.  check extension 
		String ext=name.substring(name.lastIndexOf('.')+1, name.length());
		
		if (ext.equals("html")||ext.equals("htm"))
		{
			return new HtmlExtractor();
		}

		return null;
	}

}
