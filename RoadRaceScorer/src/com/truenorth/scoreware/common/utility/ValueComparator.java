package com.truenorth.scoreware.common.utility;

import java.util.Comparator;
import java.util.Map;
import java.lang.Comparable;
 
public class ValueComparator<T, S extends Comparable> implements Comparator<T> 
{

    Map<T, S> base;
    public ValueComparator(Map<T, S> base) 
    {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(T a, T b) 
    {
    	if (base.get(a).compareTo(base.get(b))<0)
        //if (base.get(a) >= base.get(b)) 
        {
            return -1;
        } else 
        {
            return 1;
        } // returning 0 would merge keys
    }
}
