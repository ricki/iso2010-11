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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

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
	private JLabel defTimeLabel;
	private JSpinner defTimeSpinner;
	private JLabel negTimeLabel;
	private JSpinner negTimeSpinner;
	private JLabel turnTimeLabel;
	private JSpinner turnTimeSpinner;
	private JButton createButton;
	private JButton cancelButton;
	private ArrayList<Calendar> calendarList;
	private JScrollPane scroll;
	private JLabel errorLabel;

	public CreateGameDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		this.initGUI();
	}

	private void initGUI() {
		this.setResizable(false);
		//this.setSize(500, 300);

		mainPanel = new JPanel(new GridBagLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		final GridBagConstraints c = new GridBagConstraints();

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
		addPlaydateButton.addMouseListener(new NewDateMouseAdapter());

		deletePlaydateButton = new JButton("Eliminar fecha");
		deletePlaydateButton.addMouseListener(new DeleteDateMouseAdapter(this,
			false));

		datesLabel = new JLabel();
		datesLabel.setText("Fechas de juego");

		datesListContent = new DefaultComboBoxModel();
		datesListContent.setSelectedItem(null);

		datesList = new JList();
		datesList.setModel(datesListContent);

		turnTimeLabel = new JLabel();
		turnTimeLabel.setText("Tiempo de turno (segundos):");
		turnTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		turnTimeLabel.setVerticalAlignment(JLabel.CENTER);

		turnTimeSpinner = new JSpinner(new SpinnerNumberModel(60, 1, 999, 1));
		//	turnTimeTextField.setToolTipText("Introduzca aqui tiempo del turno");

		defTimeLabel = new JLabel();
		defTimeLabel.setText("Tiempo de defensa (segundos):");
		defTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		defTimeLabel.setVerticalAlignment(JLabel.CENTER);

		defTimeSpinner = new JSpinner(new SpinnerNumberModel(60, 1, 999, 1));

		negTimeLabel = new JLabel();
		negTimeLabel.setText("Tiempo de negociación (segundos):");
		negTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		negTimeLabel.setVerticalAlignment(JLabel.CENTER);

		negTimeSpinner = new JSpinner(new SpinnerNumberModel(60, 1, 999, 1));

		createButton = new JButton("Crear partida");
		createButton.addMouseListener(new CreateMouseAdapter(this, false));

		cancelButton = new JButton("Cancelar");
		cancelButton.addMouseListener(new CancelMouseAdapter(this, false));

		errorLabel = new JLabel("¡ Datos erróneos o insuficientes !");
		errorLabel.setHorizontalAlignment(JLabel.CENTER);

		calendarList = new ArrayList<Calendar>();
		scroll = new JScrollPane();
		scroll.setViewportView(datesList);

		c.fill = GridBagConstraints.BOTH;

		c.insets = new Insets(2, 2, 2, 2);
		c.weighty = 0.5;

		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(gameNameLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(gameDescLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		mainPanel.add(gameNameTextField, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
		mainPanel.add(gameDescTextField, c);

		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		mainPanel.add(addPlaydateButton, c);

		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 1;
		mainPanel.add(deletePlaydateButton, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		mainPanel.add(datesLabel, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 2;
		mainPanel.add(scroll, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
		c.gridheight = 2;
		mainPanel.add(errorLabel, c);
		errorLabel.setForeground(new java.awt.Color(255, 0, 0));
		errorLabel.setVisible(false);

		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(createButton, c);

		c.gridx = 3;
		c.gridy = 11;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(cancelButton, c);

		c.gridx = 0;
		c.gridy = 6;
		mainPanel.add(turnTimeLabel, c);

		c.gridx = 3;
		c.gridy = 6;
		mainPanel.add(turnTimeSpinner, c);

		c.gridx = 0;
		c.gridy = 7;
		mainPanel.add(defTimeLabel, c);

		c.gridx = 3;
		c.gridy = 7;
		mainPanel.add(defTimeSpinner, c);

		c.gridx = 0;
		c.gridy = 8;
		mainPanel.add(negTimeLabel, c);

		c.gridx = 3;
		c.gridy = 8;
		mainPanel.add(negTimeSpinner, c);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}

	public boolean getSelection() {
		return selection;
	}

	public ArrayList<Calendar> getCalendarList() {
		return calendarList;
	}

	public String getDescription() {
		return gameDescTextField.getText();
	}

	public String getGameName() {
		return gameNameTextField.getText();
	}

	public int getNegTime() {
		return ((SpinnerNumberModel) negTimeSpinner.getModel()).getNumber().intValue();
	}

	public int getDefTime() {
		return ((SpinnerNumberModel) defTimeSpinner.getModel()).getNumber().intValue();
	}

	public int getTurnTime() {
		return ((SpinnerNumberModel) turnTimeSpinner.getModel()).getNumber().intValue();
	}

	private class NewDateMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			final DateDialog dlg = new DateDialog(new JFrame(),
				"Introduzca fecha", true);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
			if (dlg.getSelection() == true) {
				calendarList.add(dlg.getDate());
				datesListContent.addElement(DateFormat.getDateTimeInstance().format(
					dlg.getDate().getTime()));
				datesListContent.setSelectedItem(null);
			}
		}
	}

	private class DeleteDateMouseAdapter extends MouseAdapter {

		private final CreateGameDialog dlg;
		private final boolean selection;

		public DeleteDateMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			dlg.selection = selection;
			calendarList.remove(datesList.getSelectedIndex());
			datesListContent.removeElement(datesList.getSelectedValue());
			dlg.setVisible(true);
		}
	}

	private class CreateMouseAdapter extends MouseAdapter {

		private final CreateGameDialog dlg;
		private boolean selection;

		public CreateMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;

		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (dlg.getGameName() != "" && dlg.getGameName() != null &&
					!dlg.getGameName().isEmpty()
					&& CreateGameDialog.this.getCalendarList().size() > 0) {
				selection = true;
				dlg.selection = selection;
				dlg.setVisible(false);
			} else
				errorLabel.setVisible(true);
		}
	}

	private class CancelMouseAdapter extends MouseAdapter {

		private final CreateGameDialog dlg;
		private boolean selection;

		public CancelMouseAdapter(CreateGameDialog dlg, boolean selection) {
			this.dlg = dlg;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			selection = false;
			dlg.selection = selection;
			dlg.setVisible(false);
		}
	}
}
