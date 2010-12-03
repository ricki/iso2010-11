package com.umbrella.worldconq.stubserver;

import java.io.File;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.UUID;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	private int puerto = 3234;
	private String miIP;
	private Registry reg;
	
	// Add here your security policy path
	private static final String[] secPaths = {
		"/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
		"/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
	};
	
	private String[][] registerUsers = {
			{ "JorgeCA", "jorge", "jorge.colao@gmail.com"},
			{ "ricki", "ricardo", "ricardo.ruedas@gmail.com"},
			{ "pobleteag", "antonio", "pobleteag@gmail.com"},
			{ "DaniLR", "daniel", "daniel.leonromero@gmail.com"},
			{ "Aduran", "angel", "anduraniz@gmail.com"},
			{ "LauraN", "laura", "arualitan@gmail.com"},
			{ "deejaytoni", "toni", "deejaytoni@gmail.com"} };

	
	public Server() throws Exception, RemoteException {
		super();
		
		// Load security policy
		for (int i = 0; i < secPaths.length; i++) {
			File f = new File(secPaths[i]);
			if (f.exists()) {
				System.setProperty("java.security.policy", secPaths[i]);
				break;
			}
		}
		
		miIP = (InetAddress.getLocalHost()).toString();
		System.out.println("Conexion establecida por:");
		System.out.println("IP=" + miIP + ", y puerto=" + puerto);

		reg = LocateRegistry.createRegistry(puerto);
		reg.rebind("WorldConqStubServer", this);

		System.out.println("Esperando peticiones...");
	}

	public static void main(String[] args) throws Exception {
		new Server();
	}

	@Override
	public void acceptAttack(UUID idSession, UUID idPartida) throws Exception,
			RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptOffer(UUID idSession, UUID idPartida) throws Exception,
			RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void joinGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public UUID loginUser(String UserName, String Password,
			Client referenciaRemota) throws Exception, RemoteException {
		
		boolean encontrado = false;
		UUID id = null;
		for (int i = 0; i < registerUsers.length && encontrado==false; i++) {
			if (registerUsers[i][0].compareTo(UserName)==0
					&& registerUsers[i][1].compareTo(Password)==0) {
				encontrado = true;
				id = UUID.randomUUID();
			}
		}
		if(encontrado==false) throw new Exception("Usuario no registrado");

		return id;
	}

	@Override
	public void logoutUser(UUID session) throws Exception, RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void negotiate(UUID idSession, UUID dPartida, int money, int soldiers)
			throws Exception, RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void quitGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerUser(String UserName, String sPassword, String sEmail)
			throws Exception, RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resignGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		// TODO Auto-generated method stub

	}

}
