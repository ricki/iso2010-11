package com.umbrella.worldconq.comm;

import com.umbrella.worldconq.domain.Session;

public class ServerProxy {

	protected static ServerProxy mInstancia = null;
	
	protected ServerProxy(){
		
	}
	
	public static ServerProxy getServerProxy(){
		if (mInstancia == null){
			mInstancia = new ServerProxy();
		}
		
		return mInstancia;
	}
	
	public String validateUser(String Login, String Passwd) {
		System.out.println("Validate user");
		return "valido";
	}

	public boolean registerUser(String Login, String Passwd, String Email) {
		System.out.println("Register user");
		return true;
	}

	public void closeSession(String SessionID) {
		System.out.println("Close session");
	}
	
	
}
