package com.runware;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import com.truenorth.scoreware.apps.TUResultsApp;
import com.truenorth.scoreware.gui.TUResultsGui;

public class TUResultsScorer 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				TUResultsApp app=new TUResultsApp();
				//JFrame frame = new RoadRaceScorerMainFrameTemp();
				JFrame frame = new TUResultsGui(app);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(500, 500));
				frame.setSize(new Dimension(500, 500));
				frame.setVisible(true);
			}
		});
	}
}
