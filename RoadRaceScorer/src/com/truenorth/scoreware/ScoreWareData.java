package com.truenorth.scoreware;

import com.truenorth.scoreware.DataFormats.DataTypes;

public class ScoreWareData 
{
	String dataString;
	
	Enums.ResultHeader resultHeader;
	
	DataTypes dataType;
	
	public ScoreWareData(String dataString)
	{
		this.dataString=dataString;
	}
	
	public void setDataString(String dataString)
	{
		this.dataString=dataString;
	}
	
	public String getDataString()
	{
		return dataString;
	}
	
	public void setResultHeader(Enums.ResultHeader resultHeader)
	{
		this.resultHeader=resultHeader;
	}
	
	public Enums.ResultHeader getResultHeader()
	{
		return this.resultHeader;
	}
	
	public DataTypes getDataType()
	{
		return dataType;
	}
	
	public void setDataType(DataTypes dataType)
	{
		this.dataType=dataType;
	}

	
}
