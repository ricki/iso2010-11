package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.toedter.calendar.JDateChooser;

public class DateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128501222928885944L;
	private boolean selection;
	private JPanel mainPanel;
	private JLabel dateLabel;
	private JLabel emptyLabel;
	private JTextField dateTextField;
	private JLabel gameHourLabel;
	private JButton acceptButton;
	private JButton cancelButton;
	private JDateChooser jdFecha;
	private boolean correct;
	private JSpinner hourMin;
	private JLabel errorD;

	Calendar c;

	public DateDialog(JFrame f, String string, boolean b) {
		super(f, string, b);
		this.initGUI();
	}

	private void initGUI() {

		mainPanel = new JPanel();
		final BorderLayout thisLayout = new BorderLayout();
		this.getContentPane().setLayout(thisLayout);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

		dateLabel = new JLabel();
		dateLabel.setText("Fecha de la partida:");

		emptyLabel = new JLabel();

		jdFecha = new JDateChooser();
		jdFecha.setDateFormatString("dd/MM/yyyy");
		jdFecha.setDate(Calendar.getInstance().getTime());

		gameHourLabel = new JLabel();
		gameHourLabel.setText("Hora de la partida (HH:mm)");

		final SpinnerDateModel spinnerDateModel = new SpinnerDateModel(
			new Date(), null,
			null, Calendar.HOUR_OF_DAY);
		hourMin = new JSpinner(spinnerDateModel);
		final JFormattedTextField tf = ((JSpinner.DefaultEditor) hourMin.getEditor()).getTextField();
		final DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
		final DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
		formatter.setFormat(new SimpleDateFormat("HH:mm"));

		errorD = new JLabel();
		errorD.setText("Fecha introducida no válida");
		errorD.setForeground(new Color(255, 0, 0));
		errorD.setVisible(false);

		acceptButton = new JButton("Aceptar");
		acceptButton.addMouseListener(new AcceptMouseAdapter(this, true));

		cancelButton = new JButton("Cancelar");
		cancelButton.addMouseListener(new CancelMouseAdapter(this, false));

		mainPanel.add(errorD);
		mainPanel.add(emptyLabel);
		mainPanel.add(dateLabel);
		mainPanel.add(jdFecha);
		mainPanel.add(gameHourLabel);
		mainPanel.add(hourMin);
		mainPanel.add(acceptButton);
		mainPanel.add(cancelButton);

		hourMin.getModel().setValue(hourMin.getModel().getNextValue());
		hourMin.getModel().setValue(hourMin.getModel().getPreviousValue());
		c = new GregorianCalendar();

		this.pack();

	}

	public boolean getSelection() {
		return selection;
	}

	public JTextField getDateTextField() {
		return dateTextField;
	}

	public JDateChooser getjdFecha() {
		return jdFecha;
	}

	public Calendar getDate() {
		return c;
	}

	private class AcceptMouseAdapter extends MouseAdapter {

		private final DateDialog dlg;
		private final boolean selection;

		public AcceptMouseAdapter(DateDialog dlg, boolean selection) {
			this.dlg = dlg;
			this.selection = selection;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (this.correctDate()) {

				dlg.selection = selection;
				dlg.setVisible(false);
			} else {
				errorD.setVisible(true);
			}
		}

		private boolean correctDate() {

			correct = true;
			errorD.setVisible(false);
			final Calendar c2 = Calendar.getInstance();

			c2.setTime(((SpinnerDateModel) hourMin.getModel()).getDate());
			c.setTime(jdFecha.getDate());
			c.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));

			if (c.before(Calendar.getInstance())) {
				correct = false;
				errorD.setVisible(true);
			}
			return correct;

		}

	}

	private class CancelMouseAdapter extends MouseAdapter {

		private final DateDialog dlg;
		private boolean selection;

		public CancelMouseAdapter(DateDialog dlg, boolean selection) {
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
