package com.truenorth.scoreware.membership.readers.hmrrc;

import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.w3c.dom.CharacterData;

import com.truenorth.scoreware.membership.readers.MembershipReader;

public class HmrrcXmlReader extends MembershipReader
{
	public HmrrcXmlReader(String source)
	{
		super(source);
	}
	
	public void read() 
	{
		try
		{
			FileInputStream inputStream=new FileInputStream(sourceName);
		
			DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
			Document doc=builder.parse(inputStream);
			
			NodeList nodes=doc.getDocumentElement().getChildNodes();
			
			System.out.println("xml num rows "+nodes.getLength());
			
			for (int i=0;i<nodes.getLength();i++)
			{
				System.out.println("asdlkkfj");
				Element e=(Element)nodes.item(i);
				System.out.println("asdlkkfj");
				System.out.println("Node: "+i);
			}
		}
		catch (Exception objEx)
		{
			System.out.println("Membership XML could not be read. "+objEx.getMessage());
		}
		
	}

}
