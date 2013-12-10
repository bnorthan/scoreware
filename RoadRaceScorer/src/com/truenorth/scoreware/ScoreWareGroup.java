package com.truenorth.scoreware;

import java.util.ArrayList;

import com.truenorth.scoreware.DataFormats.DataTypes;
import com.truenorth.scoreware.Enums.ResultHeader;

public class ScoreWareGroup 
{
	// the index of the data within the raw row
	// **NOTE** for an individual piece of data this is an integer
	// but for the group it can be a float (because it is the average position)
	float index;
	
	// the index of the data in terms of type within the raw row (example 3rd integer)
	float typeIndex;
	
	// the average starting position of the data
	float startPosition;
	// the average ending position of the data
	float endPosition;
	
	// is the start alligned
	boolean startAlligned;
	
	// is the end alligned
	boolean endAlligned;
	
	DataTypes type;
	
	ResultHeader header;
	
	ArrayList<ScoreWareData> data;
	
	public ScoreWareGroup()
	{
		data=new ArrayList<ScoreWareData>();
	}
	
	public void addFirstData(ScoreWareData swd)
	{
		// initialize with the type of data and the type index
		type=swd.getDataType();
		header=swd.getResultHeader();
		typeIndex=swd.getDataTypeIndex();
		data.add(swd);
	}
	
	public ArrayList<ScoreWareData> getData()
	{
		return this.data;
	}
	
	public DataTypes getType()
	{
		return this.type;
	}
	
	public ResultHeader getResultHeader()
	{
		return header;
	}
	
	public float getTypeIndex()
	{
		// cast typeIndex to integer and return it
		return this.typeIndex;
	}

}
