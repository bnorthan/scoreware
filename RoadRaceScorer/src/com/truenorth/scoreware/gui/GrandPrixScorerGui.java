package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;

import com.truenorth.scoreware.Enums;
import com.truenorth.scoreware.Enums.HmrrcRaces;
import com.truenorth.scoreware.Racer;
import com.truenorth.scoreware.apps.GrandPrixScorerApp;
import com.truenorth.scoreware.common.fromcorejava.GBC;
import com.truenorth.scoreware.matchers.IsRacerMember;

public class GrandPrixScorerGui extends JFrame
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
		this.grandPrixScorerApp=grandPrixScorerApp;
		
		grandPrixScorerApp.setIsRacerMember(new IsRacerMemberDialog());
		
		setTitle("Grand Prix Scorer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		membershipButton=new JButton("Open Membership");
		membershipButton.addActionListener(new membershipActionListener());
		membershipLocation=new JTextField();
		
		raceCombo=new JComboBox();
		
		raceCombo.addItem(Enums.HmrrcRaces.SEFCU2013);
		raceCombo.addItem(Enums.HmrrcRaces.ANNIVERSARY2013);
		raceCombo.addItem(Enums.HmrrcRaces.VOORHESVILLE2013);
		raceCombo.addItem(Enums.HmrrcRaces.HUDSONMOHAWK2012);
		raceCombo.addActionListener(new raceComboListener());
		grandPrixScorerApp.setRacePattern((HmrrcRaces)(raceCombo.getSelectedItem()));
		
		racePatternLabel=new JLabel("Race Pattern");
		
		raceButton=new JButton("Open Race");
		raceButton.addActionListener(new raceActionListener());
		raceLocation=new JTextField();
		checkFromWeb=new JCheckBox("from web");
	    
		saveButton=new JButton("Save");
		saveButton.addActionListener(new saveActionListener());
		
		databaseButton=new JButton("To database");
	    databaseButton.addActionListener(new databaseListener());
	    
	    scoreButton=new JButton("Score");
	    scoreButton.addActionListener(new scoreActionListener());
	    
		textArea=new JTextArea();
		textArea.setBorder(BorderFactory.createEtchedBorder());
	    Console.redirectOutput( textArea );
	    
	    scroll=new JScrollPane(textArea);
	    
		add(membershipButton, new GBC(0,7).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(raceButton, new GBC(0,8,1,2).setAnchor(GBC.NORTHWEST).setFill(GBC.HORIZONTAL));
		add(scoreButton, new GBC(0,10).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		
		add(saveButton, new GBC(0,11).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		add(databaseButton, new GBC(0,12).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		
		add(racePatternLabel, new GBC(0,6));
		add(raceCombo, new GBC(1,6,3,1).setFill(GBC.BOTH));
		add(scroll, new GBC(0,0,4,6).setFill(GBC.BOTH).setWeight(100, 100));
		
		add(checkFromWeb, new GBC(1,8,1,1));
		
		add(membershipLocation, new GBC(1,7,3,1).setFill(GBC.BOTH));
		add(raceLocation, new GBC(1,9,3,1).setFill(GBC.BOTH));
	}

	private static final int DEFAULT_WIDTH=500;
	private static final int DEFAULT_HEIGHT=500;
	
	GrandPrixScorerApp grandPrixScorerApp;
	
	private class membershipActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
//			File file=new File();		
			int success = chooser.showOpenDialog(null);
			
			grandPrixScorerApp.setMembershipSourceName(chooser.getSelectedFile().getAbsolutePath());
			grandPrixScorerApp.ReadMembership();
			
			membershipLocation.setText(chooser.getSelectedFile().getAbsolutePath());
			//grandPrixScorerApp.setRaceSourceName("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
			//grandPrixScorerApp.setRaceSourceName("D:/Brian2012/hmrrc/data/Voorhesville.txt");
			
			//grandPrixScorerApp.Score();
		}
	}
	
	private class raceActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser=new JFileChooser();
			
			File dir=new File("D:/Brian2012/hmrrc/data/");
			chooser.setCurrentDirectory(dir);
			
//			File file=new File();		
			int success = chooser.showOpenDialog(null);
			
			grandPrixScorerApp.setRaceSourceName(chooser.getSelectedFile().getAbsolutePath());
			
			raceLocation.setText(chooser.getSelectedFile().getAbsolutePath());
			//grandPrixScorerApp.setRaceSourceName("D:/Brian2012/hmrrc/data/Voorhesville.txt");
			
			//grandPrixScorerApp.Score();
		}
	}
	
	private class scoreActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			grandPrixScorerApp.Score();
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
			
			grandPrixScorerApp.saveResults(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private class databaseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			grandPrixScorerApp.writeToDatabase();
		}
	}
	
	private class raceComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			grandPrixScorerApp.setRacePattern((HmrrcRaces)(raceCombo.getSelectedItem()));
		}
	}
	
	class IsRacerMemberDialog implements IsRacerMember
	{
		public boolean IsRacerAMember(Racer racer, Racer member)
		{
			IsRacerAMemberDialog isRacerAMember=new IsRacerAMemberDialog(racer, member);
			
			isRacerAMember.showDialog(GrandPrixScorerGui.this, "Hello");
			/*System.out.println("************************");
			System.out.println("Is this person a member (y/n)?");
			System.out.println(racer);
			System.out.println("************************");
			
			System.out.println("(they are possibly this person)");
			System.out.println(member);
			
			String yesOrNo=new java.util.Scanner(System.in).next();
			
			if (yesOrNo.equals("y"))
			{
				return true;
			}
			else
			{
				return false;
			}*/
			
			return true;
		
		}
	}
}