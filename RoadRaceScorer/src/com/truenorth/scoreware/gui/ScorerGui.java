package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.awt.GridBagLayout;

import com.truenorth.scoreware.Enums;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.Enums.RacePatterns;
import com.truenorth.scoreware.common.fromcorejava.GBC;
import com.truenorth.scoreware.matchers.IsRacerMember;
import com.truenorth.scoreware.apps.ScoringApp;

import com.truenorth.scoreware.Race;

public abstract class ScorerGui extends JFrame
{
	JButton membershipButton;
	JTextField membershipLocation;
	
	JButton raceButton;
	JTextField raceLocation;
	JCheckBox checkFromWeb;
	
	JButton saveButton;
	
	JButton databaseButton;
	JButton updateDatabaseButton;
	JButton transferDatabaseButton;
	
	JButton scoreButton;
	
	JTextArea textArea;
	
	JScrollPane scroll;
	
	JLabel racePatternLabel;
	JComboBox raceCombo;
	
	RaceInfoFrame raceInfoFrame;
	
	ScoringApp app;
	
	public ScorerGui(ScoringApp app)
	{
		this.app=app;
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout); 
		
		membershipButton=new JButton("Open Membership");
		membershipButton.addActionListener(new membershipActionListener());
		membershipLocation=new JTextField();
		
		raceCombo=new JComboBox();
		
		raceCombo.addItem(Enums.RacePatterns.PROPERTIES);
		raceCombo.addItem(Enums.RacePatterns.UNKNOWN);
		raceCombo.addItem(Enums.RacePatterns.WINEGLASS2013);
		raceCombo.addItem(Enums.RacePatterns.PDF_TEXT);
		raceCombo.addItem(Enums.RacePatterns.RUNSCORE);
		raceCombo.addItem(Enums.RacePatterns.PDF_TABLE);
		raceCombo.addItem(Enums.RacePatterns.TABLE);
		raceCombo.addItem(Enums.RacePatterns.SEFCU2013);
		raceCombo.addItem(Enums.RacePatterns.ANNIVERSARY2013);
		raceCombo.addItem(Enums.RacePatterns.VOORHESVILLE2013);
		raceCombo.addItem(Enums.RacePatterns.HUDSONMOHAWK2012);
		raceCombo.addActionListener(new raceComboListener());
		this.app.setRacePattern((RacePatterns)(raceCombo.getSelectedItem()));
		
		racePatternLabel=new JLabel("Race Pattern");
		
		raceButton=new JButton("Open Race");
		raceButton.addActionListener(new raceActionListener());
		raceLocation=new JTextField();
		checkFromWeb=new JCheckBox("from web");
	    
		saveButton=new JButton("Save");
		saveButton.addActionListener(new saveActionListener());
		
		databaseButton=new JButton("Initialize database");
	    databaseButton.addActionListener(new databaseListener());
	    
	    updateDatabaseButton=new JButton("Update database");
	    updateDatabaseButton.addActionListener(new updateDatabaseListener());
	    
	    transferDatabaseButton=new JButton("Transfer database");
	    transferDatabaseButton.addActionListener(new transferDatabaseListener());
	    
	    scoreButton=new JButton("Score");
	    scoreButton.addActionListener(new scoreActionListener());
	    
		textArea=new JTextArea();
		textArea.setBorder(BorderFactory.createEtchedBorder());
		
	    //Console.redirectOutput( textArea );
	    
	    scroll=new JScrollPane(textArea);
	    
		add(membershipButton, new GBC(0,7).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(raceButton, new GBC(0,8,1,2).setAnchor(GBC.NORTHWEST).setFill(GBC.HORIZONTAL));
		add(scoreButton, new GBC(0,10).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		
		add(saveButton, new GBC(0,11).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(databaseButton, new GBC(0,12).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(updateDatabaseButton, new GBC(2,12).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(transferDatabaseButton, new GBC(3,12).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		
		add(racePatternLabel, new GBC(0,6));
		add(raceCombo, new GBC(1,6,3,1).setFill(GBC.BOTH));
		add(scroll, new GBC(0,0,9,6).setFill(GBC.BOTH).setWeight(100, 100));
		
		add(checkFromWeb, new GBC(1,8,1,1));
		
		add(membershipLocation, new GBC(1,7,3,1).setFill(GBC.BOTH));
		add(raceLocation, new GBC(1,9,3,1).setFill(GBC.BOTH));

		raceInfoFrame=new RaceInfoFrame();
		raceInfoFrame.setActionListener(new RaceInfoListener());
		//frame.setPreferredSize(new Dimension(500,100));
		//frame.setSize(500,200);
		add(raceInfoFrame, new GBC(4,6,5,6).setFill(GBC.BOTH));
		
	}
	
	private class membershipActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
			int success = chooser.showOpenDialog(null);
			
			app.ReadMembership(chooser.getSelectedFile().getAbsolutePath());
			
			membershipLocation.setText(chooser.getSelectedFile().getAbsolutePath());
			
		}
	}
	
	private class raceComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			app.setRacePattern((RacePatterns)(raceCombo.getSelectedItem()));
		}
	}
	
	private class raceActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
			int success = chooser.showOpenDialog(null);
			
			app.ReadRace(chooser.getSelectedFile().getAbsolutePath());
			
			raceLocation.setText(chooser.getSelectedFile().getAbsolutePath());
			
			Race race=app.getRace();
			
			String dateString="";
			
			if (race.getDate()!=null)
			{
				dateString=race.getDate().toString();
			}
			
			raceInfoFrame.setInfo(race.getIdentifier(), race.getName(), race.getCity(), race.getState(), dateString);
		}
	}
	
	private class scoreActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			app.Score();
		}
	}
	
	
	private class saveActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
//			File file=new File();		
			int success = chooser.showOpenDialog(null);
			
			app.saveResults(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private class databaseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// choose directory to save properties file to
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int success=chooser.showOpenDialog(null);
			
			app.setWorkingDirectory(chooser.getSelectedFile());
			app.initializeDatabase();
		}
	}
	
	private class updateDatabaseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			app.updateDatabase();
		}
	}
	
	private class transferDatabaseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
			int success = chooser.showOpenDialog(null);
			
			app.transferToDatabase(chooser.getSelectedFile().getAbsolutePath());
			
		}
	}
	
	class IsRacerMemberDialog implements IsRacerMember
	{
		public boolean IsRacerAMember(Racer racer, Racer member)
		{
			IsRacerAMemberDialog isRacerAMember=new IsRacerAMemberDialog(racer, member);
			
			return isRacerAMember.showDialog(ScorerGui.this, "Hello");
		
		}
	}
	
	private class RaceInfoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
			//frame.setVisible(true);
			System.out.println("id: "+raceInfoFrame.getID());
			
			ScorerGui.this.app.setRaceInfo(raceInfoFrame.getID(), 
					raceInfoFrame.getName(), 
					raceInfoFrame.getDate(), 
					raceInfoFrame.getCity(), 
					raceInfoFrame.getStateProvince(), "USA", raceInfoFrame.getTimedBy());
		}
	}
	
}
