package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.border.BevelBorder;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
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
public class LaunchAttackDialog extends javax.swing.JDialog {

	private boolean selection; //almacena la selección
	private int territoryIndex; //almacena el índice del territorio a atacar
	private int soldierCount; //almacena el número de soldados con los que se ataca
	private JPanel infoPanel;
	private JLabel soldiersIconLabel;
	private JPanel mainPanel; //Panel principal

	private JSpinner cannonTtwoSpinner; //Spinner de selección del número de cañones del segundo tipo
	private JSpinner cannonToneSpinner; //Spinner de selección del número de cañones del primer tipo
	private JSpinner cannonTthreeSpinner; //Spiner de selección del número de cañones del tercer tipo
	private JSpinner icbmCountSpinner;
	private JSpinner missileCountSpinner;
	private JSpinner soldierCountSpinner;
	private int cannonCount; //almacena el número de cañones con los que se ataca
	private int missileCount; //almacena el número de misiles con los que se ataca
	private int icbmCount; //almacena el número de icbms con los que se ataca
	private JButton acceptButton; //Botón de Aceptar
	private JButton cancelButton; //Bottón de Cancelar
	private JLabel icbmCountLabel; //Etiqueta para mostrar el texto icbm
	private JLabel missileCountLabel; //Etiqueta para mostrar el texto missile
	private JComboBox territoryCombo;
	private JLabel cannonCountLabel; //Etiqueta para mostrar el texto cannon
	private JLabel soldierCountLabel; //Etiqueta para mostrar el texto soldier

	private JLabel territoryLabel; //Etiqueta para mostrar el texto 
	private JLabel infoLabel; //Etiqueta que muestra información de relevancia
	private final ArrayList<String> adjacentList;
	private final TerritoryDecorator src; //Almacena la información del territorio del origen del ataque
	private genericSpinnerListModel soldierSpinnerModel;
	private genericSpinnerListModel cannonToneSpinnerModel;
	private genericSpinnerListModel cannonTtwoSpinnerModel;
	private genericSpinnerListModel cannonTthreeSpinnerModel;
	private genericSpinnerListModel missileSpinnerModel;
	private genericSpinnerListModel icbmSpinnerModel;
	private final JFrame mWin;

	private static final long serialVersionUID = 3384340224276468944L;

	public LaunchAttackDialog(JFrame frame, TerritoryDecorator src, ArrayList<String> adjacentList) {
		super(frame);
		mWin = frame;
		this.src = src;
		this.adjacentList = adjacentList;
		this.initGUI();
	}

