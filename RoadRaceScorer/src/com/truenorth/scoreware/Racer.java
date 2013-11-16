package com.truenorth.scoreware;

import java.util.Date;
import java.text.DateFormat;

import java.util.ArrayList;

public class Racer 
{
	
	public enum Sex{MALE, FEMALE};
	
	String firstName;
	
	String lastName;
	
	Date birthDate;
	
	int age;
	
	Sex sex;
	
	String city;
	
	String state;
	
	ArrayList<String> clubs=new ArrayList<String>();
	
	String currentClub="unknown";
	
	boolean member;
	
	public void setDummyValues()
	{	
		String unknown="unknown";
		
		firstName=unknown;
		lastName=unknown;
		
		city=firstName=unknown;
		state="XX";
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public String getLastName()
	{
		return lastName;
	}
	
	public void setBirthDate(Date birthDate)
	{
		this.birthDate=birthDate;
	}
	public Date getBirthdate()
	{
		return birthDate;
	}
	
	public void setAge(int age)
	{
		this.age=age;
	}
	public int getAge()
	{
		return age;
	}
	
	public void setSex(Sex sex)
	{
		this.sex=sex;
	}
	public Sex getSex()
	{
		return sex;
	}
	
	public void setCity(String city)
	{
		this.city=city;
	}
	public String getCity()
	{
		return city;
	}
	
	public void setState(String state)
	{
		if (state.length()>2)
		{
			state="XX";
		}
		else
		{
			this.state=state;
		}
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setCurrentClub(String currentClub)
	{
		this.currentClub=currentClub;
	}
	
	public String getCurrentClub()
	{
		return currentClub;
	}
	
	@Override
	public String toString()
	{
		return firstName+" "+lastName+" "+age+" "+sex+" "+city+" "+state+" "+currentClub;
	}

}
