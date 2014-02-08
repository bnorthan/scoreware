package com.truenorth.scoreware.data;

import com.truenorth.scoreware.data.DataFormats.DataTypes;

public class ScoreWareData 
{
	String dataString;
	
	Enums.ResultHeader resultHeader;
	
	DataTypes dataType;
	
	int row;
	int collumn;
	int dataTypeIndex;
	
	int startPosition;
	int endPosition;
	
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
	
	public void setStartPosition(int startPosition)
	{
		this.startPosition=startPosition;
	}
	
	public int getStartPosition()
	{
		return this.startPosition;
	}
	
	public void setEndPosition(int endPosition)
	{
		this.endPosition=endPosition;
	}
	
	public int getEndPosition()
	{
		return this.endPosition;
	}
	
	public void setDataTypeIndex(int dataTypeIndex)
	{
		this.dataTypeIndex=dataTypeIndex;
	}
	
	public int getDataTypeIndex()
	{
		return this.dataTypeIndex;
	}
	
	public void setCollumn(int collumn)
	{
		this.collumn=collumn;
	}
	
	public int getCollumn()
	{
		return this.collumn;
	}
	
	public void setRow(int row)
	{
		this.row=row;
	}
	
	public int getRow()
	{
		return this.row;
	}
}
