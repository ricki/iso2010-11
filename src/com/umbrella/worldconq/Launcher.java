package com.umbrella.worldconq;

import javax.swing.JFrame;

import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.ui.*;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StartupWindow stw = new StartupWindow();
		stw.setLocationRelativeTo(null);
		stw.setVisible(true);
	}
}	
