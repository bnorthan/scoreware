package com.truenorth.scoreware.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.awt.GridBagLayout;

import com.truenorth.scoreware.common.fromcorejava.GBC;

public class RaceInfoFrame extends JPanel
{
	JLabel idLabel=new JLabel("ID");
	JTextField id=new JTextField();
	String idString;
	
	JLabel label=new JLabel("Race Name");
	JTextField name=new JTextField();
	
	JLabel cityLabel=new JLabel("City");
	JTextField city=new JTextField();
	
	JLabel stateLabel=new JLabel("State");
	JTextField state=new JTextField();
	
	JLabel dateLabel=new JLabel("Date");
	JTextField date=new JTextField();
	
	JButton ok;
	JButton cancel;
	
	JDialog dialog; 

	public RaceInfoFrame()
	{
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
			
		ok=new JButton("OK");
		ok.addActionListener(new okAction());
		cancel=new JButton("Cancel");
		
		int i=0;
		add(idLabel, new GBC(0,i,1,1).setFill(GBC.BOTH));
		add(id, new GBC(1,i++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
		
		add(label, new GBC(0,i,1,1).setFill(GBC.BOTH));
		add(name, new GBC(1,i++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
	
		add(cityLabel, new GBC(0,i,1,1).setFill(GBC.BOTH));
		add(city, new GBC(1,i++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
	
		add(stateLabel, new GBC(0,i,1,1).setFill(GBC.BOTH));
		add(state, new GBC(1,i++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
	
		add(dateLabel, new GBC(0,i,1,1).setFill(GBC.BOTH));
		add(date, new GBC(1,i++,1,1).setFill(GBC.BOTH).setWeight(100, 0));
		
		add(cancel, new GBC(0,i+2,1,1).setWeight(0, 100));
		add(ok, new GBC(1,i+2,1,1));
		
		this.setSize(new Dimension(250, 250));
	}
	
	public void showDialog(Frame owner)
	{
		 dialog = new JDialog(owner, true);
		
		 dialog.add(this);
		 //OR, you can do the following...
		 //JDialog dialog = new JDialog();
		 //dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		 dialog.setBounds(350, 350, 200, 200);
		 dialog.setVisible(true);
	}
	
	public String getID()
	{
		return idString;
	}
	
	public String getName()
	{
		return name.getText();
	}
	
	public String getCity()
	{
		return city.getText();
	}
	
	public String getStateProvince()
	{
		return state.getText();
	}
	
	public String getDate()
	{
		return date.getText();
	}
	
	private class okAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			idString=id.getText();
			dialog.setVisible(false);
		}
	}
}
