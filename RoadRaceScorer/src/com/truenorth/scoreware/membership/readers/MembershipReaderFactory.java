package com.truenorth.scoreware.membership.readers;

import com.truenorth.scoreware.membership.readers.hmrrc.HmrrcExcelReader;
import com.truenorth.scoreware.membership.readers.hmrrc.HmrrcXmlReader;

public class MembershipReaderFactory 
{
	public static MembershipReader getMembershipReader(String membershipSource)
	{
		String ext=membershipSource.substring(membershipSource.lastIndexOf('.')+1, membershipSource.length());
		
		if (ext.equals("xls"))
		{
			return new HmrrcExcelReader(membershipSource);
		}
		
		else if (ext.equals("xml"))
		{
			return new HmrrcXmlReader(membershipSource);
		}
		
		return null;
	}
}
