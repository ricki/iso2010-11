package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
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


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class CreateGameDialog extends JDialog {
	
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
	
	public CreateGameDialog(JFrame f, String string, boolean b) {
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


		gameNameLabel = new JLabel();
		gameNameLabel.setText("Nombre de la partida:");
		gameNameLabel.setBounds(30, 10, 143, 16);

		gameNameTextField = new JTextField();
		gameNameTextField.setBounds(170, 10, 295, 30);
		gameNameTextField.setToolTipText("Introduzca aqui el nombre de la partida");
		//gameNameTextField.addKeyListener(new AcceptDialogKeyAdapter(this));

		gameDescLabel = new JLabel();
		gameDescLabel .setText("Descripción:");
		gameDescLabel .setBounds(35, 40, 143, 16);

		gameDescTextField = new JTextField();
		gameDescTextField.setBounds(170, 40, 295, 60);
		gameDescTextField.setToolTipText("Introduzca una descripción de la partida");
		//gameDescTextField.addKeyListener(new AcceptDialogKeyAdapter(this));

		

		addPlaydateButton = new JButton("Añadir fecha de juego");
		addPlaydateButton .setBounds(260, 110, 100, 30);
		addPlaydateButton.setSize(200, 25);
		addPlaydateButton .addMouseListener(new NewDateMouseAdapter(this));

		deletePlaydateButton = new JButton("Eliminar fecha");
		deletePlaydateButton .setBounds(260, 150, 60, 25);
		deletePlaydateButton.setSize(140, 25);
		deletePlaydateButton .addMouseListener(new DeleteDateMouseAdapter(this));
		
		datesLabel = new JLabel();
		datesLabel.setText("Fechas de juego");
		datesLabel.setBounds(45, 105, 143, 16);
		
		datesListContent = new DefaultComboBoxModel();
		datesListContent.setSelectedItem(null);


		
		datesList = new JList();
		datesList.setBounds(12, 133, 236, 117);
		datesList.setModel(datesListContent);


		
		createButton = new JButton("Crear partida");
		createButton.setBounds(320, 225, 140, 25);
		createButton.addMouseListener(new AcceptDialogMouseAdapter(this, true));
		


		mainPanel.add(gameNameLabel);
		mainPanel.add(gameNameTextField );
		mainPanel.add(gameDescLabel);
		mainPanel.add(gameDescTextField);
		mainPanel.add(addPlaydateButton);
		mainPanel.add(deletePlaydateButton);
		mainPanel.add(datesLabel);
		mainPanel.add(datesList);
		mainPanel.add(createButton);
		
	}
	
	public boolean getSelection() {
		return selection;
	}
	

	
	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private CreateGameDialog dlg;
		private boolean selection;	

		public AcceptDialogMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		public void mouseClicked(MouseEvent evt) {
			dlg.selection = this.selection;
			dlg.setVisible(false);
		}
	}
	
	private class NewDateMouseAdapter extends MouseAdapter {

		private CreateGameDialog dlg;

		public NewDateMouseAdapter(CreateGameDialog dlg) {
			this.dlg = dlg;
		}

		public void mouseClicked(MouseEvent evt) {
			DateDialog dlg = new DateDialog(new JFrame(), "Introduzca fecha", true);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
			if (dlg.getSelection() == true) {
				try{
					this.dlg.datesListContent.addElement((dlg.getDateTextField().getText())+"/t("+dlg.getGameLenTextField().getText()+" minutos)");
					this.dlg.datesListContent.setSelectedItem(null);
					
				} catch(Exception e) {
//					stw.NoticeLabel.setText(" Error en el registro");
//					NoticeLabel.setForeground(new Color (255, 0, 0));
				}	
			}
		}
	}
	private class DeleteDateMouseAdapter extends MouseAdapter {

		private CreateGameDialog dlg;

		public DeleteDateMouseAdapter(CreateGameDialog dlg) {
			this.dlg = dlg;
		}

		public void mouseClicked(MouseEvent evt) {
			datesListContent.removeElement(datesList.getSelectedValue());
			dlg.setVisible(true);
		}
	}
	
}

