package com.runware;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import java.util.ArrayList;
import com.truenorth.scoreware.membership.readers.hmrrc.*;

import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.matchers.MatchSearcher;

import com.truenorth.scoreware.races.readers.hmrrc.WinterSeries2013Reader;
import com.truenorth.scoreware.races.readers.hmrrc.Sefcu2013Reader;
import com.truenorth.scoreware.races.readers.hmrrc.Anniversary2013Reader;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer;

import com.truenorth.scoreware.scoring.schemes.MaleFemaleScore;

import com.truenorth.scoreware.scoring.schemes.AgeGroup;
import com.truenorth.scoreware.scoring.schemes.AgeGroupScorer;

import com.truenorth.scoreware.matchers.IsRacerMember;

import com.truenorth.scoreware.apps.GrandPrixScorerApp;
import com.truenorth.scoreware.gui.GrandPrixScorerGui;

public class RoadRaceScorer 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GrandPrixScorerApp app=new GrandPrixScorerApp();
				//JFrame frame = new RoadRaceScorerMainFrameTemp();
				JFrame frame = new GrandPrixScorerGui(app);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(900, 700));
				frame.setSize(new Dimension(900, 700));
				frame.setVisible(true);
			}
		});
	}
}
