package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.truenorth.scoreware.common.fromcorejava.GBC;
import com.truenorth.scoreware.data.Racer;

public class IsRacerAMemberDialog extends JPanel
{
	public IsRacerAMemberDialog(Racer racer, Racer member)
	{
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		int row=0;
		
		add(new JLabel("Is this person a member?"), new GBC(0,row).setAnchor(GBC.WEST));
		row++;
		
		add(new JLabel("Name: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(racer.getFirstName()+" "+racer.getLastName()), new GBC(1,row));
		row++;
		
		add(new JLabel("City: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(racer.getCity()), new GBC(1,row));
		row++;
		
		add(new JLabel("Age: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(racer.getAge()+""), new GBC(1,row));
		row++;
		
		add(new JLabel("Sex: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(racer.getSex()+""), new GBC(1,row));
		row++;
		
		add(new JLabel(" "), new GBC(0,row));
		row++;
		
		add(new JLabel("They are possibly this person..."), new GBC(0,row).setAnchor(GBC.WEST));
		row++;
		
		add(new JLabel("Name: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(member.getFirstName()+" "+member.getLastName()), new GBC(1,row));
		row++;
		
		add(new JLabel("City: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(member.getCity()), new GBC(1,row));
		row++;
		
		add(new JLabel("Age: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(member.getAge()+""), new GBC(1,row));
		row++;
		
		add(new JLabel("Sex: "), new GBC(0,row).setAnchor(GBC.WEST));
		add(new JLabel(member.getSex()+""), new GBC(1,row));
		row++;
		
		add(new JLabel(" "), new GBC(0,row));
		row++;
		
		
		JButton yesButton=new JButton("Yes");
		
		yesButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ok=true;
				dialog.setVisible(false);
			}
		});
		
		JButton noButton=new JButton("No");
		
		noButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ok=false;
				dialog.setVisible(false);
			}
		});
		
		add(yesButton, new GBC(0,row));
		add(noButton, new GBC(1,row));
		
		
	}
	
	public boolean showDialog(Component parent, String title)
	{
		ok=false;
		
		dialog=new JDialog((Frame)parent, true);
		dialog.add(this);
		dialog.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		dialog.setVisible(true);
		
		return ok;
	}
	
	public boolean getOK()
	{
		return ok;
	}
	
	private static int DEFAULT_WIDTH=400;
	private static int DEFAULT_HEIGHT=300;
	
	private JDialog dialog;
	private boolean ok;
	JPanel panel;
}
