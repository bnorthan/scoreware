package com.truenorth.scoreware.membership.readers;

import java.io.*;
import java.util.*;

import com.truenorth.scoreware.Racer;

public class SimpleTextMembershipReader extends MembershipReader
{
	public SimpleTextMembershipReader(String sourceName)
	{
		super(sourceName);
	}
	
	public void read()
	{
		try
		{
			FileInputStream file = new FileInputStream(sourceName);
			
			Scanner in=new Scanner(file);
			
			while (in.hasNextLine())
			{
				Racer racer=new Racer();
				
				String line=in.nextLine();
				
				String[] split=line.split(" ");
				
				int n=split.length;
				
				racer.setFirstName(split[0]);
				racer.setLastName(split[n-1]);
			
				members.add(racer);
			}
			
			for(Racer racer:members)
			{
				System.out.println(racer.getFirstName()+" "+racer.getLastName()+" "
						+racer.getCity()+" "+racer.getAge()+" "+racer.getSex());
				
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