	private void initGUI() {
		this.createSpinners();
		try {
			{
				this.setTitle("La conquista del mundo - Atacar");
			}
			try {
				this.setIconImage(new ImageIcon(
					this.getClass().getClassLoader().getResource(
					"image/logo.png")).getImage());
			} catch (final Exception e) {
				System.out.println("Imagen no encontrada");
			}
			this.setSize(400, 400);
			this.setResizable(false);
			{
				mainPanel = new JPanel();
				final AnchorLayout mainPanelLayout = new AnchorLayout();
				this.getContentPane().add(mainPanel, BorderLayout.CENTER);
				mainPanel.setLayout(mainPanelLayout);
				{
					mainPanel.setNextFocusableComponent(territoryCombo);
					{
						infoPanel = new JPanel();
						final AnchorLayout infoPanelLayout = new AnchorLayout();
						infoPanel.setLayout(infoPanelLayout);
						mainPanel.add(infoPanel, new AnchorConstraint(423, 965,
							676, 31, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						infoPanel.setPreferredSize(new java.awt.Dimension(368,
							94));
						infoPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						{
							infoLabel = new JLabel();
							infoPanel.add(infoLabel, new AnchorConstraint(122,
								976, 867, 25, AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL));
							infoLabel.setIcon(new ImageIcon(
								this.getClass().getClassLoader().getResource(
								"image/half.alerta.png")));
							infoLabel.setPreferredSize(new java.awt.Dimension(
								350, 70));
							infoLabel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
							infoLabel.setVisible(false);
						}
					}
					{
						soldiersIconLabel = new JLabel();
						mainPanel.add(soldiersIconLabel, new AnchorConstraint(
							700, 280, 969, 26, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						soldiersIconLabel.setIcon(new ImageIcon(
							this.getClass().getClassLoader().getResource(
								"image/soldados.png")));
						soldiersIconLabel.setPreferredSize(new java.awt.Dimension(
							100, 100));
					}
					mainPanel.add(territoryCombo, new AnchorConstraint(33, 965,
						100, 585, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL));
					territoryCombo.setPreferredSize(new java.awt.Dimension(150,
						25));
				}
				{
					cannonToneSpinner = new JSpinner();
					mainPanel.add(cannonToneSpinner, new AnchorConstraint(72,
						14, 297, 851, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_REL));
					cannonToneSpinner.setModel(cannonToneSpinnerModel);
					cannonToneSpinner.setPreferredSize(new java.awt.Dimension(
						45, 25));
					cannonToneSpinner.setMaximumSize(new java.awt.Dimension(45,
						20));
				}
				{
					icbmCountSpinner = new JSpinner();
					mainPanel.add(icbmCountSpinner, new AnchorConstraint(122,
						14, 487, 851, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_REL));
					icbmCountSpinner.setModel(icbmSpinnerModel);
					icbmCountSpinner.setPreferredSize(new java.awt.Dimension(
						45, 25));
				}
				{
					missileCountSpinner = new JSpinner();
					mainPanel.add(missileCountSpinner, new AnchorConstraint(97,
						14, 395, 851, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_REL));
					missileCountSpinner.setModel(missileSpinnerModel);
					missileCountSpinner.setPreferredSize(new java.awt.Dimension(
						45, 25));
				}
				{
					soldierCountSpinner = new JSpinner();
					mainPanel.add(soldierCountSpinner, new AnchorConstraint(47,
						14, 211, 851, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_REL));
					soldierCountSpinner.setModel(soldierSpinnerModel);
					soldierCountSpinner.setPreferredSize(new java.awt.Dimension(
						45, 25));
				}
				{
					acceptButton = new JButton();
					mainPanel.add(acceptButton, new AnchorConstraint(829, 149,
						22, 394, AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE));
					acceptButton.setText("Aceptar");
					acceptButton.setPreferredSize(new java.awt.Dimension(125,
						35));
					acceptButton.setToolTipText("Atacar");
					acceptButton.setIcon(new ImageIcon(
						this.getClass().getClassLoader().getResource(
							"image/ok.png")));
					acceptButton.addKeyListener(new AcceptDialogKeyAdapter(this));
					acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
						this, true));
				}
				{
					cancelButton = new JButton();
					mainPanel.add(cancelButton, new AnchorConstraint(829, 14,
						22, 699, AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_NONE));
					cancelButton.setText("Cancelar");
					cancelButton.setPreferredSize(new java.awt.Dimension(125,
						35));
					cancelButton.setToolTipText("Cancelar el ataque");
					cancelButton.setIcon(new ImageIcon(
						this.getClass().getClassLoader().getResource(
							"image/cancel.png")));
					cancelButton.addMouseListener(new AcceptDialogMouseAdapter(
						this, false));
				}
				{
					icbmCountLabel = new JLabel();
					mainPanel.add(icbmCountLabel, new AnchorConstraint(120,
						585, 498, 10, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS));
					icbmCountLabel.setText("Indique el número de ICBMs: ");
					icbmCountLabel.setPreferredSize(new java.awt.Dimension(220,
						25));
				}
				{
					missileCountLabel = new JLabel();
					mainPanel.add(missileCountLabel, new AnchorConstraint(95,
						585, 406, 10, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS));
					missileCountLabel.setText("Indique el número de misiles: ");
					missileCountLabel.setPreferredSize(new java.awt.Dimension(
						220, 25));
				}
				{
					cannonCountLabel = new JLabel();
					mainPanel.add(cannonCountLabel, new AnchorConstraint(70,
						585, 314, 10, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS));
					cannonCountLabel.setText("Indique el número de cañones: ");
					cannonCountLabel.setPreferredSize(new java.awt.Dimension(
						220, 25));
				}
				{
					soldierCountLabel = new JLabel();
					mainPanel.add(soldierCountLabel, new AnchorConstraint(45,
						585, 222, 10, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS));
					soldierCountLabel.setText("Indique el número de soldados: ");
					soldierCountLabel.setPreferredSize(new java.awt.Dimension(
						220, 25));
				}
				{
					territoryLabel = new JLabel();
					mainPanel.add(territoryLabel, new AnchorConstraint(10, 585,
						130, 10, AnchorConstraint.ANCHOR_ABS,
						AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_ABS));
					territoryLabel.setText("Seleccione el territorio a atacar: ");
					territoryLabel.setPreferredSize(new java.awt.Dimension(220,
						25));
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	//Método que devuelve la selección
	public boolean getSelection() {
		return selection;
	}

	//Método que devuelve el índice del territorio
	public int getTerritoryIndex() {
		return territoryIndex;
	}

	//Método que devuleve la cantidad de soldados
	public int getSoldierCount() {
		return soldierCount;
	}

	//Método que devuelve la cantidad de cañones
	public int getCannonCount() {
		return cannonCount;
	}

	//Método que devuelve la cantidad de misiles
	public int getMissileCount() {
		return missileCount;
	}

	//Método que devuelve la cantidad de icbms
	public int getICBMCount() {
		return icbmCount;
	}

	//Método que genera los distintos Spinners
	private void createSpinners() {
		//Creo el spinner de territorios
		final String[] adjacentListNames = new String[adjacentList.size()];

		//Genero el contenido del ComboBox de territorios a atacar		
		territoryCombo = new JComboBox();
		for (int i = 0; i < adjacentList.size(); i++) {
			adjacentListNames[i] = adjacentList.get(i);
			territoryCombo.addItem(adjacentListNames[i]);
		}

		//Creo el spinner de soldados
		final int numsoldiers = src.getNumSoldiers();
		final String[] soldiers = new String[numsoldiers + 1];
		soldierCountSpinner = new JSpinner();
		for (int i = 0; i <= numsoldiers; i++) {
			soldiers[i] = Integer.toString(i);
		}
		soldierSpinnerModel = new genericSpinnerListModel(soldiers);
		soldierCountSpinner.setModel(soldierSpinnerModel);

		int numcannonstone = src.getNumCannons()[0];
		numcannonstone = numcannonstone + src.getNumCannons()[1]
				+ src.getNumCannons()[2];
		final String[] cannonstone = new String[numcannonstone + 1];
		cannonToneSpinner = new JSpinner();
		for (int i = 0; i <= numcannonstone; i++) {
			cannonstone[i] = Integer.toString(i);
		}
		cannonToneSpinnerModel = new genericSpinnerListModel(cannonstone);
		cannonToneSpinner.setModel(cannonToneSpinnerModel);

		//Creo el spinner de cañones de tipo 2
		final int numcannonsttwo = src.getNumCannons()[1];
		final String[] cannonsttwo = new String[numcannonsttwo + 1];
		cannonTtwoSpinner = new JSpinner();
		for (int i = 0; i <= numcannonsttwo; i++) {
			cannonsttwo[i] = Integer.toString(i);
		}
		cannonTtwoSpinnerModel = new genericSpinnerListModel(cannonsttwo);
		cannonTtwoSpinner.setModel(cannonTtwoSpinnerModel);

		//Creo el spinner de cañones de tipo 3
		final int numcannonstthree = src.getNumCannons()[2];
		final String[] cannonstthree = new String[numcannonstthree + 1];
		cannonTthreeSpinner = new JSpinner();
		for (int i = 0; i <= numcannonstthree; i++) {
			cannonstthree[i] = Integer.toString(i);
		}
		cannonTthreeSpinnerModel = new genericSpinnerListModel(cannonstthree);
		cannonTthreeSpinner.setModel(cannonTthreeSpinnerModel);

		//Creo el spinner de misiles
		final int nummissile = src.getNumMissiles();
		final String[] missiles = new String[nummissile + 1];
		missileCountSpinner = new JSpinner();
		for (int i = 0; i <= nummissile; i++) {
			missiles[i] = Integer.toString(i);
		}
		missileSpinnerModel = new genericSpinnerListModel(missiles);
		missileCountSpinner.setModel(missileSpinnerModel);

		//Creo el spinner de icbms
		final int numicbm = src.getNumICBMs();
		final String[] icbms = new String[numicbm + 1];
		icbmCountSpinner = new JSpinner();
		for (int i = 0; i <= numicbm; i++) {
			icbms[i] = Integer.toString(i);
		}
		icbmSpinnerModel = new genericSpinnerListModel(icbms);
		icbmCountSpinner.setModel(icbmSpinnerModel);
	}

	//Método que comprueba que los valores del arsenal del territorio se corresponda con los valores del ataque
	public boolean correctArsenal() {
		if (src.getNumSoldiers() < Integer.parseInt((String) (soldierCountSpinner.getValue()))) {
			return false;
		}
		if ((src.getNumCannons()[0] + src.getNumCannons()[1] + src.getNumCannons()[2]) < Integer.parseInt((String) (cannonToneSpinner.getValue()))) {
			return false;
		}
		if (src.getNumMissiles() < Integer.parseInt((String) (missileCountSpinner.getValue()))) {
			return false;
		}
		if (src.getNumICBMs() < Integer.parseInt((String) (icbmCountSpinner.getValue()))) {
			return false;
		}
		return true;
	}

	//Esta es una clase privada, usada para crear los spinnerList
	private class genericSpinnerListModel extends SpinnerListModel {

		public genericSpinnerListModel(String[] adjacentList) {
			super(adjacentList);
		}
	}

	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private final LaunchAttackDialog dlg;
		private final boolean doselection;

		public AcceptDialogMouseAdapter(LaunchAttackDialog dlg, boolean selectioni) {
			this.dlg = dlg;
			doselection = selectioni;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (doselection == true) {
				if (dlg.correctArsenal()) {
					dlg.selection = doselection;
					dlg.territoryIndex = dlg.territoryCombo.getSelectedIndex();
					dlg.soldierCount = Integer.parseInt((String) (dlg.soldierCountSpinner.getValue()));
					dlg.cannonCount = Integer.parseInt(((String) (dlg.cannonToneSpinner.getValue())));
					dlg.missileCount = Integer.parseInt(((String) (dlg.missileCountSpinner.getValue())));
					dlg.icbmCount = Integer.parseInt(((String) (dlg.icbmCountSpinner.getValue())));
					dlg.setVisible(false);
				} else {
					dlg.infoLabel.setText("Arsenal no disponible");
					dlg.infoLabel.setVisible(true);
				}
			} else {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}

	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private final LaunchAttackDialog dlg;

		public AcceptDialogKeyAdapter(LaunchAttackDialog dlg) {
			this.dlg = dlg;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				dlg.selection = true;
				dlg.setVisible(false);
			} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				dlg.selection = false;
				dlg.setVisible(false);
			}
		}
	}

}
