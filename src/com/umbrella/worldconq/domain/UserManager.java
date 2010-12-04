package com.umbrella.worldconq.domain;

import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.Session;


public class UserManager {
	
	private Session mSession;
	
	public UserManager(){
		 mSession = null;
	}
	
	public void registerUser(String Login, String Passwd, String Email) {
		ServerAdapter.getServerAdapter().registerUser(Login, Passwd, Email);
	}

	public void createSession(String Login, String Passwd){
		// TODO Comprobar si hay sesión activa y cerrarla antes.
		UUID id = ServerAdapter.getServerAdapter().validateUser(Login, Passwd);
		mSession = new Session(id);
	}

	public void closeSession() {
		ServerAdapter.getServerAdapter().closeSession(mSession.getId());
		mSession = null;
	}
	
	public Session getActiveSession() {
		return mSession;
	}
}
