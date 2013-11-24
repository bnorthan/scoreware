package com.truenorth.scoreware.common.utility;

public class ScoreWareStats 
{
	public static int[] calcHistogram(int[] data, int min, int max) 
	{
		  final int numBins = max - min+ 1;
		  final int[] result = new int[numBins];
		  
		  for (int d : data) 
		  {
		    int bin = d-min;
		    if (bin < 0) { /* this data is smaller than min */ }
		    else if (bin >= numBins) { /* this data point is bigger than max */ }
		    else 
		    {
		      result[bin] += 1;
		    }
		  }
		  return result;
	}

}
