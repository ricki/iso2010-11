package com.umbrella.worldconq;

import java.net.InetAddress;

public class Launcher {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}

		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());

		WorldConqApp.getServerAdapter().setRemoteInfo(
			"WorldConqStubServer",
			InetAddress.getByName("localhost"),
			3234
		);
		WorldConqApp.getServerAdapter().connect();

		WorldConqApp.getStartupWindow().setLocationRelativeTo(null);
		WorldConqApp.getStartupWindow().setVisible(true);
	}
}	
