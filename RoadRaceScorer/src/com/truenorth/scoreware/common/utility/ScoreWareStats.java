package com.truenorth.scoreware.common.utility;

import java.util.ArrayList;

public class ScoreWareStats 
{
	int max;
	int min;
	int mode;
	double mean;
	
	int[] bins;
	int[] histogram;
	
	public void calcIntegerHistogram(int[] data) 
	{
		min=Integer.MAX_VALUE;
		max=Integer.MIN_VALUE;
			
		for (int i:data)
		{
			if (i<min)
			{
				min=i;
			}
			
			if (i>max)
			{
				max=i;
			}
		}
		
		int nbins=max-min+1;
		
		bins=new int[nbins];
		histogram=new int[nbins];
		
		for (int d : data) 
		{
		    int bin = d-min;
		    if (bin < 0) { /* this data is smaller than min */ }
		    else if (bin >= nbins) { /* this data point is bigger than max */ }
		    else 
		    {
		      histogram[bin] += 1;
		    }
		}	  
		
		// while we are here calculate the mode
		int modenum=-1;
		int modeposition=-1;
		
		for (int n=0;n<histogram.length;n++)
		{
			if (histogram[n]>modenum)
			{
				modenum=histogram[n];
				modeposition=n;
			}
		}
		
		mode=min+modeposition;
		
	}
	
	public double calculateMean(int[] data)
	{
		double sum=0;
		for (int i=0;i<data.length;i++)
		{
			sum+=data[i];
		}
		
		return sum/data.length;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getMin()
	{
		return min;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public int[] getBins()
	{
		return bins;
	}
	
	public int[] getHistogram()
	{
		return histogram;
	}
	

}
