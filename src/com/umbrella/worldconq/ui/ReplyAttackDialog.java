package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Arsenal;

public class ReplyAttackDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = -485789698446933968L;
	private JPanel jPanel1;
	private JComboBox soldiersCombo;
	private JLabel missileinfoLabel;
	private JLabel tainfoLabel;
	private JPanel tattackedinfoPanel;
	private JLabel tattackedinfoLabel;
	private JLabel icbminfoLabel;
	private JLabel cannoninfoLabel;
	private JLabel soldiersinfoLabel;
	private JLabel infoLabel;
	private JLabel moneyLabel;
	private JButton acceptNegotiationButton;
	private JLabel soldiersLabel;
	private JComboBox moneyCombo;
	private JButton negotiateButton;
	private JButton acceptButton;
	private JPanel negotiatePanel;
	private JPanel attackpanel;
	private boolean selection;
	private int money, soldiers;
	private final TerritoryDecorator sourcet, destinyt;
	private final Arsenal arsenal;

	public ReplyAttackDialog(JFrame frame, TerritoryDecorator src, TerritoryDecorator dst, Arsenal arsenal) {
		super(frame);
		sourcet = src;
		destinyt = dst;
		this.arsenal = arsenal;
		this.initGUI();
	}

	private void initGUI() {
		this.createCombos();
		try {
			{
				jPanel1 = new JPanel();
				final GridLayout jPanel1Layout = new GridLayout(2, 1);
				jPanel1Layout.setHgap(5);
				jPanel1Layout.setVgap(5);
				jPanel1Layout.setColumns(1);
				jPanel1Layout.setRows(2);
				this.getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(jPanel1Layout);
				{
					attackpanel = new JPanel();
					final AnchorLayout attackpanelLayout = new AnchorLayout();
					attackpanel.setLayout(attackpanelLayout);
					jPanel1.add(attackpanel);
					attackpanel.setPreferredSize(new java.awt.Dimension(291, 65));
					attackpanel.setMaximumSize(new java.awt.Dimension(400, 150));
					attackpanel.setBorder(new LineBorder(new java.awt.Color(0,
						0, 0), 1, false));
					{
						attackpanel.add(icbminfoLabel, new AnchorConstraint(
							707, 417, 863, 27, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						icbminfoLabel.setPreferredSize(new java.awt.Dimension(
							150, 20));
					}
					{
						attackpanel.add(missileinfoLabel, new AnchorConstraint(
							550, 417, 707, 27, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						missileinfoLabel.setPreferredSize(new java.awt.Dimension(
							150, 20));
					}
					{
						attackpanel.add(cannoninfoLabel, new AnchorConstraint(
							394, 417, 550, 27, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						cannoninfoLabel.setPreferredSize(new java.awt.Dimension(
							150, 20));
					}
					{
						attackpanel.add(soldiersinfoLabel,
							new AnchorConstraint(238, 417, 394, 27,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						soldiersinfoLabel.setPreferredSize(new java.awt.Dimension(
							150, 20));
					}
					{
						attackpanel.add(infoLabel, new AnchorConstraint(82,
							938, 238, 27, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));

						infoLabel.setPreferredSize(new java.awt.Dimension(350,
							20));
					}
					{
						negotiateButton = new JButton();
						negotiateButton.setLayout(null);
						attackpanel.add(negotiateButton, new AnchorConstraint(
							87, 11, 11, 173, AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS));
						negotiateButton.setText("Rechazar ataque");
						negotiateButton.setToolTipText("Rechazar el ataque y negociar");
						negotiateButton.setPreferredSize(new java.awt.Dimension(
							200, 30));
						//+++++++++++++++++++++
						acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, false));
					}
					{
						acceptButton = new JButton();
						attackpanel.add(acceptButton, new AnchorConstraint(46,
							11, 52, 173, AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_ABS));
						acceptButton.setText("Aceptar ataque");
						acceptButton.setToolTipText("Aceptar el ataque");
						acceptButton.setPreferredSize(new java.awt.Dimension(
							200, 30));
						//+++++++++++++++++++++++++++++++++++++++++++++++++++++
						acceptButton.addKeyListener(new AcceptDialogKeyAdapter(
							this));
						acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
							this, true));

					}
				}
				{
					negotiatePanel = new JPanel();
					final AnchorLayout negotiatePanelLayout = new AnchorLayout();
					negotiatePanel.setLayout(negotiatePanelLayout);
					jPanel1.add(negotiatePanel);
					negotiatePanel.setPreferredSize(new java.awt.Dimension(258,
						233));
					negotiatePanel.setEnabled(false);
					negotiatePanel.setBorder(new LineBorder(new java.awt.Color(
						0, 0, 0), 1, false));
					{
						tattackedinfoPanel = new JPanel();
						negotiatePanel.add(tattackedinfoPanel,
							new AnchorConstraint(535, 694, 957, 14,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						tattackedinfoPanel.setPreferredSize(new java.awt.Dimension(
							261, 54));
						tattackedinfoPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						{
							tainfoLabel = new JLabel();
							tattackedinfoPanel.add(tainfoLabel);
							tainfoLabel.setText("Territorio atacado: ");
							tainfoLabel.setPreferredSize(new java.awt.Dimension(
								250, 20));
						}
						{
							tattackedinfoPanel.add(tattackedinfoLabel);
						}
					}
					{
						tattackedinfoLabel.setPreferredSize(new java.awt.Dimension(
							250, 20));
					}
					{
						acceptNegotiationButton = new JButton();
						negotiatePanel.add(acceptNegotiationButton,
							new AnchorConstraint(675, 970, 910, 709,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						acceptNegotiationButton.setText("Negociar");

						acceptNegotiationButton.setPreferredSize(new java.awt.Dimension(
							100, 30));
						acceptNegotiationButton.setToolTipText("Rechazar el ataque y negociar");
						acceptNegotiationButton.addMouseListener(new NegotiateDialogMouseAdapter(
							this, false));
					}
					{
						soldiersLabel = new JLabel();
						negotiatePanel.add(soldiersLabel, new AnchorConstraint(
							316, 748, 441, 32, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						soldiersLabel.setText("Indique la cantidad de soldados: ");
						soldiersLabel.setPreferredSize(new java.awt.Dimension(
							275, 16));
					}
					{
						moneyLabel = new JLabel();
						negotiatePanel.add(moneyLabel, new AnchorConstraint(
							121, 751, 246, 32, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						moneyLabel.setText("Indique la cantidad de dinero: ");
						moneyLabel.setPreferredSize(new java.awt.Dimension(276,
							16));
					}
					{
						final ComboBoxModel soldiersComboModel =
								new DefaultComboBoxModel(
							new String[] {
								"Item One", "Item Two"
						});
						soldiersCombo = new JComboBox();
						negotiatePanel.add(soldiersCombo, new AnchorConstraint(
							300, 970, 480, 779, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						soldiersCombo.setModel(soldiersComboModel);
						soldiersCombo.setPreferredSize(new java.awt.Dimension(
							73, 23));
					}
					{
						final ComboBoxModel moneyComboModel =
								new DefaultComboBoxModel(
							new String[] {
								"Item One", "Item Two"
						});
						moneyCombo = new JComboBox();
						negotiatePanel.add(moneyCombo, new AnchorConstraint(82,
							970, 261, 782, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						moneyCombo.setModel(moneyComboModel);
						moneyCombo.setPreferredSize(new java.awt.Dimension(72,
							23));
					}
				}
			}
			this.setSize(400, 300);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	//Método que dice si el ataque se aceptó o no
	public boolean isAttackAccepted() {
		return selection;
	}

	//Método que devuelve el número de monedas
	public int getMoney() {
		return money;
	}

	//Método que devuelve el número de soldados
	public int getSoldierCount() {
		return soldiers;
	}

	//Método que genera los distintos Spinners
	private void createCombos() {
		infoLabel = new JLabel();
		soldiersinfoLabel = new JLabel();
		cannoninfoLabel = new JLabel();
		missileinfoLabel = new JLabel();
		icbminfoLabel = new JLabel();
		tattackedinfoLabel = new JLabel();

		//Creo el combo de soldados
		final int numsoldiers = destinyt.getNumSoldiers();
		soldiersCombo = new JComboBox();
		for (int i = 0; i <= numsoldiers; i++) {
			soldiersCombo.addItem(i);
		}

		//Creo el combo de monedas
		final int nummoney = destinyt.getPlayer().getMoney();
		moneyCombo = new JComboBox();
		for (int i = 0; i <= nummoney; i++) {
			moneyCombo.addItem(i);
		}

		//Genero la info del ataque del oponente
		final String attackinfo = "Está siendo atacado desde "
				+ sourcet.getName()
				+ " por " + sourcet.getPlayer().getName();
		infoLabel.setText(attackinfo);
		soldiersinfoLabel.setText("con " + arsenal.getSoldiers()
				+ " soldados");
		cannoninfoLabel.setText("con " + arsenal.getCannons() + " cañones");
		missileinfoLabel.setText("con " + arsenal.getMissiles() + " misiles");
		icbminfoLabel.setText("con " + arsenal.getICBMs() + " icbms");
		tattackedinfoLabel.setText("" + destinyt.getName());

	}

	private class AcceptDialogMouseAdapter extends MouseAdapter {

		private final ReplyAttackDialog dlg;
		private final boolean doselection;

		public AcceptDialogMouseAdapter(ReplyAttackDialog replyAttackDialog, boolean selectioni) {
			dlg = replyAttackDialog;
			doselection = selectioni;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void mouseClicked(MouseEvent evt) {
			if (doselection == false) {
				dlg.negotiatePanel.setEnabled(true);
				dlg.attackpanel.setEnabled(false);
				dlg.negotiatePanel.setNextFocusableComponent(dlg.moneyCombo);
			} else {
				dlg.selection = doselection;
				dlg.setVisible(false);
			}
		}
	}

	//Clase privada para capturar el evento del botón de negociar
	private class NegotiateDialogMouseAdapter extends MouseAdapter {

		private final ReplyAttackDialog dlg;
		private final boolean doselection;

		public NegotiateDialogMouseAdapter(ReplyAttackDialog replyAttackDialog, boolean selectioni) {
			dlg = replyAttackDialog;
			doselection = selectioni;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			dlg.money = dlg.moneyCombo.getSelectedIndex();
			dlg.soldiers = dlg.soldiersCombo.getSelectedIndex();
			dlg.selection = doselection;
			dlg.setVisible(false);
		}
	}

	private class AcceptDialogKeyAdapter extends KeyAdapter {
		private final ReplyAttackDialog dlg;

		public AcceptDialogKeyAdapter(ReplyAttackDialog replyAttackDialog) {
			dlg = replyAttackDialog;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ENTER
					&& dlg.acceptButton.getFocusTraversalKeysEnabled()) {
				dlg.selection = true;
				dlg.setVisible(false);
			}
		}
	}

}
