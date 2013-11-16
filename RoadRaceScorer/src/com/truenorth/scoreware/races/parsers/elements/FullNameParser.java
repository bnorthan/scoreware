package com.truenorth.scoreware.races.parsers.elements;

public abstract class FullNameParser 
{
	protected String firstName;
	protected String lastName;
	
	abstract public void Parse(String FullName);
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
}
