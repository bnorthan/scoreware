package com.truenorth.scoreware.gui;

import javax.swing.*;

import com.truenorth.scoreware.apps.GrandPrixScorerApp;

public class GrandPrixScorerGui extends ScorerGui
{
	JButton membershipButton;
	JTextField membershipLocation;
	
	JButton raceButton;
	JTextField raceLocation;
	JCheckBox checkFromWeb;
	
	JButton saveButton;
	
	JButton databaseButton;
	
	JButton scoreButton;
	
	JTextArea textArea;
	
	JScrollPane scroll;
	
	JLabel racePatternLabel;
	JComboBox raceCombo;
	
	public GrandPrixScorerGui(GrandPrixScorerApp grandPrixScorerApp)
	{
		super(grandPrixScorerApp);
		
		this.grandPrixScorerApp=grandPrixScorerApp;
		
		grandPrixScorerApp.setIsRacerMember(new IsRacerMemberDialog());
		
		setTitle("Grand Prix Scorer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
	}

	private static final int DEFAULT_WIDTH=500;
	private static final int DEFAULT_HEIGHT=500;
	
	GrandPrixScorerApp grandPrixScorerApp;

	
}