package com.umbrella.worldconq;

import javax.swing.JFrame;
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
	}

}
