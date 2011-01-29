package com.umbrella.worldconq.domain;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.exception.InvalidArgumentException;

public class UserManager {

	private final WorldConqApp app;
	private Session mSession;
	private final String emailReEx = "^[A-Za-z0-9_%-]+@[A-Za-z0-9_%-]+.[A-Za-z0-9_%-]{2,4}$";

	public UserManager() {
		app = WorldConqApp.getWorldConqApp();
		mSession = null;
	}

	public Session getSession() {
		return mSession;
	}

	public void registerUser(String login, String passwd, String email) throws InvalidArgumentException, Exception {
		if (login == null || passwd == null || email == null ||
				login.equals("") || passwd.equals("") || email.equals("")) {
			throw new InvalidArgumentException();
		} else {
			final Pattern p = Pattern.compile(emailReEx);
			final Matcher m = p.matcher(email);
			if (m.find())
				app.getServerAdapter().registerUser(login, passwd, email);
			else
				throw new InvalidArgumentException();
		}
	}

	public void createSession(String login, String passwd) throws Exception {
		if (login == null || passwd == null || login.equals("")
				|| passwd.equals(""))
			throw new InvalidArgumentException();
		else {
			if (mSession != null) this.closeSession();
			final UUID id = app.getServerAdapter().createSession(login, passwd);
			mSession = new Session(id, login);
			app.setMainMode();
		}
	}

	public void closeSession() throws Exception {
		app.getServerAdapter().closeSession(mSession);
		mSession = null;
	}
}
