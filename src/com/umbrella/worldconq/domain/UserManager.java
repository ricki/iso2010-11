package com.umbrella.worldconq.domain;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;

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

	public void registerUser(String login, String passwd, String email) throws Exception {
		if (login == null || passwd == null || email == null ||
				login.isEmpty() || passwd.isEmpty() || email.isEmpty()) {
			throw new InvalidArgumentException();
		} else {
			final Pattern p = Pattern.compile(emailReEx);
			final Matcher m = p.matcher(email);
			if (m.find())
				srvAdapter.registerUser(login, passwd, email);
			else
				throw new InvalidArgumentException();
		}
	}

	public void createSession(String login, String passwd) throws Exception {
		if (login == null || passwd == null || login.isEmpty()
				|| passwd.isEmpty())
			throw new InvalidArgumentException();
		else {
			if (mSession != null) this.closeSession();
			final UUID id = srvAdapter.createSession(login, passwd, cltAdapter);
			mSession = new Session(id, login);
		}
	}

	public void closeSession() throws Exception {
		if (gameMgr.getGameEngine() != null) gameMgr.disconnectFromGame();
		srvAdapter.closeSession(mSession);
		mSession = null;

	}
}
