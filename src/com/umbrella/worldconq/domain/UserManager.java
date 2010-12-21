package com.umbrella.worldconq.domain;

import java.util.UUID;

import com.umbrella.worldconq.WorldConqApp;


public class UserManager {
	
	private Session mSession;
	private User mUser;
	
	public UserManager(){
		 mSession = null;
		 setmUser(null);
	}
	
	public void registerUser(String Login, String Passwd, String Email) throws Exception {
		WorldConqApp.getServerAdapter().registerUser(Login, Passwd, Email);
	}

	public void createSession(String Login, String Passwd) throws Exception {
		// TODO Comprobar si hay sesión activa y cerrarla antes.
		mSession = WorldConqApp.getServerAdapter().createSession(Login, Passwd);
		// TODO Poner el email y el uuid correcto
		setmUser(new User(UUID.randomUUID(), Login, "asdf@asfd.adsf"));
	}

	public void closeSession() throws Exception {
		WorldConqApp.getServerAdapter().closeSession(mSession);
		mSession = null;
	}
	
	public Session getActiveSession() {
		return mSession;
	}

	public void setmUser(User mUser) {
		this.mUser = mUser;
	}

	public User getmUser() {
		return mUser;
	}
}
