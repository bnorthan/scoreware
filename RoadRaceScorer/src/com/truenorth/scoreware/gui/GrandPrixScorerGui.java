package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import com.truenorth.scoreware.apps.GrandPrixScorerApp;

import com.truenorth.scoreware.common.fromcorejava.GBC;

public class GrandPrixScorerGui extends JFrame
{
	public GrandPrixScorerGui(GrandPrixScorerApp grandPrixScorerApp)
	{
		this.grandPrixScorerApp=grandPrixScorerApp;
		
		setTitle("Grand Prix Scorer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		JButton membershipButton=new JButton("Open Membership");
		membershipButton.addActionListener(new membershipActionListener());
		
		JButton raceButton=new JButton("Open Race");
		
		JTextArea textArea=new JTextArea();
		textArea.setBorder(BorderFactory.createEtchedBorder());
		
		add(membershipButton, new GBC(0,0).setAnchor(GBC.CENTER));
		add(raceButton, new GBC(0,1).setAnchor(GBC.CENTER));
		add(textArea, new GBC(1,0,2,4).setFill(GBC.BOTH).setWeight(100, 100));
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
			grandPrixScorerApp.setRaceSourceName("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
			
			grandPrixScorerApp.Score();
		}
	}
}