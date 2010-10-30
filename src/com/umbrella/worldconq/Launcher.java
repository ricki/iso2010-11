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
		RegisterDialog dlg = new RegisterDialog(f, "La Conquista del Mundo - Registro", true);
		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);
		System.out.println("Fin del registro");
		f.dispose();
		
		
		if (dlg.getSelection() == true) new UserManager().registerUser(dlg.getUser(), dlg.getPasswd(), dlg.getEmail());
		else System.out.println("Ha pulsado Esc");
	/*}
		StartupWindow stw = new StartupWindow();
		stw.setLocationRelativeTo(null);
		stw.setVisible(true);*/	
	}
}	
