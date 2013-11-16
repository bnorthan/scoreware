package com.truenorth.scoreware.extractors;

import com.truenorth.scoreware.extractors.DOMExtractor;
import com.truenorth.scoreware.extractors.PdfDOMExtractor;

/**
 * Class used to create an appropriate TableExtractor given the name of a source
 * @author bnorthan
 *
 */
public class TableExtractorFactory 
{
	/**
	 * 
	 * @param name
	 * name of the source
	 * 
	 * @return
	 * returns an appropriate TextExtractor
	 */
	public static TableExtractor MakeExtractor(String name)
	{
		// 1.  check extension 
		String ext=name.substring(name.lastIndexOf('.')+1, name.length());
		
		if (ext.equals("html")||ext.equals("htm"))
		{
			return new DOMExtractor();
		}
		else if (ext.equals("pdf"))
		{
			return new PdfDOMExtractor();
		}

		return null;
	}

}

