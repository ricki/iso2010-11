package com.umbrella.worldconq.comm;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import com.umbrella.worldconq.domain.Attack;
import com.umbrella.worldconq.domain.Session;
import com.umbrella.worldconq.domain.TerritoryDecorator;
import communications.IServer;

import domain.EventType;
import domain.Game;
import domain.GameInfo;
import domain.Player;
import domain.Territory;

public class ServerAdapter {

	private String mRemoteName;
	private InetAddress mRemoteHost;
	private int mRemotePort;
	private IServer mProxy;

	public ServerAdapter() {
		mRemoteName = null;
		mRemoteHost = null;
		mRemotePort = 0;
		mProxy = null;
	}

	public ServerAdapter(String remoteName, InetAddress remoteHost, int remotePort) {
		this.setRemoteInfo(remoteName, remoteHost, remotePort);
		mProxy = null;
	}

	public void setRemoteInfo(String remoteName, InetAddress remoteHost, int remotePort) {
		mRemoteName = remoteName;
		mRemoteHost = remoteHost;
		mRemotePort = remotePort;
	}

	public String getRemoteName() {
		return mRemoteName;
	}

	public InetAddress getRemoteHost() {
		return mRemoteHost;
	}

	public int getRemotePort() {
		return mRemotePort;
	}

	public void connect() throws MalformedURLException, RemoteException, NotBoundException {
		this.disconnect();
		final String url = String.format("rmi://%s:%d/%s",
			mRemoteHost.getHostAddress(),
			mRemotePort,
			mRemoteName);
		System.out.println("Connecting to " + url);
		mProxy = (IServer) Naming.lookup(url);
	}

	public void disconnect() {
		mProxy = null;
	}

	public boolean isConnected() {
		return mProxy != null;
	}

	private void checkConnection() throws Exception {
		if (!this.isConnected()) throw new RemoteException();
	}

	public UUID createSession(String login, String passwd, Remote callback) throws Exception {
		this.checkConnection();
		return mProxy.loginUser(login, passwd, callback);
	}

	public void closeSession(Session session) throws Exception {
		this.checkConnection();
		mProxy.logoutUser(session.getId());
	}

	public void registerUser(String login, String passwd, String email) throws Exception {
		this.checkConnection();
		mProxy.registerUser(login, passwd, email);
	}

	public ArrayList<GameInfo> fetchGameList(Session session) throws Exception {
		this.checkConnection();
		return mProxy.listGames(session.getId());
	}

	public void createGame(Session session, GameInfo game) throws Exception {
		this.checkConnection();
		mProxy.createGame(session.getId(), game);
	}

	public void joinGame(Session session, GameInfo game) throws Exception {
		this.checkConnection();
		mProxy.joinGame(session.getId(), game.getId());
	}

	public Game playGame(Session session, GameInfo game) throws Exception {
		this.checkConnection();
		return mProxy.playGame(session.getId(), game.getId());

	}

	public void quitGame(Session session, Game game) throws Exception {
		this.checkConnection();
		mProxy.quitGame(session.getId(), game.getGameInfo().getId());
	}

	public void resignGame(Session session, Game game) throws Exception {
		this.checkConnection();
		mProxy.resignGame(session.getId(), game.getGameInfo().getId());
	}

	public void attackTerritory(Session session, Game game, Attack currentAttack) throws Exception {
		this.checkConnection();
		mProxy.attackTerritory(session.getId(), game.getGameInfo().getId(),
			currentAttack.getOrigin().getDecoratedTerritory(),
			currentAttack.getDestination().getDecoratedTerritory(),
			currentAttack.getArsenal());
	}

	public void acceptAttack(Session session, Game game) throws Exception {
		this.checkConnection();
		mProxy.acceptAttack(session.getId(), game.getGameInfo().getId());
	}

	public void requestNegotiation(Session session, Game game, int money, int soldiers) throws Exception {
		this.checkConnection();
		mProxy.requestedNegotiation(session.getId(),
			game.getGameInfo().getId(), money, soldiers);
	}

	public void updateGame(Session session, Game game, ArrayList<Player> playerUpdate, ArrayList<TerritoryDecorator> territoryUpdate, EventType event) throws Exception {
		this.checkConnection();
		final ArrayList<Territory> territoryList = new ArrayList<Territory>();
		for (final TerritoryDecorator t : territoryUpdate)
			territoryList.add(t.getDecoratedTerritory());
		mProxy.updateGame(session.getId(), game.getGameInfo().getId(),
			playerUpdate, territoryList, event);
	}

}
