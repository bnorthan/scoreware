package com.truenorth.scoreware.extractors;

/**
 * Class used to create an appropriate TextExtractor given the name of a source
 * @author bnorthan
 *
 */
public class TextExtractorFactory 
{
	/**
	 * 
	 * @param name
	 * name of the source
	 * 
	 * @return
	 * returns an appropriate TextExtractor
	 */
	public static TextExtractor MakeExtractor(String name)
	{
		// 1.  check extension 
		String ext=name.substring(name.lastIndexOf('.')+1, name.length());
		
		if (ext.equals("html")||ext.equals("htm"))
		{
			return new HtmlExtractor();
		}
		else if (ext.equals("txt"))
		{
			return new TextFileExtractor();
		}

		return null;
	}

}
