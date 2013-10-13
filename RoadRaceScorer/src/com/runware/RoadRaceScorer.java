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
				frame.setPreferredSize(new Dimension(500, 500));
				frame.setSize(new Dimension(500, 500));
				frame.setVisible(true);
			}
		});
	}
}

class RoadRaceScorerMainFrameTemp extends JFrame
{
	
	public RoadRaceScorerMainFrameTemp()
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
		ActionListener action1=new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				// prompt the user to choose the hmrrc membership list
				int success = chooser.showOpenDialog(null);
				
				// create a hmrrc reader to read it
				reader=new HmrrcExcelReader(chooser.getSelectedFile().getAbsolutePath());
				reader.read();
				
				// create a race reader to read the race data
				//Sefcu2013Reader raceReader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				//Sefcu2013Reader raceReader=new Sefcu2013Reader("D:\\Brian2012\\hmrrc\\data\\13sefcu.htm");
				Anniversary2013Reader raceReader=new Anniversary2013Reader("http://www.hmrrc.com/View/PDFs/Results/13anniv6.htm");
				
				raceReader.read();
				
				ArrayList<Result> memberResults=new ArrayList<Result>();
				
				// loop through all the results to check wether these people are members
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
						
					}
					catch (Exception objEx)
					{
						
					}
				}
				
				// divide into male and female
				MaleFemaleScore sexScore=new MaleFemaleScore();
				
				sexScore.Score(memberResults);
				
				ArrayList<Result> females=sexScore.getFemales();
				ArrayList<Result> males=sexScore.getMales();
				
				////////////////////////////////////////////////////////////////////////////
				// do each age group for females
				
				for (Result result:females)
				{
					System.out.println(result);
				}
				
				for (Result result:males)
				{
					System.out.println(result);
				}
				
				////////////////////////////////////////////////////////////////////////////////
				// do each age group for males
				
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
		};
		
		ActionListener action2=new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				// prompt the user to choose the hmrrc membership list
				int success = chooser.showOpenDialog(null);
				
				// create a hmrrc reader to read it
				reader=new HmrrcExcelReader(chooser.getSelectedFile().getAbsolutePath());
				reader.read();
				
				// create a race reader to read the race data
				Sefcu2013Reader raceReader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				//Sefcu2013Reader raceReader=new Sefcu2013Reader("D:\\Brian2012\\hmrrc\\data\\13sefcu.htm");
				//Anniversary2013Reader raceReader=new Anniversary2013Reader("http://www.hmrrc.com/View/PDFs/Results/13anniv6.htm");
				
				raceReader.read();
				
				// divide into male and female
				MaleFemaleScore sexScore=new MaleFemaleScore();
				
				sexScore.Score(raceReader.getResults());
				
				ArrayList<Result> females=sexScore.getFemales();
				ArrayList<Result> males=sexScore.getMales();
				
				System.out.println("num fe "+females.size());
				System.out.println("num ma "+males.size());
				
				////////////////////////////////////////////////////////////////////////////////
				// do each age group for males
				
				ArrayList<Result> male20=AgeGroupScorer.ScoreAge(males, 1, 29);
				male20=findMembers(male20);
				
				System.out.println("num ma20 "+male20.size());
				
				ArrayList<Result> male30=AgeGroupScorer.ScoreAge(males, 30, 39);
				male30=findMembers(male30);
				
				ArrayList<Result> male40=AgeGroupScorer.ScoreAge(males, 40, 49);
				male40=findMembers(male40);
				
				ArrayList<Result> male50=AgeGroupScorer.ScoreAge(males, 50, 59);
				male50=findMembers(male50);
				
				ArrayList<Result> male60=AgeGroupScorer.ScoreAge(males, 60, 69);
				male60=findMembers(male60);
				
				ArrayList<Result> male70=AgeGroupScorer.ScoreAge(males, 70, 99);
				male70=findMembers(male70);
				
				ArrayList<Result> female20=AgeGroupScorer.ScoreAge(females, 1, 29);
				female20=findMembers(female20);
				
				ArrayList<Result> female30=AgeGroupScorer.ScoreAge(females, 30, 39);
				female30=findMembers(female30);
				
				ArrayList<Result> female40=AgeGroupScorer.ScoreAge(females, 40, 49);
				female40=findMembers(female40);
				
				ArrayList<Result> female50=AgeGroupScorer.ScoreAge(females, 50, 59);
				female50=findMembers(female50);
				
				ArrayList<Result> female60=AgeGroupScorer.ScoreAge(females, 60, 69);
				female60=findMembers(female60);
				
				ArrayList<Result> female70=AgeGroupScorer.ScoreAge(females, 70, 99);
				female70=findMembers(female70);
				
				for (Result result:male20)
				{
					System.out.println(result);
				}
						
				System.out.println();
				
				for (Result result:male30)
				{
					System.out.println(result);
				}
				
				System.out.println();
				
				for (Result result:male40)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:male50)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:male60)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:male70)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:female20)
				{
					System.out.println(result);
				}
				
				System.out.println();
				
				for (Result result:female30)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:female40)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:female50)
				{
					System.out.println(result);
				}
				
				System.out.println();
				
				for (Result result:female60)
				{
					System.out.println(result);
				}
				
				
				System.out.println();
				
				for (Result result:female70)
				{
					System.out.println(result);
				}
				
				//System.out.println("There were "+raceReader.getResults().size()+" in the race");
				//System.out.println("There were "+memberResults.size()+" members in the race");
			}
		};
		
		
		
		memberButton.addActionListener(action2);
				
		
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
			//	Sefcu2013Reader reader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				//reader.read();
			}
		});
	}
	
	ArrayList<Result> findMembers(ArrayList<Result> racers)
	{
		ArrayList<Result> members=new ArrayList<Result>();
		
		resultsLoop:
		for (Result result:racers)
		{
			try
			{
				MatchSearcher matcher=new MatchSearcher();
				matcher.setInteractive(true);
				matcher.setIsRacerMember(new IsRacerMemberCommandLine());
				Racer match=matcher.searchForMatch(result.getRacer(), reader.getMembers());
		
				if (match!=null)
				{
					members.add(result);
				}
			
			}
			catch (Exception objEx)
			{
			
			}
			
			if (members.size()==10)
			{
				System.out.println("10 found!");
				break resultsLoop;
			}
		}
		
		return members;
	}
	
	private JTextArea myTextArea;
	private JScrollPane scrollPane;
	private JFrame mainFrame;
	
	
	private JFileChooser chooser;
	
	private static final int DEFAULT_WIDTH=400;
	private static final int DEFAULT_HEIGHT=300;
	
	private static final int BUTTON_WIDTH=55;
	private static final int BUTTON_HEIGHT=55;
	
	HmrrcExcelReader reader;
}

class IsRacerMemberCommandLine implements IsRacerMember
{
	public boolean IsRacerAMember(Racer racer, Racer member)
	{
		System.out.println("************************");
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
		}
	
	}
}


///////////////////////////////////////////////////////////////////////////////////////////////
// Temp area for some scrap debugging code

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
