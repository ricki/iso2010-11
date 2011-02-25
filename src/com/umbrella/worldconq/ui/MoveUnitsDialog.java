package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.umbrella.worldconq.domain.TerritoryDecorator;

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
public class MoveUnitsDialog extends javax.swing.JDialog {
	private JPanel mainPanel;
	private JButton cancelButton;
	private JLabel infotextLabel;
	private JLabel cannonsLabel;
	private JLabel alertsetinfoLabel;
	private JLabel alertinfoLabel;
	private JComboBox antiMissilesCombo;
	private JComboBox icbmsCombo;
	private JLabel antiMissilesLabel;
	private JComboBox cannonTthreeCombo;
	private JLabel typethreeLabel;
	private JLabel typeoneLabel;
	private JComboBox cannonTtwoCombo;
	private JLabel cannonTtwoLabel;
	private JPanel centralPanel;
	private JPanel territoriesPanel;
	private JLabel territoryselectinfoLabel;
	private JPanel infoPanel;
	private JPanel actionPanel;
	private JComboBox territoriesCombo;
	private JComboBox cannonsCombo;
	private JComboBox missilesCombo;
	private JComboBox soldiersCombo;
	private JLabel icbmsLabel;
	private JLabel missilesLabel;
	private JLabel soldiersLabel;
	private JButton acceptButton;
	private JLabel backgroundLabel;
	private boolean selection;
	private int destiny, soldiers, missiles, icbms, antimissiles;
	private final int[] cannons = {
			0, 0, 0
	};
	private final TerritoryDecorator srcterritory;
	private final ArrayList<String> adjacentListNames;

	public MoveUnitsDialog(JFrame frame, TerritoryDecorator src, ArrayList<String> adjacentList) {
		super(frame);
		srcterritory = src;
		adjacentListNames = adjacentList;
		this.initGUI();
	}

