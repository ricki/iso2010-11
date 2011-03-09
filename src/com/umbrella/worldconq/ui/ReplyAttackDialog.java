package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Arsenal;

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
				this.setResizable(false);
			}
			jPanel1 = new JPanel();
			final GridLayout jPanel1Layout = new GridLayout(2, 1);
			jPanel1Layout.setHgap(5);
			jPanel1Layout.setVgap(5);
			jPanel1Layout.setColumns(1);
			jPanel1Layout.setRows(2);
			this.getContentPane().add(jPanel1, BorderLayout.CENTER);
			jPanel1.setLayout(jPanel1Layout);

			attackpanel = new JPanel();
			attackpanel.setLayout(null);
			jPanel1.add(attackpanel);
			attackpanel.setPreferredSize(new java.awt.Dimension(394, 132));
			attackpanel.setMaximumSize(new java.awt.Dimension(400, 150));
			attackpanel.setBorder(new LineBorder(new java.awt.Color(0,
				0, 0), 1, false));

			attackpanel.add(icbminfoLabel);
			attackpanel.add(missileinfoLabel);
			attackpanel.add(cannoninfoLabel);
			attackpanel.add(soldiersinfoLabel);
			attackpanel.add(infoLabel);

			negotiateButton = new JButton();
			negotiateButton.setLayout(null);
			attackpanel.add(negotiateButton);
			negotiateButton.setText("Rechazar ataque");
			negotiateButton.setToolTipText("Rechazar el ataque y negociar");
			negotiateButton.setBounds(178, 87, 204, 35);
			//+++++++++++++++++++++

			acceptButton = new JButton();
			attackpanel.add(acceptButton);
			acceptButton.setText("Aceptar ataque");
			acceptButton.setToolTipText("Aceptar el ataque");
			acceptButton.setBounds(178, 46, 204, 35);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			acceptButton.addKeyListener(new AcceptDialogKeyAdapter(
				this));
			acceptButton.addMouseListener(new AcceptDialogMouseAdapter(
				this, true));

			negotiatePanel = new JPanel();
			negotiatePanel.setLayout(null);
			jPanel1.add(negotiatePanel);
			negotiatePanel.setPreferredSize(new java.awt.Dimension(393, 133));
			negotiatePanel.setEnabled(false);
			negotiatePanel.setBorder(new LineBorder(new java.awt.Color(
				0, 0, 0), 1, false));

			tattackedinfoPanel = new JPanel();
			tattackedinfoPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			tattackedinfoPanel.setBounds(5, 72, 267, 56);
			negotiatePanel.add(tattackedinfoPanel);

			tainfoLabel = new JLabel();
			tattackedinfoPanel.add(tainfoLabel);
			tainfoLabel.setText("Territorio atacado: ");
			tainfoLabel.setPreferredSize(new java.awt.Dimension(250, 20));

			tattackedinfoPanel.add(tattackedinfoLabel);

			tattackedinfoLabel.setPreferredSize(new java.awt.Dimension(250, 20));

			acceptNegotiationButton = new JButton();
			negotiatePanel.add(acceptNegotiationButton);
			acceptNegotiationButton.setText("Negociar");

			acceptNegotiationButton.setToolTipText("Rechazar el ataque y negociar");
			acceptNegotiationButton.setBounds(279, 88, 104, 32);
			acceptNegotiationButton.addMouseListener(new NegotiateDialogMouseAdapter(
				this, false));

			soldiersLabel = new JLabel();
			soldiersLabel.setText("Indique la cantidad de soldados: ");
			soldiersLabel.setBounds(12, 41, 282, 18);
			negotiatePanel.add(soldiersLabel);

			moneyLabel = new JLabel();
			moneyLabel.setText("Indique la cantidad de dinero: ");
			moneyLabel.setBounds(12, 14, 283, 18);
			negotiatePanel.add(moneyLabel);
			negotiatePanel.add(soldiersCombo);
			negotiatePanel.add(moneyCombo);
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
		soldiersCombo.setBounds(305, 39, 76, 24);
		for (int i = 0; i <= numsoldiers; i++) {
			soldiersCombo.addItem(i);
		}

		//Creo el combo de monedas
		final int nummoney = destinyt.getPlayer().getMoney();
		moneyCombo = new JComboBox();
		moneyCombo.setBounds(307, 10, 74, 24);
		for (int i = 0; i <= nummoney; i++) {
			moneyCombo.addItem(i);
		}

		//Genero la info del ataque del oponente
		final String attackinfo = "Está siendo atacado desde "
				+ sourcet.getName()
				+ " por " + sourcet.getPlayer().getName();
		infoLabel.setText(attackinfo);
		infoLabel.setBounds(10, 4, 378, 21);
		soldiersinfoLabel.setText("con " + arsenal.getSoldiers()
				+ " soldados");
		soldiersinfoLabel.setBounds(10, 31, 156, 21);
		cannoninfoLabel.setText("con " + arsenal.getCannons() + " cañones");
		cannoninfoLabel.setBounds(10, 52, 156, 21);
		missileinfoLabel.setText("con " + arsenal.getMissiles() + " misiles");
		missileinfoLabel.setBounds(10, 73, 156, 21);
		icbminfoLabel.setText("con " + arsenal.getICBMs() + " icbms");
		icbminfoLabel.setBounds(10, 94, 156, 20);
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
