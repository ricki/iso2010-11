package com.umbrella.worldconq.stubserver;

import java.rmi.*;
import java.util.UUID;

public interface IServer extends java.rmi.Remote {
	/* Clase que contiene las definiciones que implementara el servidor */

	public void registerUser(String UserName, String sPassword, String sEmail)
			throws Exception, RemoteException;

	public UUID loginUser(String UserName, String Password,
			Client referenciaRemota) throws Exception, RemoteException;

	public void logoutUser(UUID session) throws Exception, RemoteException;

	// public ArrayList<GameInfo> listGames() throws Exception, RemoteException;
	// public UUID createGame (GameInfo info) throws Exception, RemoteException;
	public void joinGame(UUID session, UUID partida) throws Exception,
			RemoteException;

	public void resignGame(UUID session, UUID partida) throws Exception,
			RemoteException;

	// public Game playGame (UUID session, UUID partida) throws Exception,
	// RemoteException;
	public void quitGame(UUID session, UUID partida) throws Exception,
			RemoteException;

	// public void updateGame(UUID sessId, UUID gameId, Vector <Player>
	// playerUpdate, Vector<Territory> territoryUpdate) throws Exception,
	// RemoteException;
	// public void notifyNextAttack(UUID idSesion,UUID idPartida, Territory
	// origen,Territory destino) throws Exception,RemoteException;
	public void acceptAttack(UUID idSession, UUID idPartida) throws Exception,
			RemoteException;

	public void negotiate(UUID idSession, UUID dPartida, int money, int soldiers)
			throws Exception, RemoteException;

	public void acceptOffer(UUID idSession, UUID idPartida) throws Exception,
			RemoteException;

}
