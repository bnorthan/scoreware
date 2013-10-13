package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;

import com.truenorth.scoreware.Enums;
import com.truenorth.scoreware.Enums.RacePatterns;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.apps.GrandPrixScorerApp;
import com.truenorth.scoreware.common.fromcorejava.GBC;
import com.truenorth.scoreware.matchers.IsRacerMember;

import com.truenorth.scoreware.apps.TUResultsApp;

public class TUResultsGui extends ScorerGui
{
	
	TUResultsApp tuResultsApp;
	
	public TUResultsGui(TUResultsApp tuResultsApp)
	{
		super(tuResultsApp);
		
		this.tuResultsApp=tuResultsApp;
		
		tuResultsApp.setIsRacerMember(new IsRacerMemberDialog());
		
		setTitle("TU Results");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
	}

	private static final int DEFAULT_WIDTH=500;
	private static final int DEFAULT_HEIGHT=500;
}
