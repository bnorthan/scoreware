package com.truenorth.scoreware.sql;
import com.truenorth.scoreware.data.Result;

public interface RunwareQuery 
{
	String makeResultQuery(Result result, String table);
}
