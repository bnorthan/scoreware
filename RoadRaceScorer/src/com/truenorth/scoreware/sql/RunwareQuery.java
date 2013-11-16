package com.truenorth.scoreware.sql;
import com.truenorth.scoreware.Result;

public interface RunwareQuery 
{
	String makeResultQuery(Result result, String table);
}
