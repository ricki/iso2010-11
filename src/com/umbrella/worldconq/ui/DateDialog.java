package com.umbrella.worldconq.ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;


public class DateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128501222928885944L;
	private boolean selection;
	private JPanel mainPanel;
	private JLabel dateLabel;
	private JTextField dateTextField;
	private JLabel gameHourLabel;
	private JTextField gameHourTextField;
	private JButton acceptButton;
	private JDateChooser jdFecha;
	private int hour;
	private int minutes;
	private boolean correct;
	private JLabel errorD;
	private JLabel errorH;
	Calendar c;
	
	public DateDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		initGUI();
	}
	
	private void initGUI() {


		mainPanel = new JPanel();
		BorderLayout thisLayout = new BorderLayout();
		getContentPane().setLayout(thisLayout);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(4,2,10,10));


		dateLabel = new JLabel();
		dateLabel.setText("Fecha de la partida:");
		
		jdFecha = new JDateChooser();
		jdFecha.setDateFormatString("dd/MM/yyyy");
		mainPanel.add(jdFecha);


		gameHourLabel = new JLabel();
		gameHourLabel.setText("Hora de la partida (hh:mm)");

		gameHourTextField = new JTextField();
		gameHourTextField .setToolTipText("Introduzca aquí la hora de la partida:");

		errorD = new JLabel();
		errorD.setText("Fecha introducida no válida");
		errorD.setForeground(new Color(255,0,0));
		errorD.setVisible(false);
		
		errorH = new JLabel();
		errorH.setText("Hora introducida no válida");
		errorH.setForeground(new Color(255,0,0));
		errorH.setVisible(false);
		
		acceptButton = new JButton("Aceptar");
		acceptButton.addMouseListener(new AcceptMouseAdapter(this, true));

		mainPanel.add(errorD);
		mainPanel.add(errorH);
		mainPanel.add(dateLabel);
		mainPanel.add(jdFecha);
		mainPanel.add(gameHourLabel);
		mainPanel.add(gameHourTextField);
		mainPanel.add(new JLabel(""));
		mainPanel.add(acceptButton);
		
		c = new GregorianCalendar();
		
		this.pack();
		

	}
	
	public boolean getSelection() {
		return selection;
	}
	
	public JTextField getDateTextField(){
		return dateTextField;
	}
	public JDateChooser getjdFecha(){
		return jdFecha;
	}
	
	public Calendar getDate(){
		return c;
	}

	
	

	private class AcceptMouseAdapter extends MouseAdapter {

		private DateDialog dlg;
		private boolean selection;	

		public AcceptMouseAdapter(DateDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		public void mouseClicked(MouseEvent evt) {
			if (correctDate()){
			
			dlg.selection = this.selection;
			dlg.setVisible(false);
			}
			else{
				errorD.setVisible(true);
			}
		}
		private boolean correctDate(){
			String [] aux= gameHourTextField.getText().split(":");
			correct=true;
			errorH.setVisible(false);
			errorD.setVisible(false);
			try{
				if (!(aux.length == 2 && aux[0].length() == 2 && aux[1].length()==2 &&
						Integer.parseInt(aux[0])<24 && Integer.parseInt(aux[1])<59)){
				 	correct = false;
					errorH.setVisible(true);
				}
					


			}catch(NumberFormatException e){
				correct=false;
			}
			if (correct == true){
				hour = Integer.parseInt(aux[0]);
				minutes = Integer.parseInt(aux[1]);
				c.setTime(jdFecha.getDate());
				c.set(Calendar.HOUR_OF_DAY, hour);
				c.set(Calendar.MINUTE, minutes);
				
			}
			if (c.before(Calendar.getInstance())){
				correct=false;
				errorD.setVisible(true);
			}
			return correct;
			
		}
	}



}