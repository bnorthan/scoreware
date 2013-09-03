package com.runware;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import java.util.ArrayList;
import com.truenorth.scoreware.membership.readers.hmrrc.*;

import com.truenorth.scoreware.extractors.HtmlExtractor;
import com.truenorth.scoreware.races.readers.RaceReader;
import com.truenorth.scoreware.matchers.MatchSearcher;

import com.truenorth.scoreware.races.readers.hmrrc.WinterSeries2013Reader;
import com.truenorth.scoreware.races.readers.hmrrc.Sefcu2013Reader;

import com.truenorth.scoreware.Result;
import com.truenorth.scoreware.Racer;

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
				frame.setVisible(true);
			}
		});
	}
}

class RoadRaceScorerMainFrame extends JFrame
{
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
		
		add(panel);
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		memberButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				int success = chooser.showOpenDialog(null);
				
				//ReadMembershipExcel readExcel=new ReadMembershipExcel();
				
				//readExcel.Read(chooser.getSelectedFile().getAbsolutePath());
				
				HmrrcExcelReader reader=new HmrrcExcelReader(chooser.getSelectedFile().getAbsolutePath());
				reader.read();
				
				Sefcu2013Reader raceReader=new Sefcu2013Reader("http://www.hmrrc.com/View/PDFs/Results/13sefcu.htm");
				raceReader.read();
				
				ArrayList<Result> memberResults=new ArrayList<Result>();
				
				for (Result result:raceReader.getResults())
				{
					try
					{
						MatchSearcher matcher=new MatchSearcher();
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
				
				System.out.println("There were "+raceReader.getResults().size()+" in the race");
				System.out.println("There were "+memberResults.size()+" members in the race");
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
