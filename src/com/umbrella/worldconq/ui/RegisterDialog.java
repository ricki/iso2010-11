package com.umbrella.worldconq.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class RegisterDialog extends JDialog {
	
	private JPanel registerPanel;
	private JButton CancelButton, AcceptButton;
	private boolean selection;

	public RegisterDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		JPanel p = new JPanel();
		AcceptButton = new JButton("Aceptar");
		CancelButton = new JButton("Cancelar");
		AcceptButton.addMouseListener(new AcceptDialogAdapter(this, true));
		CancelButton.addMouseListener(new AcceptDialogAdapter(this, false));
		p.add(AcceptButton);
		p.add(CancelButton);
		this.setContentPane(p);
		this.pack();
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
