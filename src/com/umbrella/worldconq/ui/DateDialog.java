package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class DateDialog extends JDialog{
	private static final long serialVersionUID = -5128501222928885944L;
	private boolean selection;
	private JPanel mainPanel;
	private JLabel dateLabel;
	private JTextField dateTextField;
	private JLabel gameLenLabel;
	private JTextField gameLenTextField;
	private JButton acceptButton;

	
	public DateDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		initGUI();
	}
	
	private void initGUI() {
		this.setResizable(false);
		this.setSize(500, 300);

		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		try {
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/logo.png")).getImage());
		} catch (Exception e) {
			System.out.println("Imagen no encontrada");
		}
		dateLabel = new JLabel();
		dateLabel.setText("Fecha de la partida aaaa-mm-dd");
		dateLabel.setBounds(30, 10, 143, 16);

		dateTextField = new JTextField();
		dateTextField.setBounds(170, 10, 295, 30);
		dateTextField.setToolTipText("Introduzca aqui la fecha de la partida");

		gameLenLabel = new JLabel();
		gameLenLabel.setText("Duración de la partida (minutos):");
		gameLenLabel.setBounds(30, 40, 250, 50);

		gameLenTextField = new JTextField();
		gameLenTextField .setBounds(170, 80, 200, 30);
		gameLenTextField .setToolTipText("Introduzca aquí la duración de la partida");
		
		acceptButton = new JButton("Aceptar");
		acceptButton.setBounds(260, 150, 60, 25);
		acceptButton.setSize(140, 25);
		acceptButton.addMouseListener(new AcceptMouseAdapter(this, true));
		
		
		mainPanel.add(dateLabel);
		mainPanel.add(dateTextField);
		mainPanel.add(gameLenLabel);
		mainPanel.add(gameLenTextField);
		mainPanel.add(acceptButton);		
	}
	
	public boolean getSelection() {
		return selection;
	}
	
	public JTextField getDateTextField(){
		return dateTextField;
	}
	public JTextField getGameLenTextField(){
		return gameLenTextField;
	}
	

	private class AcceptMouseAdapter extends MouseAdapter {

		private DateDialog dlg;
		private boolean selection;	

		public AcceptMouseAdapter(DateDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		public void mouseClicked(MouseEvent evt) {
			dlg.selection = this.selection;
			dlg.setVisible(false);
		}
	}


}
