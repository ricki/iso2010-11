package com.umbrella.worldconq;

import java.net.InetAddress;

import com.umbrella.worldconq.comm.ServerAdapter;
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
		
		try {
			WorldConqApp.getServerAdapter().setRemoteInfo(
				"WorldConqStubServer",
				InetAddress.getByName("localhost"),
				3234
			);
			WorldConqApp.getServerAdapter().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		StartupWindow stw = new StartupWindow();
		stw.setLocationRelativeTo(null);
		stw.setVisible(true);
	}
}	
