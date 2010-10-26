package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.comm.ServerProxy;
import com.umbrella.worldconq.domain.Session;


public class UserManager {
	
	private Session Session;
	
	public UserManager(){
		 Session = new Session();
	}
	
	public void registerUser(String Login, String Passwd, String Email) {
		ServerProxy.getServerProxy().registerUser(Login, Passwd, Email);
	}

	public void validateUser(String Login, String Passwd){
		String id_session = ServerProxy.getServerProxy().validateUser(Login, Passwd);
		Session.setSessID(id_session);
	}

	public void closeSession() {
		throw new UnsupportedOperationException();
	}
}
