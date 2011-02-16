package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	private JPanel negotiatePanel;
	private JPanel attackpanel;

	/**
	 * Auto-generated main method to display this JDialog
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final JFrame frame = new JFrame();
				final ReplyAttackDialog inst = new ReplyAttackDialog(frame);
				inst.setVisible(true);
			}
		});
	}

	public ReplyAttackDialog(JFrame frame) {
		super(frame);
		this.initGUI();
	}

	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				final FlowLayout jPanel1Layout = new FlowLayout();
				this.getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(jPanel1Layout);
				{
					attackpanel = new JPanel();
					jPanel1.add(attackpanel);
					attackpanel.setPreferredSize(new java.awt.Dimension(291, 65));
				}
				{
					negotiatePanel = new JPanel();
					jPanel1.add(negotiatePanel);
					negotiatePanel.setPreferredSize(new java.awt.Dimension(152,
						119));
				}
			}
			this.setSize(400, 300);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
