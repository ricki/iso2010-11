package com.umbrella.worldconq.stubserver;

import java.io.File;
import java.rmi.Naming;
import java.util.UUID;

public class Client {

	// Add here your security policy path
	private static final String[] secPaths = {
		"/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
		"/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
	};
	
	private static int puerto=3234;
	private static String IP="127.0.1.1";

	public static void main(String[] args) throws Exception {
		// Load security policy
		for (int i = 0; i < secPaths.length; i++) {
			File f = new File(secPaths[i]);
			if (f.exists()) {
				System.setProperty("java.security.policy", secPaths[i]);
				break;
			}
		}
		
		IServer prx = null;
		prx = (IServer) Naming.lookup("rmi://"+IP+":"+puerto+"/WorldConqStubServer");
		System.out.println("Conectado a :"+"rmi://"+IP+":"+puerto+"/WorldConqStubServer");
		
		// registramos a los usuarios para comprobar la funcionalidad
		//prx.registerUser("JorgeCA", "jorge", "jorge.colao@gmail.com");
		//prx.registerUser("ricki", "ricardo", "ricardo.ruedas@gmail.com");
		//prx.registerUser("pobleteag", "antonio", "pobleteag@gmail.com");
		//prx.registerUser("DaniLR", "daniel", "daniel.leonromero@gmail.com");
		//prx.registerUser("Aduran", "angel", "anduraniz@gmail.com");
		//prx.registerUser("LauraN", "laura", "arualitan@gmail.com");
		//prx.registerUser("deejaytoni", "toni", "deejaytoni@gmail.com");

		// hacemos un login de cada uno para comprobar si es correcto
		UUID jorge=prx.loginUser("JorgeCA", "jorge", null);
		UUID ricardo=prx.loginUser("ricki", "ricardo", null);
		UUID antonio=prx.loginUser("pobleteag", "antonio", null);
		UUID daniel=prx.loginUser("DaniLR", "daniel", null);
		UUID angel=prx.loginUser("Aduran", "angel", null);
		UUID laura=prx.loginUser("LauraN", "laura", null);
		UUID toni=prx.loginUser("deejaytoni", "toni", null);

		// El usuario se desconecta
		prx.logoutUser(jorge);
		prx.logoutUser(ricardo);
		prx.logoutUser(antonio);
		prx.logoutUser(daniel);
		prx.logoutUser(angel);
		prx.logoutUser(laura);
		prx.logoutUser(toni);

	}

}
