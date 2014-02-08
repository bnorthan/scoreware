package com.truenorth.scoreware.common.utility;

public class GenericStats<T extends Number> 
{
	double mean;
	double std;
	T[] data;
	
	public static<T extends Number> double CalculateMean(T[] data)
	{
		GenericStats<T> stats=new GenericStats<T>(data);
		
		return stats.getMean();
	}
	
	public GenericStats(T[] data)
	{
		this.data=data;
		
		calculateStats();
				
	}
	
	private void calculateStats()
	{
		mean=0;
		std=0;
		
		for (T t:data)
		{
			mean+=t.doubleValue();
		}
		
		mean/=data.length;
	}
	
	public double getMean()
	{
		return mean;
	}
}

/*
public class ScoreWareStats<T extends Number & Comparable<T>> 
{
	T max;
	T min;
	T mode;
	T median;
	
	double mean;
	double std;
	
	T binSize;
	int[] bins;
	int[] histogram;
	
	public void calculateStats(T[] data, T typeMin, T typeMax) 
	{
		min=typeMin;
		max=typeMax;
			
		for (T t:data)
		{
			if (t.compareTo(min)<=0)
			{
				min=t;
			}
			
			if (t.compareTo(max)>=0)
			{
				max=t;
			}
		}
		
		Double range=max.doubleValue()-min.doubleValue();
		
		int nbins=(int)range.doubleValue();
		
		bins=new int[nbins];
		histogram=new int[nbins];
		
		for (T d : data) 
		{
		    double bin = d.doubleValue()-min.doubleValue();
		    
		    if (bin < 0) { // this data is smaller than min  }
		    else if (bin >= nbins) { /* this data point is bigger than max  }
		    else 
		    {
		      int binIndex=(int)bin;
		      histogram[binIndex] += 1;
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
	
	public T getMax()
	{
		return max;
	}
	
	public T getMin()
	{
		return min;
	}
	
	public T getMode()
	{
		return mode;
	}
	
	public T[] getBins()
	{
		return bins;
	}
	
	public int[] getHistogram()
	{
		return histogram;
	}
	*/