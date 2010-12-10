package com.umbrella.worldconq.ui;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateGameDialog extends JDialog{
	
	private static final long serialVersionUID = -5128501222928885944L;
	private boolean selection;
	private JPanel mainPanel;
	private JLabel gameNameLabel;
	private JTextField gameNameTextField;
	private JLabel gameDescLabel;
	private JTextField gameDescTextField;
	private JButton addPlaydateButton;
	private JButton deletePlaydateButton;
	private JLabel datesLabel;
	private JList datesList;
	private DefaultComboBoxModel datesListContent;
	private JButton createButton;
	private ArrayList<Calendar> calendarList;
	
	public CreateGameDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		initGUI();
	}
	
	private void initGUI() {
		this.setResizable(false);
		this.setSize(500, 300);

		mainPanel = new JPanel(new GridBagLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagConstraints c = new GridBagConstraints();





		gameNameLabel = new JLabel();
		gameNameLabel.setText("Nombre de la partida:");
		gameNameLabel.setHorizontalAlignment(JLabel.CENTER);
		gameNameLabel.setVerticalAlignment(JLabel.CENTER);

		gameNameTextField = new JTextField();
		gameNameTextField.setToolTipText("Introduzca aqui el nombre de la partida");

		gameDescLabel = new JLabel();
		gameDescLabel.setText("Descripción:");
		gameDescLabel.setHorizontalAlignment(JLabel.CENTER);
		gameDescLabel.setVerticalAlignment(JLabel.CENTER);

		gameDescTextField = new JTextField();
		gameDescTextField.setToolTipText("Introduzca una descripción de la partida");
		

		addPlaydateButton = new JButton("Añadir fecha de juego");
		addPlaydateButton .addMouseListener(new NewDateMouseAdapter());

		deletePlaydateButton = new JButton("Eliminar fecha");
		deletePlaydateButton .addMouseListener(new DeleteDateMouseAdapter(this, false));
		
		datesLabel = new JLabel();
		datesLabel.setText("Fechas de juego");
		
		datesListContent = new DefaultComboBoxModel();
		datesListContent.setSelectedItem(null);


		
		datesList = new JList();
		datesList.setModel(datesListContent);


		
		createButton = new JButton("Crear partida");
		createButton .addMouseListener(new CreateMouseAdapter(this, false));
		
		calendarList = new ArrayList<Calendar>();
		

		c.fill = c.BOTH;

		c.insets = new Insets(2, 2, 2, 2);
		c.weighty = 0.5;

		c.gridx=0;
		c.gridy=0;
		mainPanel.add(gameNameLabel,c);

		
		c.gridx=0;
		c.gridy=1;
		mainPanel.add(gameDescLabel,c);
		
		c.fill = c.HORIZONTAL;
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=4;
		mainPanel.add(gameNameTextField,c);
		
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=4;
		c.gridheight=1;
		mainPanel.add(gameDescTextField,c);
		 		
		c.gridx=3;
		c.gridy=3;
		c.gridwidth=2;
		c.gridheight=1;
		mainPanel.add(addPlaydateButton,c);
		
		c.gridx=3;
		c.gridy=4;
		c.gridwidth=2;
		c.gridheight=1;
		mainPanel.add(deletePlaydateButton,c);
			
		c.fill = c.BOTH;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		c.gridheight=1;
		mainPanel.add(datesLabel,c);
		
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.gridheight=3;
		mainPanel.add(datesList,c);
		
		c.gridx=0;
		c.gridy=6;
		c.gridwidth=5;
		c.gridheight=1;
		c.fill = c.HORIZONTAL;
		mainPanel.add(createButton,c);


		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.pack();
	}
	
	public boolean getSelection() {
		return selection;
	}
	
	public ArrayList<Calendar> getCalendarList(){
		return calendarList;
	}
	public String getDescription(){
		return gameDescTextField.getText();
	}
	public String getGameName(){
		return gameNameTextField.getText();
	}
	

	
	private class NewDateMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			DateDialog dlg= new DateDialog(new JFrame(), "Introduzca fecha", true);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
			if (dlg.getSelection() == true) {
				calendarList.add(dlg.getDate());
				datesListContent.addElement(DateFormat.getDateTimeInstance().format(dlg.getDate().getTime()));
				datesListContent.setSelectedItem(null);
			}
		}
	}
	private class DeleteDateMouseAdapter extends MouseAdapter {

		private CreateGameDialog dlg;
		private boolean selection;	

		public DeleteDateMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		public void mouseClicked(MouseEvent evt) {
			dlg.selection = this.selection;
			calendarList.remove(datesList.getSelectedIndex());
			datesListContent.removeElement(datesList.getSelectedValue());	
			dlg.setVisible(true);
		}
	}
	
	private class CreateMouseAdapter extends MouseAdapter {

		private CreateGameDialog dlg;
		private boolean selection;	

		public CreateMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;

			
		}

		public void mouseClicked(MouseEvent evt) {
			this.selection = true;
			dlg.selection = this.selection;
			dlg.setVisible(false);
		}
	}
}