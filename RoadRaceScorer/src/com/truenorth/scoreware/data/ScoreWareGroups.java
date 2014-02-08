package com.truenorth.scoreware.data;

import java.util.ArrayList;
import java.util.Date;

import com.truenorth.scoreware.data.DataFormats.DataTypes;
import com.truenorth.scoreware.data.Enums.ResultHeader;

import com.truenorth.scoreware.common.utility.DateTimeParser;

public class ScoreWareGroups 
{
	ArrayList<ScoreWareGroup> groups;

	public ScoreWareGroups()
	{
		groups=new ArrayList<ScoreWareGroup>();
	}
	
	/**
	 * Adds a piece of score ware data to a group
	 * @param swd
	 */
	public void addDataToGroup(ScoreWareData swd)
	{
		// try and add data to existing group
		
		if (addDataToExistingGroup(swd))
		{
			// if the function return true then data was added to existing group and
			// we can return
			return;
		}
		
		// if we get this far we couldn't find a group so start a new one
		
		ScoreWareGroup newGroup=new ScoreWareGroup();
					
		groups.add(newGroup);
		newGroup.addFirstData(swd);;
					
		return;
	}
	
	/**
	 * Adds the data to an existing group
	 * @param swd
	 * @return
	 * 
	 * returns true if data was added to existing group.  False if no group was found
	 */
	private boolean addDataToExistingGroup(ScoreWareData swd)
	{
		// first see if we can add it to an existing group
		
		DataTypes t2=swd.getDataType();
		ResultHeader rh2=swd.getResultHeader();
				
		int i2=swd.getDataTypeIndex();
		
		String dataString=swd.getDataString();
		
		// row of this data
		int r=swd.getRow();
		
		// if the result header is known
		if (rh2!=null)
		{
			// loop through all groups looking for this result header
			for (ScoreWareGroup group:groups)
			{
				
				ResultHeader rh1=group.getResultHeader();
			
				// if we have the right result header
				if (rh1==rh2)
				{
					// add the data to the group
					group.getData().add(swd);
					return true;
				}
			}
		}
		else
		{
			// loop through all groups trying to match the datatype and integer
			for (ScoreWareGroup group:groups)
			{
			//	DataTypes t1=group.getType();	
			
			//	int i1=(int)group.getTypeIndex();
			
				// # in the group so far
				int num=group.getData().size();
			
				// row of last added to group
				int rowLastAdded=group.getData().get(num-1).getRow();
			
				// if group type = datatype and if group type index = data type index 
				if (group.getType()==swd.getDataType() && (int)(group.getTypeIndex())==swd.getDataTypeIndex())
				{
					if (r>rowLastAdded)
					{
						group.getData().add(swd);
						return true;
					}
				}
				
				// if they are alligned group them
			}
		}
		
		if (r!=0)
		{
			int stop=5;
		}
		
		return false;
	}
	
	// this routine looks through each row, finds all the times and identifies 
		// gun time, chip time and other splits
/*		void analyzeTimes()
		{
			// for every row of data
			for (ScoreWareGroup group:groups)
			{
				// if it is a time
				if ( (group.getType()==DataTypes.MM_SS) || (group.getType()==DataTypes.HH_MM_SS) )
				{
					
				}
				
				ArrayList<ScoreWareData> times=new ArrayList<ScoreWareData>();
				
				// loop through the row
				for (ScoreWareData sw:dataList)
				{
					// get data type
					DataTypes dt=sw.getDataType();
					
					if ( (dt==DataTypes.MM_SS)|| (dt==DataTypes.HH_MM_SS) )
					{
						times.add(sw);
					}
				}
				
				Date largest=new Date(0L);
				int posLargest=-1;
				
				for (int i=0;i<times.size();i++)
				{
					String strTemp=times.get(i).getDataString();
					Date temp=DateTimeParser.getTime(strTemp);
					
					if (temp.after(largest))
					{
						largest=temp;
						posLargest=i;
					}
					
				//	System.out.println(temp+" : "+largest);
				}
				
				if (posLargest!=-1)
				{
					times.get(posLargest).setResultHeader(ResultHeader.GUN_TIME);
				}
				
				//RunwareUtilities.Pause();
			}
		}*/
	
	public ArrayList<ScoreWareGroup> getGroups()
	{
		return groups;
	}
}