	private void initGUI() {
		this.createCombos();
		try {
			{
				this.setResizable(false);
				this.setTitle("La conquista del mundo - Mover unidades");
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
					mainPanel.setToolTipText("La conquista del mundo - Mover unidades");
					mainPanel.setPreferredSize(new java.awt.Dimension(382, 360));
				}
				{
					centralPanel = new JPanel();
					mainPanel.add(centralPanel);
					centralPanel.setBounds(12, 76, 370, 156);
					centralPanel.setLayout(null);
					centralPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					centralPanel.setOpaque(false);
				}
				{
					infotextLabel = new JLabel();
					centralPanel.add(infotextLabel);
					infotextLabel.setText("Indique las unidades que desea mover: ");
					infotextLabel.setBounds(12, 5, 360, 20);
				}
				{
					soldiersLabel = new JLabel();
					centralPanel.add(soldiersLabel);
					soldiersLabel.setText("Soldados: ");
					soldiersLabel.setBounds(25, 30, 100, 20);
				}
				{
					cannonsLabel = new JLabel();
					cannonsLabel.setLayout(null);
					centralPanel.add(cannonsLabel);
					cannonsLabel.setText("Cañones: ");
					cannonsLabel.setBounds(25, 55, 150, 20);
				}
				{
					typeoneLabel = new JLabel();
					centralPanel.add(typeoneLabel);
					typeoneLabel.setText("Tipo1: ");
					typeoneLabel.setBounds(50, 80, 100, 20);
				}
				{
					cannonTtwoLabel = new JLabel();
					centralPanel.add(cannonTtwoLabel);
					cannonTtwoLabel.setText("Tipo 2: ");
					cannonTtwoLabel.setBounds(50, 105, 39, 16);
				}
				{
					missilesLabel = new JLabel();
					centralPanel.add(missilesLabel);
					missilesLabel.setText("Misiles: ");
					missilesLabel.setBounds(200, 30, 100, 20);
				}
				{
					icbmsLabel = new JLabel();
					centralPanel.add(icbmsLabel);
					icbmsLabel.setText("ICBMs: ");
					icbmsLabel.setBounds(200, 55, 100, 20);
				}
				{
					antiMissilesLabel = new JLabel();
					centralPanel.add(antiMissilesLabel);
					antiMissilesLabel.setText("Anti-Misiles: ");
					antiMissilesLabel.setBounds(200, 80, 150, 20);
				}
				{
					centralPanel.add(soldiersCombo);
					soldiersCombo.setBounds(125, 30, 50, 20);
				}
				{
					centralPanel.add(cannonsCombo);
					cannonsCombo.setBounds(125, 80, 50, 20);
				}
				{
					centralPanel.add(missilesCombo);
					missilesCombo.setBounds(300, 30, 50, 20);
				}
				{
					centralPanel.add(icbmsCombo);
					icbmsCombo.setBounds(300, 55, 50, 20);
				}
				{
					centralPanel.add(antiMissilesCombo);
					centralPanel.add(cannonTtwoCombo);
					cannonTtwoCombo.setBounds(125, 105, 50, 20);
					{
						typethreeLabel = new JLabel();
						centralPanel.add(typethreeLabel);
						typethreeLabel.setText("Tipo 3: ");
						typethreeLabel.setBounds(50, 130, 100, 20);
					}
					centralPanel.add(cannonTthreeCombo);
					cannonTthreeCombo.setBounds(125, 130, 50, 20);
					antiMissilesCombo.setBounds(300, 80, 50, 20);
				}
				{
					infoPanel = new JPanel();
					mainPanel.add(infoPanel);
					infoPanel.setBounds(12, 236, 370, 70);
					infoPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					infoPanel.setLayout(null);

					{
						alertinfoLabel = new JLabel();
						infoPanel.add(alertinfoLabel);
						alertinfoLabel.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
							"image/half.alerta.png")));
						alertinfoLabel.setBounds(2, 2, 75, 64);
						alertinfoLabel.setVisible(false);
					}
					{
						alertsetinfoLabel = new JLabel();
						infoPanel.add(alertsetinfoLabel);
						alertsetinfoLabel.setBounds(80, 22, 270, 25);
					}
				}
				{
					actionPanel = new JPanel();
					{
						acceptButton = new JButton();
						actionPanel.add(acceptButton);
						acceptButton.setText("Aceptar");
						acceptButton.setBounds(15, 10, 150, 30);
						acceptButton.setToolTipText("Mover las unidades seleccionadas");
						acceptButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
						acceptButton.addKeyListener(new AcceptDialogKeyAdapter(
							this));
						acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, true));
						{
							cancelButton = new JButton();
							actionPanel.add(cancelButton);
							cancelButton.setText("Cancelar");
							cancelButton.setBounds(205, 10, 150, 30);
							cancelButton.setToolTipText("No mover ninguna unidad");
							cancelButton.addMouseListener(new AcceptDialogMouseAdapter(
								this, false));
						}
					}
					mainPanel.add(actionPanel);
					actionPanel.setBounds(12, 310, 370, 50);
					actionPanel.setLayout(null);
					actionPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					actionPanel.setLayout(null);
				}
				{
					territoriesPanel = new JPanel();
					final FlowLayout territoriesPanelLayout = new FlowLayout();
					mainPanel.add(territoriesPanel);
					territoriesPanel.setBounds(12, 12, 370, 60);
					territoriesPanel.setLayout(territoriesPanelLayout);
					territoriesPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					territoriesPanel.setOpaque(false);
					{
						territoryselectinfoLabel = new JLabel();
						territoriesPanel.add(territoryselectinfoLabel);
						territoryselectinfoLabel.setText("Seleccione el territorio al que mover unidades: ");
						territoryselectinfoLabel.setBounds(12, 12, 370, 20);
						territoryselectinfoLabel.setPreferredSize(new java.awt.Dimension(
							350, 20));
					}
					{
						territoriesPanel.add(territoriesCombo);
						territoriesCombo.setPreferredSize(new java.awt.Dimension(
							300, 20));
					}
				}
				{
					backgroundLabel = new JLabel();
					mainPanel.add(backgroundLabel);
					backgroundLabel.setLayout(null);
					backgroundLabel.setIcon(new ImageIcon(
						this.getClass().getClassLoader().getResource(
						"image/mapa.png")));
					backgroundLabel.setBounds(20, 77, 357, 153);
				}
			}
			this.setSize(400, 400);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	//Método que devuleve la selección tomada
	public boolean getSelection() {
		return selection;
	}

	//Método que devuelve el índice del territorio destino
	public int getDestiny() {
		return destiny;
	}

	//Método que devuelve el número de soldados comprados
	public int getSoldierCount() {
		return soldiers;
	}

	//Método que devuelve el número de cañones comprados
	public int[] getCannonCount() {
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
		territoriesCombo = new JComboBox();
		soldiersCombo = new JComboBox();
		missilesCombo = new JComboBox();
		cannonsCombo = new JComboBox();
		icbmsCombo = new JComboBox();
		antiMissilesCombo = new JComboBox();
		cannonTtwoCombo = new JComboBox();
		cannonTthreeCombo = new JComboBox();

		//Genero el contenido del ComboBox de territorios a los que mover unidades		
		final int numadj = adjacentListNames.size();
		if (numadj == 0) {
			territoriesCombo.addItem("No hay territorios donde mover");
		} else {
			for (int i = 0; i < adjacentListNames.size(); i++) {
				territoriesCombo.addItem(adjacentListNames.get(i));
			}
		}

		//Creo el combo de soldados		
		for (int i = 0; i <= srcterritory.getNumSoldiers(); i++) {
			soldiersCombo.addItem(i);
		}

		//Creo el combo de cañones		
		int numcannons = srcterritory.getNumCannons()[0];
		for (int i = 0; i <= numcannons; i++) {
			cannonsCombo.addItem(i);
		}
		numcannons = srcterritory.getNumCannons()[1];
		for (int i = 0; i <= numcannons; i++) {
			cannonTtwoCombo.addItem(i);
		}
		numcannons = srcterritory.getNumCannons()[2];
		for (int i = 0; i <= numcannons; i++) {
			cannonTthreeCombo.addItem(i);
		}

		//Creo el combo de misiles		
		for (int i = 0; i <= srcterritory.getNumMissiles(); i++) {
			missilesCombo.addItem(i);
		}

		//Creo el combo de icbms		
		for (int i = 0; i <= srcterritory.getNumICBMs(); i++) {
			icbmsCombo.addItem(i);
		}

		//Creo el combo de antimisiles		
		for (int i = 0; i <= srcterritory.getNumAntiMissiles(); i++) {
			antiMissilesCombo.addItem(i);
		}
	}

	//Método que comprueba que los valores del arsenal a comprar
	public boolean correctArsenal() {
		return true;
	}

	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private final MoveUnitsDialog dlg;
		private final boolean doselection;

		public AcceptDialogMouseAdapter(MoveUnitsDialog dlg, boolean selectioni) {
			this.dlg = dlg;
			doselection = selectioni;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (doselection == true) {
				if (dlg.correctArsenal()) {
					if (dlg.adjacentListNames.size() == 0) {
						dlg.alertinfoLabel.setVisible(true);
						dlg.alertsetinfoLabel.setText("No puede mover tropas a ningún territorio");
					} else {
						dlg.selection = doselection;
						dlg.destiny = dlg.territoriesCombo.getSelectedIndex();
						dlg.soldiers = dlg.soldiersCombo.getSelectedIndex();
						dlg.cannons[0] = dlg.cannonsCombo.getSelectedIndex();
						dlg.cannons[1] = dlg.cannonTtwoCombo.getSelectedIndex();
						dlg.cannons[2] = dlg.cannonTthreeCombo.getSelectedIndex();
						dlg.missiles = dlg.missilesCombo.getSelectedIndex();
						dlg.icbms = dlg.icbmsCombo.getSelectedIndex();
						dlg.antimissiles = dlg.antiMissilesCombo.getSelectedIndex();
						dlg.setVisible(false);
					}
				} else {
					dlg.alertinfoLabel.setVisible(true);
					dlg.alertsetinfoLabel.setText("Mueve más unidades de las que tiene");
				}
			} else {
				dlg.selection = doselection;
				dlg.setVisible(false);
			}
		}
	}

	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private final MoveUnitsDialog dlg;

		public AcceptDialogKeyAdapter(MoveUnitsDialog dlg) {
			this.dlg = dlg;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				if (dlg.correctArsenal()) {
					dlg.selection = true;
					dlg.destiny = dlg.territoriesCombo.getSelectedIndex();
					dlg.soldiers = dlg.soldiersCombo.getSelectedIndex();
					dlg.cannons[0] = dlg.cannonsCombo.getSelectedIndex();
					dlg.cannons[1] = dlg.cannonTtwoCombo.getSelectedIndex();
					dlg.cannons[2] = dlg.cannonTthreeCombo.getSelectedIndex();
					dlg.missiles = dlg.missilesCombo.getSelectedIndex();
					dlg.icbms = dlg.icbmsCombo.getSelectedIndex();
					dlg.antimissiles = dlg.antiMissilesCombo.getSelectedIndex();
					dlg.setVisible(false);
				} else {
					dlg.alertinfoLabel.setVisible(true);
					dlg.alertsetinfoLabel.setText("Mueve más unidades de las que tiene");
				}
			} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}

}
