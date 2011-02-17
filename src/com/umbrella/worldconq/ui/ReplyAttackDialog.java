package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
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
	private JPanel jPanel1;
	private JComboBox soldiersCombo;
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
					{
						negotiateButton = new JButton();
						attackpanel.add(negotiateButton, new AnchorConstraint(
							75, 772, 785, 251, AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_NONE,
							AnchorConstraint.ANCHOR_NONE,
							AnchorConstraint.ANCHOR_NONE));
						negotiateButton.setText("Rechazar el ataque");
						negotiateButton.setPreferredSize(new java.awt.Dimension(
							200, 25));
						negotiateButton.setToolTipText("Rechazar el ataque y negociar");
						negotiateButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent evt) {
								ReplyAttackDialog.this.negotiateButtonMouseClicked(evt);
							}
						});
					}
					{
						acceptButton = new JButton();
						attackpanel.add(acceptButton, new AnchorConstraint(29,
							772, 425, 251, AnchorConstraint.ANCHOR_ABS,
							AnchorConstraint.ANCHOR_NONE,
							AnchorConstraint.ANCHOR_NONE,
							AnchorConstraint.ANCHOR_NONE));
						acceptButton.setText("Aceptar el ataque");
						acceptButton.setPreferredSize(new java.awt.Dimension(
							200, 25));
						acceptButton.setToolTipText("Aceptar el ataque");
						acceptButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent evt) {
								ReplyAttackDialog.this.acceptButtonMouseClicked(evt);
							}
						});
					}
				}
				{
					negotiatePanel = new JPanel();
					final AnchorLayout negotiatePanelLayout = new AnchorLayout();
					negotiatePanel.setLayout(negotiatePanelLayout);
					jPanel1.add(negotiatePanel);
					negotiatePanel.setPreferredSize(new java.awt.Dimension(258,
						233));
					negotiatePanel.setVisible(false);
					{
						acceptNegotiationButton = new JButton();
						negotiatePanel.add(acceptNegotiationButton,
							new AnchorConstraint(738, 972, 917, 772,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL));
						acceptNegotiationButton.setText("Negociar");
						acceptNegotiationButton.setPreferredSize(new java.awt.Dimension(
							77, 23));
						acceptNegotiationButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent evt) {
								ReplyAttackDialog.this.acceptNegotiationButtonMouseClicked(evt);
							}
						});
					}
					{
						soldiersLabel = new JLabel();
						negotiatePanel.add(soldiersLabel, new AnchorConstraint(
							394, 748, 519, 32, AnchorConstraint.ANCHOR_REL,
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
							371, 970, 550, 779, AnchorConstraint.ANCHOR_REL,
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
						negotiatePanel.add(moneyCombo, new AnchorConstraint(97,
							970, 277, 782, AnchorConstraint.ANCHOR_REL,
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

	private void acceptButtonMouseClicked(MouseEvent evt) {
		System.out.println("acceptButton.mouseClicked, event=" + evt);
		//TODO add your code for acceptButton.mouseClicked
	}

	private void negotiateButtonMouseClicked(MouseEvent evt) {
		System.out.println("negotiateButton.mouseClicked, event=" + evt);
		//TODO add your code for negotiateButton.mouseClicked
	}

	private void acceptNegotiationButtonMouseClicked(MouseEvent evt) {
		System.out.println("acceptNegotiationButton.mouseClicked, event=" + evt);
		//TODO add your code for acceptNegotiationButton.mouseClicked
	}

}
