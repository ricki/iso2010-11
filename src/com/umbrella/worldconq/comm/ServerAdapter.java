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
import exceptions.AlreadyInGameException;
import exceptions.FullGameException;
import exceptions.GameNotFoundException;
import exceptions.InvalidGameInfoException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTerritoryException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

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

	public void connect() throws RemoteException, MalformedURLException, NotBoundException {
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

	private void checkConnection() throws RemoteException {
		if (!this.isConnected())
			throw new RemoteException("ServerAdapter: Proxy not connected");
	}

	public UUID createSession(String login, String passwd, Remote callback)
			throws RemoteException, WrongLoginException {
		this.checkConnection();
		UUID ret = null;
		try {
			ret = mProxy.loginUser(login, passwd, callback);
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
		return ret;
	}

	public void closeSession(Session session)
			throws RemoteException, InvalidSessionException {
		this.checkConnection();
		try {
			mProxy.logoutUser(session.getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void registerUser(String login, String passwd, String email)
			throws RemoteException, UserAlreadyExistsException {
		this.checkConnection();
		try {
			mProxy.registerUser(login, passwd, email);
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public ArrayList<GameInfo> fetchGameList(Session session)
			throws RemoteException, InvalidSessionException {
		this.checkConnection();
		ArrayList<GameInfo> ret;
		try {
			ret = mProxy.listGames(session.getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
		return ret;
	}

	public void createGame(Session session, GameInfo game)
			throws RemoteException, InvalidGameInfoException, InvalidSessionException {
		this.checkConnection();
		try {
			mProxy.createGame(session.getId(), game);
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void joinGame(Session session, GameInfo game)
			throws RemoteException, FullGameException, GameNotFoundException, InvalidSessionException, AlreadyInGameException {
		this.checkConnection();
		try {
			mProxy.joinGame(session.getId(), game.getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public Game playGame(Session session, GameInfo game)
			throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException, AlreadyInGameException {
		this.checkConnection();
		Game ret;
		try {
			ret = mProxy.playGame(session.getId(), game.getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
		return ret;
	}

	public void quitGame(Session session, Game game)
			throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException {
		this.checkConnection();
		try {
			mProxy.quitGame(session.getId(), game.getGameInfo().getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void resignGame(Session session, Game game)
			throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException {
		this.checkConnection();
		try {
			mProxy.resignGame(session.getId(), game.getGameInfo().getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void attackTerritory(Session session, Game game, Attack currentAttack)
			throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTerritoryException, InvalidTimeException {
		this.checkConnection();
		try {
			mProxy.attackTerritory(
				session.getId(),
				game.getGameInfo().getId(),
				currentAttack.getOrigin().getDecoratedTerritory(),
				currentAttack.getDestination().getDecoratedTerritory(),
				currentAttack.getArsenal());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void acceptAttack(Session session, Game game)
			throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException {
		this.checkConnection();
		try {
			mProxy.acceptAttack(session.getId(), game.getGameInfo().getId());
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void requestNegotiation(Session session, Game game, int money, int soldiers)
			throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException {
		this.checkConnection();
		try {
			mProxy.requestedNegotiation(session.getId(),
				game.getGameInfo().getId(), money, soldiers);
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

	public void updateGame(Session session, Game game, ArrayList<Player> playerUpdate, ArrayList<TerritoryDecorator> territoryUpdate, EventType event)
			throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException {
		this.checkConnection();
		try {
			final ArrayList<Territory> territoryList = new ArrayList<Territory>();
			if (territoryUpdate != null) {
				for (final TerritoryDecorator t : territoryUpdate) {
					territoryList.add(t.getDecoratedTerritory());
				}
			}
			mProxy.updateGame(session.getId(), game.getGameInfo().getId(),
				playerUpdate, territoryList, event);
		} catch (final RemoteException e) {
			this.disconnect();
			throw e;
		}
	}

}
