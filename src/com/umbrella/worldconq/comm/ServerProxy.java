package com.umbrella.worldconq.comm;

import com.umbrella.worldconq.domain.Session;

public class ServerProxy {

	protected static ServerProxy mInstance = null;
	
	protected ServerProxy(){
		
	}
	
	public static ServerProxy getServerProxy(){
		if (mInstance == null) {
			mInstance = new ServerProxy();
		}
		
		return mInstance;
	}
	
	public String validateUser(String Login, String Passwd) {
		System.out.println("ServerProxy::validateUser " + Login +" " + Passwd );
		return "1234";
	}

	public boolean registerUser(String Login, String Passwd, String Email) {
		System.out.println("ServerProxy::registerUser " + Login +" " + Passwd + " "+ Email);
		return true;
	}

	public void closeSession(String SessionID) {
		System.out.println("Close session");
	}
	
	
}
