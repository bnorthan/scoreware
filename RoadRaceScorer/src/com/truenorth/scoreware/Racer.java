package com.truenorth.scoreware;

import java.util.Date;
import java.text.DateFormat;

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
	
	boolean member;
	
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
		this.state=state;
	}
	public String getState()
	{
		return state;
	}
	
	@Override
	public String toString()
	{
		return firstName+" "+lastName+" "+age+" "+sex+" "+city+" "+state;
	}

}
