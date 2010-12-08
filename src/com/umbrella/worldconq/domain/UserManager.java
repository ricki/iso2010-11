package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.WorldConqApp;


public class UserManager {
	
	private Session mSession;
	
	public UserManager(){
		 mSession = null;
	}
	
	public void registerUser(String Login, String Passwd, String Email) throws Exception {
		WorldConqApp.getServerAdapter().registerUser(Login, Passwd, Email);
	}

	public void createSession(String Login, String Passwd) throws Exception {
		// TODO Comprobar si hay sesión activa y cerrarla antes.
		mSession = WorldConqApp.getServerAdapter().createSession(Login, Passwd);
	}

	public void closeSession() throws Exception {
		WorldConqApp.getServerAdapter().closeSession(mSession);
		mSession = null;
	}
	
	public Session getActiveSession() {
		return mSession;
	}
}
