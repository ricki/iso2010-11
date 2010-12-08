package com.umbrella.worldconq.stubserver;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 8434201731175738674L;
	private int puerto = 3234;
	private String miIP;
	private Registry reg;
	
	private String[][] registerUsers = {
			{ "JorgeCA", "jorge", "jorge.colao@gmail.com"},
			{ "ricki", "ricardo", "ricardo.ruedas@gmail.com"},
			{ "pobleteag", "antonio", "pobleteag@gmail.com"},
			{ "DaniLR", "daniel", "daniel.leonromero@gmail.com"},
			{ "Aduran", "angel", "anduraniz@gmail.com"},
			{ "LauraN", "laura", "arualitan@gmail.com"},
			{ "deejaytoni", "toni", "deejaytoni@gmail.com"} };
	
	private ArrayList<GameInfo> gameList;
	
	public Server() throws Exception, RemoteException {
		super();
		
		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());
		
		this.gameList = new ArrayList<GameInfo>();
		{ // GameInfo 01
			GameInfo info = new GameInfo();
			info.ID = UUID.randomUUID();
			info.Name = "game01";
			info.Description = "desc form game01";
			info.Players = new ArrayList<String>();
			info.Players.add("JorgeCA");
			info.Players.add("Aduran");
			info.GameSessions = new ArrayList<Calendar>();
			info.GameSessions.add(Calendar.getInstance());
			gameList.add(info);
		}
		{ // GameInfo 02
			GameInfo info = new GameInfo();
			info.ID = UUID.randomUUID();
			info.Name = "game02";
			info.Description = "desc form game02";
			info.Players = new ArrayList<String>();
			info.Players.add("ricki");
			info.Players.add("DaniLR");
			info.Players.add("deejaytoni");
			info.GameSessions = new ArrayList<Calendar>();
			info.GameSessions.add(Calendar.getInstance());
			gameList.add(info);
		}
		{ // GameInfo 03
			GameInfo info = new GameInfo();
			info.ID = UUID.randomUUID();
			info.Name = "game03";
			info.Description = "desc form game03";
			info.Players = new ArrayList<String>();
			info.Players.add("pobleteag");
			info.Players.add("LauraN");
			info.GameSessions = new ArrayList<Calendar>();
			info.GameSessions.add(Calendar.getInstance());
			gameList.add(info);
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
		System.out.println("IServer::acceptAttack");
	}

	@Override
	public void acceptOffer(UUID idSession, UUID idPartida) throws Exception,
			RemoteException {
		System.out.println("IServer::acceptOffer");
	}

	@Override
	public void joinGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		System.out.println("IServer::joinGame");
	}

	@Override
	public UUID loginUser(String UserName, String Password,
			Client referenciaRemota) throws Exception, RemoteException {
		System.out.println("IServer::loginUser " + UserName);
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
		System.out.println("IServer::logoutUser " + session);
	}

	@Override
	public void negotiate(UUID idSession, UUID dPartida, int money, int soldiers)
			throws Exception, RemoteException {
		System.out.println("IServer::negotiate");
	}

	@Override
	public void quitGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		System.out.println("IServer::quitGame");
	}

	@Override
	public void registerUser(String UserName, String sPassword, String sEmail)
			throws Exception, RemoteException {
		System.out.println("IServer::registerUser " + UserName);
	}

	@Override
	public void resignGame(UUID session, UUID partida) throws Exception,
			RemoteException {
		System.out.println("IServer::resignGame");
	}

	@Override
	public UUID createGame(GameInfo info) throws Exception, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GameInfo> listGames() throws Exception, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
