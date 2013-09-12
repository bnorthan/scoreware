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

import com.truenorth.scoreware.extractors.HtmlExtractor;
import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.matchers.MatchSearcher;

import com.truenorth.scoreware.races.readers.hmrrc.WinterSeries2013Reader;
import com.truenorth.scoreware.races.readers.hmrrc.Sefcu2013Reader;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer;

import com.truenorth.scoreware.scoring.schemes.MaleFemaleScore;

import com.truenorth.scoreware.scoring.schemes.AgeGroup;
import com.truenorth.scoreware.scoring.schemes.AgeGroupScorer;

public class RoadRaceScorer 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new RoadRaceScorerMainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(500, 500));
				frame.setSize(new Dimension(500, 500));
				frame.setVisible(true);
			}
		});
	}
}

class RoadRaceScorerMainFrame extends JFrame
{
	private JTextArea myTextArea;
	private JScrollPane scrollPane;
	private JFrame mainFrame;
	
	public RoadRaceScorerMainFrame()
	{
		setTitle("Road Race Scorer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JPanel panel=new JPanel();
		
		JButton memberButton = new JButton("Open");
		memberButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		JButton raceButton = new JButton("Race");
		memberButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		panel.add(memberButton);
		panel.add(raceButton);
		
		// create a text area
		myTextArea = new JTextArea();
	    myTextArea.setBackground(Color.BLACK);
	    myTextArea.setForeground(Color.WHITE);
	    myTextArea.setEditable(false);
	    myTextArea.setMargin(new Insets(10, 10, 10, 10));
	    
	    myTextArea.setSize(400, 700);

	    scrollPane = new JScrollPane(myTextArea);
	    scrollPane.setPreferredSize(new Dimension(400, 300));
	   // scrollPane.set
	    scrollPane.setBackground(Color.BLACK);
		
	    add(scrollPane, BorderLayout.NORTH);
	    
	    add(panel);
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		memberButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				// prompt the user to choose the hmrrc membership list
				int success = chooser.showOpenDialog(null);
				
				// create a hmrrc reader to read it
				HmrrcExcelReader reader=new HmrrcExcelReader(chooser.getSelectedFile().getAbsolutePath());
				reader.read();
				
				// create a race reader to read the race data
				//Sefcu2013Reader raceReader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				Sefcu2013Reader raceReader=new Sefcu2013Reader("D:\\Brian2012\\hmrrc\\data\\13sefcu.htm");
				raceReader.read();
				
				ArrayList<Result> memberResults=new ArrayList<Result>();
				
				for (Result result:raceReader.getResults())
				{
					try
					{
						MatchSearcher matcher=new MatchSearcher();
						matcher.setInteractive(true);
						Racer match=matcher.searchForMatch(result.getRacer(), reader.getMembers());
					
						if (match!=null)
						{
							memberResults.add(result);
						}
						
					/*	if (result.getRacer().getLastName().equals("Sherman")
								&& result.getRacer().getFirstName().equals("Honor"))
						//if (result.getRacer().getLastName().equals("O'Connor T"))
						{
							System.out.println("Info for: "+result.getRacer().getFirstName()+" "+result.getRacer().getLastName());
							matcher.setVerbose(true);
							matcher.setMatchThreshold(40);
							matcher.searchForMatch(result.getRacer(), reader.getMembers());
							
							new java.util.Scanner(System.in).nextLine();
						}*/
					}
					catch (Exception objEx)
					{
						
					}
				}
				
				MaleFemaleScore sexScore=new MaleFemaleScore();
				
				sexScore.Score(memberResults);
				
				ArrayList<Result> females=sexScore.getFemales();
				ArrayList<Result> males=sexScore.getMales();
				
				for (Result result:females)
				{
					System.out.println(result);
				}
				
				for (Result result:males)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> male20=AgeGroupScorer.ScoreAge(males, 1, 29);
				System.out.println();
				
				for (Result result:male20)
				{
					System.out.println(result);
				}
						
				ArrayList<Result> male30=AgeGroupScorer.ScoreAge(males, 30, 39);
				
				System.out.println();
				
				for (Result result:male30)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> male40=AgeGroupScorer.ScoreAge(males, 40, 49);
				
				System.out.println();
				
				for (Result result:male40)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> male50=AgeGroupScorer.ScoreAge(males, 50, 59);
				
				System.out.println();
				
				for (Result result:male50)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> male60=AgeGroupScorer.ScoreAge(males, 60, 69);
				
				System.out.println();
				
				for (Result result:male60)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> male70=AgeGroupScorer.ScoreAge(males, 70, 99);
				
				System.out.println();
				
				for (Result result:male70)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> female20=AgeGroupScorer.ScoreAge(females, 1, 29);
				
				System.out.println();
				
				for (Result result:female20)
				{
					System.out.println(result);
				}
				ArrayList<Result> female30=AgeGroupScorer.ScoreAge(females, 30, 39);
				
				System.out.println();
				
				for (Result result:female30)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> female40=AgeGroupScorer.ScoreAge(females, 40, 49);
				
				System.out.println();
				
				for (Result result:female40)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> female50=AgeGroupScorer.ScoreAge(females, 50, 59);
				
				System.out.println();
				
				for (Result result:female50)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> female60=AgeGroupScorer.ScoreAge(females, 60, 69);
				
				System.out.println();
				
				for (Result result:female60)
				{
					System.out.println(result);
				}
				
				ArrayList<Result> female70=AgeGroupScorer.ScoreAge(females, 70, 99);
				
				System.out.println();
				
				for (Result result:female70)
				{
					System.out.println(result);
				}
				
				//System.out.println("There were "+raceReader.getResults().size()+" in the race");
				//System.out.println("There were "+memberResults.size()+" members in the race");
			}
		});
		
		raceButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				//ReadRaceResult readRace=new ReadRaceResult();
				
				//readRace.Read("http://www.hmrrc.com/View/PDFs/Results/13tawa1.htm");
				//readRace.Read("http://www.hmrrc.com/View/PDFs/Results/13colonie_mile.htm");
				//readRace.Read("http://www.hmrrc.com/View/PDFs/Results/13WS2_half.htm");
				
				//HtmlExtractor extractor = new HtmlExtractor();
				//ArrayList<String> list=extractor.extractText("http://www.hmrrc.com/View/PDFs/Results/13WS2_half.htm");
				
				//int l=1;
				//for(String str:list)
				//{
				//	String[] splitter=str.split("\\s+");
				//	int n=splitter.length;
				//	System.out.println(l+"!:!"+n+"!:!"+str);
				//}
				//WinterSeries2013Reader reader=new WinterSeries2013Reader("http://www.hmrrc.com/View/PDFs/Results/13WS2_half.htm");
				Sefcu2013Reader reader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				reader.read();
			}
		});
	}
	
	private JFileChooser chooser;
	
	private static final int DEFAULT_WIDTH=400;
	private static final int DEFAULT_HEIGHT=300;
	
	private static final int BUTTON_WIDTH=55;
	private static final int BUTTON_HEIGHT=55;
}
