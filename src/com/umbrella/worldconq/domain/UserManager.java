package com.umbrella.worldconq.domain;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.EmptyStringException;
import com.umbrella.worldconq.exceptions.MalformedEmailException;

import exceptions.GameNotFoundException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

public class UserManager {

	private final ServerAdapter srvAdapter;
	private GameManager gameMgr;
	private Session mSession;
	private final ClientAdapter cltAdapter;
	private final String emailReEx = "^[A-Za-z0-9][A-Za-z0-9_%-\\·]*@[A-Za-z0-9][A-Za-z0-9_%-\\.]*\\.[A-Za-z0-9_%-]{2,4}$";

	public UserManager(ServerAdapter srvAdapter) {
		this.srvAdapter = srvAdapter;
		gameMgr = null;
		mSession = null;
		cltAdapter = null;
	}

	public UserManager(ServerAdapter srvAdapter, GameManager gameMgr, ClientAdapter cltAdapter) {
		this.srvAdapter = srvAdapter;
		this.gameMgr = gameMgr;
		mSession = null;
		this.cltAdapter = cltAdapter;
	}

	public void setGameManager(GameManager gameMgr) {
		this.gameMgr = gameMgr;
	}

	public GameManager getGameManager() {
		return gameMgr;
	}

	public Session getSession() {
		return mSession;
	}

	public void registerUser(String login, String passwd, String email) throws RemoteException, UserAlreadyExistsException, MalformedEmailException {
		if (login == null)
			throw new NullPointerException();
		if (login.isEmpty())
			throw new EmptyStringException();
		if (passwd == null)
			throw new NullPointerException();
		if (passwd.isEmpty())
			throw new EmptyStringException();
		if (email == null)
			throw new NullPointerException();
		if (email.isEmpty())
			throw new EmptyStringException();

		final Pattern p = Pattern.compile(emailReEx);
		final Matcher m = p.matcher(email);
		if (!m.find())
			throw new MalformedEmailException();

		srvAdapter.registerUser(login, passwd, email);
	}

	public void createSession(String login, String passwd) throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException, WrongLoginException {
		if (login == null)
			throw new NullPointerException();
		if (passwd == null)
			throw new NullPointerException();
		if (login.isEmpty())
			throw new EmptyStringException();
		if (passwd.isEmpty())
			throw new EmptyStringException();
		if (mSession != null) this.closeSession();
		final UUID id = srvAdapter.createSession(login, passwd, cltAdapter);
		mSession = new Session(id, login);
	}

	public void closeSession() throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException {
		if (gameMgr.getGameEngine() != null) gameMgr.disconnectFromGame();
		srvAdapter.closeSession(mSession);
		mSession = null;

	}
}
