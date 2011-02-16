package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
public class pruebas extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JComboBox territoryCombo;
	private JButton jButton1;
	private JLabel labelinfo;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final pruebas inst = new pruebas();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public pruebas() {
		super();
		this.initGUI();
	}

	private void initGUI() {
		try {
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				this.getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					//Genero el contenido del ComboBox de territorios a atacar
					//final String[] adjacentListNames = new String[adjacentList.size()];
					territoryCombo = new JComboBox();
					for (int i = 0; i < 7; i++) {
						territoryCombo.addItem(i);
					}
					//final ComboBoxModel territoryComboModel =
					//	new DefaultComboBoxModel(
					//new String[] {
					//"Item One", "Item Two"
					//});
					jPanel1.add(territoryCombo);
					//territoryCombo.setModel(territoryComboModel);
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("jButton1");
					jButton1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent evt) {
							pruebas.this.jButton1MouseClicked(evt);
						}
					});
				}
				{
					labelinfo = new JLabel();
					jPanel1.add(labelinfo);
					labelinfo.setPreferredSize(new java.awt.Dimension(122, 42));
				}
			}
			this.pack();
			this.setSize(400, 300);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void jButton1MouseClicked(MouseEvent evt) {
		System.out.println("jButton1.mouseClicked, event=" + evt);
		//TODO add your code for jButton1.mouseClicked
		labelinfo.setText("" + territoryCombo.getSelectedIndex());
	}

}
