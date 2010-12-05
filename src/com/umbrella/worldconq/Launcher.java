package com.umbrella.worldconq;

import com.umbrella.worldconq.ui.StartupWindow;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());
		
		StartupWindow stw = new StartupWindow();
		stw.setLocationRelativeTo(null);
		stw.setVisible(true);
	}
}	
