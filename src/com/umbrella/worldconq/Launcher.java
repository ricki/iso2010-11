package com.umbrella.worldconq;

import javax.swing.JFrame;

import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.ui.*;

public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		RegisterDialog dlg = new RegisterDialog(f, "Registro", true);
		dlg.setVisible(true);
		System.out.println("Fin del registro");
		f.dispose();

		if (dlg.getSelection() == true) new UserManager().registerUser("A", "B","");
		else System.out.println("Ha pulsado adios");
	}

}
