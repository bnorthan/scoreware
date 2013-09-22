package com.truenorth.scoreware.membership.readers;

import com.truenorth.scoreware.membership.readers.hmrrc.HmrrcExcelReader;

public class MembershipReaderFactory 
{
	public static MembershipReader getMembershipReader(String membershipSource)
	{
		return new HmrrcExcelReader(membershipSource);
	}
}
