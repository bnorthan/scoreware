package com.truenorth.scoreware;

import java.util.ArrayList;
import com.truenorth.scoreware.DataFormats.DataTypes;
import com.truenorth.scoreware.Enums.ResultHeader;

public class ScoreWareGroups 
{
	ArrayList<ScoreWareGroup> groups;

	public ScoreWareGroups()
	{
		groups=new ArrayList<ScoreWareGroup>();
	}
	
	public void addDataToGroup(ScoreWareData swd)
	{
		// which group does it belong to???
		
		DataTypes t2=swd.getDataType();
		ResultHeader rh2=swd.getResultHeader();
				
		int i2=swd.getDataTypeIndex();
		
		String dataString=swd.getDataString();
		// row of this data
		int r=swd.getRow();
		
		// if the group is known
		if (rh2!=null)
		{
			for (ScoreWareGroup group:groups)
			{
				ResultHeader rh1=group.getResultHeader();
			
				if (rh1==rh2)
				{
					group.getData().add(swd);
					return;
				}
			}
			
		}
		else
		{
			for (ScoreWareGroup group:groups)
			{
				DataTypes t1=group.getType();	
			
				int i1=(int)group.getTypeIndex();
			
				// # in the group so far
				int num=group.getData().size();
			
				// row of last added to group
				int rowLastAdded=group.getData().get(num-1).getRow();
			
				if (group.getType()==swd.getDataType() && (int)(group.getTypeIndex())==swd.getDataTypeIndex())
				{
					if (r>rowLastAdded)
					{
						group.getData().add(swd);
						return;
					}
				}
			}
		}
		
		// if we get this far we couldn't find a group so start a new one
		
		ScoreWareGroup newGroup=new ScoreWareGroup();
					
		groups.add(newGroup);
		newGroup.addFirstData(swd);;
					
		return;
	}
	
	public ArrayList<ScoreWareGroup> getGroups()
	{
		return groups;
	}
}
