package com.umbrella.worldconq.domain;

import java.util.UUID;

import com.umbrella.worldconq.WorldConqApp;

public class UserManager {

	private final WorldConqApp app;
	private Session mSession;

	public UserManager() {
		app = WorldConqApp.getWorldConqApp();
		mSession = null;
	}

	public Session getSession() {
		return mSession;
	}

	public void registerUser(String login, String passwd, String email) throws Exception {
		app.getServerAdapter().registerUser(login, passwd, email);
	}

	public void createSession(String login, String passwd) throws Exception {
		if (mSession != null) this.closeSession();
		final UUID id = app.getServerAdapter().createSession(login, passwd);
		mSession = new Session(id, login);
		app.setMainMode();
	}

	public void closeSession() throws Exception {
		app.getServerAdapter().closeSession(mSession);
		mSession = null;
	}
}
