package com.umbrella.worldconq.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


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
	
	public RegisterDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		initGUI(f);
	}
	
		private void initGUI(JFrame f) {
		try {
			JPanel p = new JPanel();
			try {
				this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("home/antonio/workspace/iso2010-11/src/image/umbrella.png")).getImage());
			} catch (Exception e) {
				System.out.println("Imagen no encontrada");
			}
			
			AcceptButton = new JButton("Aceptar");
			CancelButton = new JButton("Cancelar");
			AcceptButton.addMouseListener(new AcceptDialogAdapter(this, true));
			CancelButton.addMouseListener(new AcceptDialogAdapter(this, false));
			p.add(AcceptButton);
			p.add(CancelButton);
			
			map = new JLabel();
			//map.setIcon(new ImageIcon(getClass().getClassLoader().getResource("src/images/mapa.png")));
			map.setBounds(0, 6, 472, 257);
			p.add(map);
						
			this.setContentPane(p);
			this.pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getSelection() {
		return selection;
	}
	
	
	private class AcceptDialogAdapter extends MouseAdapter {
		
		private RegisterDialog dlg;
		private boolean selection;	
		
		public AcceptDialogAdapter(RegisterDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}
		
		public void mouseClicked(MouseEvent evt) {
			dlg.selection = this.selection;
			dlg.setVisible(false);
		}
	}
	
}
