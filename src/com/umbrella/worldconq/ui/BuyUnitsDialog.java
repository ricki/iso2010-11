package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.umbrella.worldconq.domain.UnitInfo;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class BuyUnitsDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 7085927869047108380L;
	private JPanel mainPanel;
	private JButton cancelButton;
	private JLabel infotextLabel;
	private JLabel cannonsLabel;
	private JLabel alertsetinfoLabel;
	private JLabel alertinfoLabel;
	private JSpinner antiMissilesCombo;
	private JSpinner icbmsCombo;
	private JSpinner cannonsCombo;
	private JSpinner missilesCombo;
	private JSpinner soldiersCombo;
	private JLabel antiMissilesLabel;
	private JLabel saldoLabel;
	private JLabel totalPriceLabel;
	private JLabel maxAntiLabel;
	private JLabel maxIcbmsLabel;
	private JLabel maxMissilesLabel;
	private JLabel maxCannonsLabel;
	private JLabel maxSoldiersLabel;
	private JLabel infoListLabel;
	private JPanel infoPanel;
	private JLabel icbmsLabel;
	private JLabel missilesLabel;
	private JLabel soldiersLabel;
	private JButton acceptButton;
	private JLabel backgroundLabel;
	private boolean selection;
	private int soldiers, cannons, missiles, icbms, antimissiles;
	private final int money;

	public BuyUnitsDialog(JFrame frame, String territory, int maxMoney) {
		super(frame);
		money = maxMoney;
		this.initGUI();
	}

	private void initGUI() {
		this.createCombos();
		try {
			{
				this.setResizable(false);
				this.setTitle("La conquista del Mundo - Comprar unidades");
				try {
					this.setIconImage(new ImageIcon(
						this.getClass().getClassLoader().getResource(
						"image/logo.png")).getImage());
				} catch (final Exception e) {
					System.out.println("Imagen no encontrada");
				}
				{
					mainPanel = new JPanel();
					this.getContentPane().add(mainPanel, BorderLayout.CENTER);
					mainPanel.setLayout(null);
					{
						mainPanel.add(totalPriceLabel);
						totalPriceLabel.setBounds(12, 183, 468, 31);
						totalPriceLabel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					}
					{
						mainPanel.add(maxSoldiersLabel);
						maxSoldiersLabel.setBounds(209, 41, 150, 20);
					}
					{
						mainPanel.add(maxCannonsLabel);
						maxCannonsLabel.setBounds(209, 67, 150, 20);
					}
					{
						mainPanel.add(maxMissilesLabel);
						maxMissilesLabel.setBounds(209, 91, 150, 20);
					}
					{
						mainPanel.add(maxIcbmsLabel);
						maxIcbmsLabel.setBounds(209, 114, 150, 20);
					}
					{
						mainPanel.add(maxAntiLabel);
						maxAntiLabel.setBounds(209, 140, 150, 20);
					}
					{
						cancelButton = new JButton();
						mainPanel.add(cancelButton);
						cancelButton.setText("Cancelar");
						cancelButton.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/cancel.png")));
						cancelButton.setBounds(330, 326, 150, 35);
						cancelButton.setToolTipText("No realizar la compra de ninguna unidad");
						cancelButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, false));
					}
					{
						acceptButton = new JButton();
						mainPanel.add(acceptButton);
						acceptButton.setText("Aceptar");
						acceptButton.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/ok.png")));
						acceptButton.setBounds(330, 280, 150, 35);
						acceptButton.setToolTipText("Comprar las unidades seleccionadas");
						acceptButton.addKeyListener(new AcceptDialogKeyAdapter(
							this));
						acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, true));
					}
					{
						infotextLabel = new JLabel();
						mainPanel.add(infotextLabel);
						infotextLabel.setText("Indique las unidades a comprar: ");
						infotextLabel.setBounds(12, 12, 325, 20);
					}
					{
						soldiersLabel = new JLabel();
						mainPanel.add(soldiersLabel);
						soldiersLabel.setText("Soldados: ");
						soldiersLabel.setBounds(20, 40, 150, 20);
					}
					{
						cannonsLabel = new JLabel();
						cannonsLabel.setLayout(null);
						mainPanel.add(cannonsLabel);
						cannonsLabel.setText("Cañones: ");
						cannonsLabel.setBounds(20, 65, 150, 20);
					}
					{
						missilesLabel = new JLabel();
						mainPanel.add(missilesLabel);
						missilesLabel.setText("Misiles: ");
						missilesLabel.setBounds(20, 90, 150, 20);
					}
					{
						icbmsLabel = new JLabel();
						mainPanel.add(icbmsLabel);
						icbmsLabel.setText("ICBMs: ");
						icbmsLabel.setBounds(20, 115, 150, 20);
					}
					{
						antiMissilesLabel = new JLabel();
						mainPanel.add(antiMissilesLabel);
						antiMissilesLabel.setText("Anti-Misiles: ");
						antiMissilesLabel.setBounds(20, 140, 150, 20);
					}
					{
						mainPanel.add(soldiersCombo);
						soldiersCombo.setBounds(150, 40, 50, 20);
					}
					{
						mainPanel.add(cannonsCombo);
						cannonsCombo.setBounds(150, 65, 50, 20);
					}
					{
						mainPanel.add(missilesCombo);
						missilesCombo.setBounds(150, 90, 50, 20);
					}
					{

						mainPanel.add(icbmsCombo);
						icbmsCombo.setBounds(150, 115, 50, 20);
					}
					{
						mainPanel.add(antiMissilesCombo);
						antiMissilesCombo.setBounds(150, 140, 50, 20);
					}
					{
						alertinfoLabel = new JLabel();
						mainPanel.add(alertinfoLabel);
						alertinfoLabel.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/half.alerta.png")));
						alertinfoLabel.setBounds(20, 196, 75, 64);
						alertinfoLabel.setVisible(false);
					}
					{
						alertsetinfoLabel = new JLabel();
						mainPanel.add(alertsetinfoLabel);
						alertsetinfoLabel.setBounds(80, 180, 300, 25);
					}
					{
						backgroundLabel = new JLabel();
						mainPanel.add(backgroundLabel);
						backgroundLabel.setLayout(null);
						backgroundLabel.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/mapa.png")));
						backgroundLabel.setBounds(5, 5, 357, 215);
					}
					{
						infoPanel = new JPanel();
						final BorderLayout infoPanelLayout = new BorderLayout();
						infoPanel.setLayout(infoPanelLayout);
						mainPanel.add(infoPanel);
						infoPanel.setBounds(12, 232, 309, 129);
						{
							infoPanel.add(infoListLabel, BorderLayout.CENTER);
						}
					}
					{
						mainPanel.add(saldoLabel);
						saldoLabel.setBounds(365, 14, 115, 120);
					}
				}
			}
			this.setSize(500, 400);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	//Método que devuleve la selección tomada
	public boolean getSelection() {
		return selection;
	}

	//Método que devuelve el número de soldados comprados
	public int getSoldierCount() {
		return soldiers;
	}

	//Método que devuelve el número de cañones comprados
	public int getCannonCount() {
		return cannons;
	}

	//Método que devuelve el número de icbms comprados
	public int getICBMCount() {
		return icbms;
	}

	//Método que devuelve el número de misiles comprados
	public int getMissileCount() {
		return missiles;
	}

	//Método que devuelve el número de anti-misiles comprados
	public int getAntiMissileCount() {
		return antimissiles;
	}

	//Método que genera los distintos Combos
	private void createCombos() {
		soldiersCombo = new JSpinner();
		missilesCombo = new JSpinner();
		cannonsCombo = new JSpinner();
		icbmsCombo = new JSpinner();
		antiMissilesCombo = new JSpinner();
		infoListLabel = new JLabel();
		maxSoldiersLabel = new JLabel();
		maxCannonsLabel = new JLabel();
		maxMissilesLabel = new JLabel();
		maxIcbmsLabel = new JLabel();
		maxAntiLabel = new JLabel();
		SpinnerModel values;
		int units, total;
		total = 0;

		//Genero el Label totalPrice inicial		
		totalPriceLabel = new JLabel();
		totalPriceLabel.setText("   Usted ha comprado unidades por valor de "
				+ total + " gallifantes.");

		//Genero el label del saldo actual
		final String actualMoney = "<html>\n<P ALIGN=\"left\">Saldo<BR>disponible:<BR>"
				+ money + "gallifantes.<BR></P>";
		saldoLabel = new JLabel();
		saldoLabel.setText(actualMoney);

		//Genero la lista de precios
		final String pricesList = "<html>\n<P ALIGN=\"left\"> Lista de precios:<BR>"
				+ "<TABLE BORDER=0><TR><TD Align=\"left\">Soldados</TD><TD Align=\"left\">--></TD><TD Align=\"left\">"
				+ UnitInfo.getSoldierCost()
				+ "</TD> gallifantes.</TD></TR><TR><TD Align=\"left\">Cañones</TD><TD Align=\"left\">--></TD><TD Align=\"left\">"
				+ UnitInfo.getCannonCost()
				+ "</TD> gallifantes.</TD></TR><TR><TD Align=\"left\">Misiles</TD><TD Align=\"left\">--></TD><TD Align=\"left\">"
				+ UnitInfo.getMissileCost()
				+ "</TD> gallifantes.</TD></TR><TR><TD Align=\"left\">ICBMs</TD><TD Align=\"left\">--></TD><TD Align=\"left\">"
				+ UnitInfo.getICBMCost()
				+ "</TD> gallifantes.</TD></TR><TR><TD Align=\"left\">Anti-Misiles</TD><TD Align=\"left\">--></TD><TD Align=\"left\">"
				+ UnitInfo.getAntiMissileCost()
				+ "</TD> gallifantes.</TD></TR></TABLE>";
		infoListLabel.setText(pricesList);

		//Creo el combo de soldados			

		units = (money / UnitInfo.getSoldierCost());
		values = new SpinnerNumberModel(0, 0,
			units, 1);
		soldiersCombo.setModel(values);
		final ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("Source: " + e.getSource());
				final String priceText = "   Usted ha comprado unidades por valor de "
						+ BuyUnitsDialog.this.totalPrice()
						+ " gallifantes.";
				//Genero el label del saldo actual
				final String actualMoney = "<html>\n<P ALIGN=\"left\">Saldo<BR>disponible:<BR>"
						+ (money - BuyUnitsDialog.this.totalPrice())
						+ "gallifantes.<BR></P>";
				saldoLabel = new JLabel();
				saldoLabel.setText(actualMoney);
				if (BuyUnitsDialog.this.correctArsenal()) {
					totalPriceLabel.setText(priceText);
				}
				else {
					totalPriceLabel.setText(priceText);
					totalPriceLabel.setForeground(Color.RED);
				}
			}
		};

		soldiersCombo.addChangeListener(listener);

		//Etiqueta maxSoldiers		
		maxSoldiersLabel = new JLabel();
		maxSoldiersLabel.setText("(" + units + " máximo)");

		//Creo el combo de cañones		

		units = (money / UnitInfo.getCannonCost());
		values = new SpinnerNumberModel(0, 0,
			units, 1);
		cannonsCombo.setModel(values);
		cannonsCombo.addChangeListener(listener);

		//Etiqueta maxCannons		
		maxCannonsLabel = new JLabel();
		maxCannonsLabel.setText("(" + units + " máximo)");

		//Creo el combo de misiles		

		units = (money / UnitInfo.getMissileCost());
		values = new SpinnerNumberModel(0, 0,
			units, 1);
		missilesCombo.setModel(values);
		missilesCombo.addChangeListener(listener);

		//Etiqueta maxMissiles		
		maxMissilesLabel = new JLabel();
		maxMissilesLabel.setText("(" + units + " máximo)");

		//Creo el combo de icbms		

		units = (money / UnitInfo.getICBMCost());
		values = new SpinnerNumberModel(0, 0,
			units, 1);
		icbmsCombo.setModel(values);
		icbmsCombo.addChangeListener(listener);

		//Etiqueta maxIcbms		
		maxIcbmsLabel = new JLabel();
		maxIcbmsLabel.setText("(" + units + " máximo)");

		//Creo el combo de antimisiles		

		units = (money / UnitInfo.getAntiMissileCost());
		values = new SpinnerNumberModel(0, 0,
			units, 1);
		antiMissilesCombo.setModel(values);
		antiMissilesCombo.addChangeListener(listener);

		//Etiqueta maxAnti		
		maxAntiLabel = new JLabel();
		maxAntiLabel.setText("(" + units + " máximo)");
	}

	//Método que calcula el precio toal de la compra
	public int totalPrice() {
		final int arsenalCount = ((Integer) soldiersCombo.getValue())
				* UnitInfo.getSoldierCost()
				+ ((Integer) cannonsCombo.getValue())
				* UnitInfo.getCannonCost()
				+ ((Integer) missilesCombo.getValue())
				* UnitInfo.getMissileCost()
				+ ((Integer) icbmsCombo.getValue()) * UnitInfo.getICBMCost()
				+ ((Integer) antiMissilesCombo.getValue())
				* UnitInfo.getAntiMissileCost();
		return arsenalCount;
	}

	//Método que comprueba que los valores del arsenal a comprar
	public boolean correctArsenal() {
		final int arsenalCount = ((Integer) soldiersCombo.getValue())
				* UnitInfo.getSoldierCost()
				+ ((Integer) cannonsCombo.getValue())
				* UnitInfo.getCannonCost()
				+ ((Integer) missilesCombo.getValue())
				* UnitInfo.getMissileCost()
				+ ((Integer) icbmsCombo.getValue()) * UnitInfo.getICBMCost()
				+ ((Integer) antiMissilesCombo.getValue())
				* UnitInfo.getAntiMissileCost();
		if (arsenalCount > money) {
			return false;
		}
		return true;
	}

	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private final BuyUnitsDialog dlg;
		private final boolean doselection;

		public AcceptDialogMouseAdapter(BuyUnitsDialog dlg, boolean selectioni) {
			this.dlg = dlg;
			doselection = selectioni;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (doselection == true) {
				if (dlg.correctArsenal()) {
					dlg.selection = doselection;
					dlg.soldiers = (Integer) dlg.soldiersCombo.getValue();
					dlg.cannons = (Integer) dlg.cannonsCombo.getValue();
					dlg.missiles = (Integer) dlg.missilesCombo.getValue();
					dlg.icbms = (Integer) dlg.icbmsCombo.getValue();
					dlg.antimissiles = (Integer) dlg.antiMissilesCombo.getValue();
					dlg.setVisible(false);
				} else {
					dlg.alertinfoLabel.setVisible(true);
					dlg.acceptButton.setEnabled(false);
					dlg.alertsetinfoLabel.setText("No dispone del suficiente dinero");
				}
			} else {
				dlg.selection = doselection;
				dlg.setVisible(false);
			}
		}
	}

	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private final BuyUnitsDialog dlg;

		public AcceptDialogKeyAdapter(BuyUnitsDialog dlg) {
			this.dlg = dlg;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				if (dlg.correctArsenal()) {
					dlg.selection = true;
					dlg.soldiers = (Integer) dlg.soldiersCombo.getValue();
					dlg.cannons = (Integer) dlg.cannonsCombo.getValue();
					dlg.missiles = (Integer) dlg.missilesCombo.getValue();
					dlg.icbms = (Integer) dlg.icbmsCombo.getValue();
					dlg.antimissiles = (Integer) dlg.antiMissilesCombo.getValue();
					dlg.setVisible(false);
				} else {
					dlg.alertinfoLabel.setVisible(true);
					dlg.acceptButton.setEnabled(false);
					dlg.alertsetinfoLabel.setText("No dispone del suficiente dinero");
				}
			} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}

	//Clase privada para manejar los eventos del Spinner de soldados
	private class soldiersChangeListener implements ChangeListener {
		BuyUnitsDialog bud;

		public soldiersChangeListener(BuyUnitsDialog bud, ChangeEvent e) {
			this.bud = bud;
		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			final int total = ((Integer) bud.soldiersCombo.getValue());

		}

	}

}
