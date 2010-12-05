package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.comm.ServerAdapter;


public class UserManager {
	
	private Session mSession;
	
	public UserManager(){
		 mSession = null;
	}
	
	public void registerUser(String Login, String Passwd, String Email) throws Exception {
		ServerAdapter.getServerAdapter().registerUser(Login, Passwd, Email);
	}

	public void createSession(String Login, String Passwd) throws Exception {
		// TODO Comprobar si hay sesión activa y cerrarla antes.
		mSession = ServerAdapter.getServerAdapter().createSession(Login, Passwd);
	}

	public void closeSession() throws Exception {
		ServerAdapter.getServerAdapter().closeSession(mSession);
		mSession = null;
	}
	
	public Session getActiveSession() {
		return mSession;
	}
}
