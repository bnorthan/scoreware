package com.truenorth.scoreware.writers;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import com.truenorth.scoreware.ScorewareWriter;

public abstract class SqlWriter extends ScorewareWriter
{
	public SqlWriter(String destination)
	{
		super(destination);
	}

	public abstract Connection getConnection() throws SQLException, IOException;
}
