package com.truenorth.scoreware.membership.readers.hmrrc;

import java.io.*;
import java.util.Date;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.w3c.dom.CharacterData;

import javax.xml.namespace.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.common.utility.DateTimeParser;
import com.truenorth.scoreware.common.utility.SexParser;
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
				Node n=(Node)nodes.item(i);
				System.out.println("Node: "+i+" "+n.toString());
			}
			
			XPath path;
			XPathFactory xpfactory=XPathFactory.newInstance();
			path=xpfactory.newXPath();
		
			System.out.println("get worksheet");
			Node sheet=(Node)path.evaluate("//Worksheet/Table", doc, XPathConstants.NODE);
			
			System.out.println(sheet.getChildNodes().getLength());
			
			NodeList rows=(NodeList)path.evaluate("//Worksheet/Table/Row", doc, XPathConstants.NODESET);
			
			XPathExpression expr = path.compile("Cell");
				
			Node row=rows.item(0);
			
			row.getParentNode().removeChild(row);
			NodeList cellList=(NodeList)expr.evaluate((Element)row,XPathConstants.NODESET);
			
			System.out.println();
			
			int firstNameIndex=-1;
			int lastNameIndex=-1;
			int birthDateIndex=-1;
			int cityIndex=-1;
			int genderIndex=-1;
			
			for (int i=0;i<cellList.getLength();i++)
			{
				System.out.print(" "+cellList.item(i).getTextContent());
				
				String cellName=cellList.item(i).getTextContent();
				
				if (cellName.equals("First name"))
				{
					firstNameIndex=i;
				}
				if (cellName.equals("Last name"))
				{
					lastNameIndex=i;
				}
				if (cellName.equals("Birthdate (e.g., 01 Jun 1954)"))
				{
					birthDateIndex=i;
				}
				if (cellName.equals("City"))
				{
					cityIndex=i;
				}
				if (cellName.equals("Gender"))
				{
					genderIndex=i;
				}
			
			}
			System.out.println();
			System.out.println(firstNameIndex);
			System.out.println(lastNameIndex);
			System.out.println(birthDateIndex);
			System.out.println(cityIndex);
			System.out.println(genderIndex);
			System.out.println("end:");
			
			for (int i=1;i<rows.getLength();i++)
			{
				Racer racer=new Racer();
			
				row=rows.item(i);
				row.getParentNode().removeChild(row);
				
				//NodeList cellList=(NodeList)path.evaluate("Cell", (Element)n, XPathConstants.NODESET);
				cellList=(NodeList)expr.evaluate((Element)row,XPathConstants.NODESET);
			
				String firstName=cellList.item(firstNameIndex).getTextContent();
				racer.setFirstName(firstName);
				
				String lastName=cellList.item(lastNameIndex).getTextContent();
				racer.setLastName(lastName);
				
				String birthDate=cellList.item(birthDateIndex).getTextContent();
				Date date=DateTimeParser.getDate(birthDate);
				
				if (date!=null) 
				{
					int age=DateTimeParser.howOldInYears(date, new Date());
					racer.setAge(age);
				}
				
				String city=cellList.item(cityIndex).getTextContent();
				racer.setCity(city);
				
				String sex=cellList.item(genderIndex).getTextContent();
				racer.setSex(SexParser.parseSex(sex));
				
				//System.out.println(i);
				//System.out.println("First Name: "+cellList.item(1).getTextContent());
				
				/*if (n!=null && n.getNodeType()==Node.ELEMENT_NODE)
				{
					Element row=(Element)n;
					NodeList cells=(NodeList)path.evaluate("/Cell", row, XPathConstants.NODESET);
					System.out.println("NAME: "+nodes.item(1).getTextContent()+" "+nodes.item(2).getTextContent());
					System.out.flush();
				}*/
				
				members.add(racer);
			}
			
			for(Racer racer:members)
			{
				System.out.println(racer.getFirstName()+" "+racer.getLastName()+" "
						+racer.getCity()+" "+racer.getAge()+" "+racer.getSex());
				
			}
			
		}
		catch (Exception objEx)
		{
			System.out.println("Membership XML could not be read. "+objEx.getMessage());
		}
		
	}

}
