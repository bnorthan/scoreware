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
        
        static public boolean isPlace(ArrayList<Integer> integerList)
    	{
    		int expectedPlace=1;
    		int previousPlace=0;
    		
    		int nExpectedPlace=0;
    		int nIncreasingPlace=0;
    		
    		int nTotal=integerList.size();
    		
    		// simple test to see if this is place
    		for (Integer i:integerList)
    		{
    			if (i==expectedPlace) nExpectedPlace++;
    			
    			expectedPlace++;
    			
    			// occasionally we will have a subset of results but overall place
    			// should still increase
    			if (i>previousPlace)
    			{
    				nIncreasingPlace++;
    			}
    			
    			previousPlace=i;
    		}
    		
    		double ratio=(double)nIncreasingPlace/(double)nTotal;
    		
    	//	System.out.println("place complete: "+expectedPlace);
    	//	System.out.println("place consistent: "+nIncreasingPlace);
    	//	System.out.println("ratio: "+ratio);
    	//	System.out.println();
    		if (ratio>0.9)
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}
        
        static public boolean isAge(ArrayList<Integer> integerList)
    	{
    		int[] intarray=new int[integerList.size()];
    		
    		int n=0;
    		for (int i:integerList)
    		{
    			intarray[n]=i;
    			n++;
    		}
    		
    		ScoreWareStats stats= new ScoreWareStats();
    		
    		stats.calcIntegerHistogram(intarray);
    		
    		int hist[]=stats.getHistogram(); //ScoreWareStats.calcHistogram(numcollumnsarray, minCols, maxCols);
    		
    		int max=stats.getMax();
    		int min=stats.getMin();
    		int mode=stats.getMode();
    		
    		double mean=stats.calculateMean(intarray);
    		
    		System.out.println("min: "+min+" max: "+max+" mode: "+mode+" mean: "+mean);
    		
    		// first simple test to see if this is age
    		if ( (mean>10)&&(mean<55) && (min>-1) && (max<100) )
    		{
    			return true;
    		}
    		
    		return false;
    	}

        

}