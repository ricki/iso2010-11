package com.umbrella.worldconq.domain;

import java.util.UUID;

import com.umbrella.worldconq.comm.ServerProxy;
import com.umbrella.worldconq.domain.Session;


public class UserManager {
	
	private Session mSession;
	
	public UserManager(){
		 mSession = null;
	}
	
	public void registerUser(String Login, String Passwd, String Email) {
		ServerProxy.getServerProxy().registerUser(Login, Passwd, Email);
	}

	public void createSession(String Login, String Passwd){
		// TODO Comprobar si hay sesión activa y cerrarla antes.
		UUID id = ServerProxy.getServerProxy().validateUser(Login, Passwd);
		mSession = new Session(id);
	}

	public void closeSession() {
		ServerProxy.getServerProxy().closeSession(mSession.getId());
		mSession = null;
	}
	
	public Session getActiveSession() {
		return mSession;
	}
}
