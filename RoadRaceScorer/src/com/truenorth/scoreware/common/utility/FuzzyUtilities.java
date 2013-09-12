package com.truenorth.scoreware.common.utility;

public class FuzzyUtilities 
{
	public static double sigmoid(double val, double alpha)
	{
		return 1/(1+java.lang.Math.exp(-alpha*val));
	}
}
