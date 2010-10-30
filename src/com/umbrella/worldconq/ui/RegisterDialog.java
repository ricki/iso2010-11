package com.umbrella.worldconq.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.BorderLayout;

public class RegisterDialog extends JDialog {
	
	private JPanel registerPanel;
	private JButton CancelButton, AcceptButton;
	

	private JLabel mapLabel;
	
	private JLabel UserLabel;
	private JLabel EmailLabel;
	private JLabel PasswdLabel;
	
	private JTextField UserTextField;
	private JTextField EmailTextField;
	private  JPasswordField PasswdField;
	
	private boolean selection;
	
	{
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public RegisterDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		initGUI();
	}
	
		private void initGUI() {
		try {
			this.setResizable(false);
			
			registerPanel = new JPanel();
			getContentPane().add(registerPanel, BorderLayout.CENTER);
			registerPanel.setLayout(null);
			registerPanel.addKeyListener(new AcceptDialogKeyAdapter(this));
			
			try {
				this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("home/antonio/workspace/iso2010-11/src/image/umbrella.png")).getImage());
			} catch (Exception e) {
				System.out.println("Imagen no encontrada");
			}
			
			
			UserLabel = new JLabel();
			UserLabel.setText("Nombre de usuario :");
			UserLabel.setBounds(35, 23, 143, 16);
			
			UserTextField = new JTextField();
			UserTextField.setBounds(170, 20, 295, 30);
			UserTextField.setToolTipText("Introduzca aqui su nombre de usuario");

			EmailLabel = new JLabel();
			EmailLabel.setText("Email :");
			EmailLabel.setBounds(35, 63, 143, 16);
			
			EmailTextField = new JTextField();
			EmailTextField.setBounds(170, 60, 295, 30);
			EmailTextField.setToolTipText("Introduzca aqui su correo electrónico");

			PasswdLabel = new JLabel();
			PasswdLabel.setText("Contraseña :");
			PasswdLabel.setBounds(35, 103, 143, 16);
			
			PasswdField = new JPasswordField();
			PasswdField.setBounds(170, 100, 295, 30);
			PasswdField.setToolTipText("Introduzca aqui su contraseña");
			PasswdField.addKeyListener(new AcceptDialogKeyAdapter(this));
			
			AcceptButton = new JButton("Aceptar");
			AcceptButton.setBounds(140, 150, 100, 30);
			AcceptButton.addMouseListener(new AcceptDialogMouseAdapter(this, true));
				
			CancelButton = new JButton("Cancelar");
			CancelButton.setBounds(260, 150, 100, 30);
			CancelButton.addMouseListener(new AcceptDialogMouseAdapter(this, false));
				
			
			mapLabel = new JLabel();
			//map.setIcon(new ImageIcon(getClass().getClassLoader().getResource("src/images/mapa.png")));
			mapLabel.setBounds(0, 6, 472, 257);
			
			registerPanel.add(UserLabel);
			registerPanel.add(UserTextField);
			registerPanel.add(EmailLabel);
			registerPanel.add(EmailTextField);
			registerPanel.add(PasswdLabel);
			registerPanel.add(PasswdField);
			registerPanel.add(AcceptButton);
			registerPanel.add(CancelButton);
			
			//registerPanel.add(mapLabel);
				
			this.setContentPane(registerPanel);
		
		
			this.setSize(500, 230);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getSelection() {
		return selection;
	}
	
	public String getUser() {
		return UserTextField.getText();
		
	}
	
	public String getEmail() {
		return EmailTextField.getText();
		
	}
	
	@SuppressWarnings("deprecation")
	public String getPasswd() {
		return PasswdField.getText();		
	}
	
	private class AcceptDialogMouseAdapter extends MouseAdapter {
		
		private RegisterDialog dlg;
		private boolean selection;	
		
		public AcceptDialogMouseAdapter(RegisterDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}
		
		public void mouseClicked(MouseEvent evt) {
			dlg.selection = this.selection;
			dlg.setVisible(false);
		}
	}
	
	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private RegisterDialog dlg;
		
		public  AcceptDialogKeyAdapter (RegisterDialog dlg) {
			this.dlg = dlg;
		}
		
		public void keyPressed(KeyEvent evt) {
			if(evt.getKeyCode() == 10){
				dlg.selection = true;
				dlg.setVisible(false);
			}
			else if(evt.getKeyCode() == 27){
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}
}
