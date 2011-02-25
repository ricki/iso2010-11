package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JComboBox antiMissilesCombo;
	private JComboBox icbmsCombo;
	private JLabel antiMissilesLabel;
	private JComboBox cannonsCombo;
	private JComboBox missilesCombo;
	private JComboBox soldiersCombo;
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
						cancelButton = new JButton();
						mainPanel.add(cancelButton);
						cancelButton.setText("Cancelar");
						cancelButton.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/cancel.png")));
						cancelButton.setBounds(210, 220, 150, 35);
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
						acceptButton.setBounds(50, 220, 150, 35);
						acceptButton.setToolTipText("Comprar las unidades seleccionadas");
						acceptButton.addKeyListener(new AcceptDialogKeyAdapter(
							this));
						acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, true));
					}
					{
						infotextLabel = new JLabel();
						mainPanel.add(infotextLabel);
						infotextLabel.setText("Indique las unidades que desea comprar: ");
						infotextLabel.setBounds(12, 12, 370, 16);
					}
					{
						soldiersLabel = new JLabel();
						mainPanel.add(soldiersLabel);
						soldiersLabel.setText("Soldados: ");
						soldiersLabel.setBounds(70, 40, 150, 20);
					}
					{
						cannonsLabel = new JLabel();
						cannonsLabel.setLayout(null);
						mainPanel.add(cannonsLabel);
						cannonsLabel.setText("Cañones: ");
						cannonsLabel.setBounds(70, 65, 150, 20);
					}
					{
						missilesLabel = new JLabel();
						mainPanel.add(missilesLabel);
						missilesLabel.setText("Misiles: ");
						missilesLabel.setBounds(70, 90, 150, 20);
					}
					{
						icbmsLabel = new JLabel();
						mainPanel.add(icbmsLabel);
						icbmsLabel.setText("ICBMs: ");
						icbmsLabel.setBounds(70, 115, 150, 20);
					}
					{
						antiMissilesLabel = new JLabel();
						mainPanel.add(antiMissilesLabel);
						antiMissilesLabel.setText("Anti-Misiles: ");
						antiMissilesLabel.setBounds(70, 140, 150, 20);
					}
					{
						mainPanel.add(soldiersCombo);
						soldiersCombo.setBounds(250, 40, 50, 20);
					}
					{
						mainPanel.add(cannonsCombo);
						cannonsCombo.setBounds(250, 65, 50, 20);
					}
					{
						mainPanel.add(missilesCombo);
						missilesCombo.setBounds(250, 90, 50, 20);
					}
					{

						mainPanel.add(icbmsCombo);
						icbmsCombo.setBounds(250, 115, 50, 20);
					}
					{
						mainPanel.add(antiMissilesCombo);
						antiMissilesCombo.setBounds(250, 140, 50, 20);
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
						backgroundLabel.setBounds(20, 20, 357, 215);
					}
				}
			}
			this.setSize(400, 300);
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
		soldiersCombo = new JComboBox();
		missilesCombo = new JComboBox();
		cannonsCombo = new JComboBox();
		icbmsCombo = new JComboBox();
		antiMissilesCombo = new JComboBox();

		//Creo el combo de soldados
		for (int i = 0; i <= (money / UnitInfo.getPriceSoldier()); i++) {
			soldiersCombo.addItem(i);
		}

		//Creo el combo de cañones		
		for (int i = 0; i <= (money / UnitInfo.getPriceCannon()); i++) {
			cannonsCombo.addItem(i);
		}

		//Creo el combo de misiles		
		for (int i = 0; i <= (money / UnitInfo.getPriceMissil()); i++) {
			missilesCombo.addItem(i);
		}

		//Creo el combo de icbms		
		for (int i = 0; i <= (money / UnitInfo.getPriceICBM()); i++) {
			icbmsCombo.addItem(i);
		}

		//Creo el combo de antimisiles		
		for (int i = 0; i <= (money / UnitInfo.getPriceAntiMissile()); i++) {
			antiMissilesCombo.addItem(i);
		}
	}

	//Método que comprueba que los valores del arsenal a comprar
	public boolean correctArsenal() {
		final int arsenalCount = soldiersCombo.getSelectedIndex()
				+ cannonsCombo.getSelectedIndex()
				+ missilesCombo.getSelectedIndex()
				+ icbmsCombo.getSelectedIndex()
				+ antiMissilesCombo.getSelectedIndex();
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
					dlg.soldiers = dlg.soldiersCombo.getSelectedIndex();
					dlg.cannons = dlg.cannonsCombo.getSelectedIndex();
					dlg.missiles = dlg.missilesCombo.getSelectedIndex();
					dlg.icbms = dlg.icbmsCombo.getSelectedIndex();
					dlg.antimissiles = dlg.antiMissilesCombo.getSelectedIndex();
					dlg.setVisible(false);
				} else {
					dlg.alertinfoLabel.setVisible(true);
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
					dlg.soldiers = dlg.soldiersCombo.getSelectedIndex();
					dlg.cannons = dlg.cannonsCombo.getSelectedIndex();
					dlg.missiles = dlg.missilesCombo.getSelectedIndex();
					dlg.icbms = dlg.icbmsCombo.getSelectedIndex();
					dlg.antimissiles = dlg.antiMissilesCombo.getSelectedIndex();
					dlg.setVisible(false);
				} else {
					dlg.alertinfoLabel.setVisible(true);
					dlg.alertsetinfoLabel.setText("No dispone del suficiente dinero");
				}
			} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}

}
