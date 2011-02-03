package com.umbrella.worldconq.domain;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;

public class UserManager {

	private final ServerAdapter srvAdapter;
	private Session mSession;
	private final String emailReEx = "^[A-Za-z0-9][A-Za-z0-9_%-\\·]*@[A-Za-z0-9][A-Za-z0-9_%-\\.]*\\.[A-Za-z0-9_%-]{2,4}$";

	public UserManager(ServerAdapter srvAdapter) {
		this.srvAdapter = srvAdapter;
		mSession = null;
	}

	public Session getSession() {
		return mSession;
	}

	public void registerUser(String login, String passwd, String email) throws Exception {
		if (login == null || passwd == null || email == null ||
				login.equals("") || passwd.equals("") || email.equals("")) {
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
		if (login == null || passwd == null || login.equals("")
				|| passwd.equals(""))
			throw new InvalidArgumentException();
		else {
			if (mSession != null) this.closeSession();
			final UUID id = srvAdapter.createSession(login, passwd);
			mSession = new Session(id, login);
		}
	}

	public void closeSession() throws Exception {
		srvAdapter.closeSession(mSession);
		mSession = null;
	}
}
