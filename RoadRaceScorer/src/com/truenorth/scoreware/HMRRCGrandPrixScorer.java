package com.truenorth.scoreware;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;

import com.truenorth.scoreware.apps.GrandPrixScorerApp;
import com.truenorth.scoreware.gui.GrandPrixScorerGui;

public class HMRRCGrandPrixScorer 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GrandPrixScorerApp app=new GrandPrixScorerApp();
				
				JFrame frame = new GrandPrixScorerGui(app);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(900, 700));
				frame.setSize(new Dimension(900, 700));
				frame.setVisible(true);
			}
		});
	}
}

