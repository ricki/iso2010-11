package com.umbrella.worldconq.comm;

import java.util.UUID;

public class ServerAdapter {

	protected static ServerAdapter mInstance = null;
	
	protected ServerAdapter(){
		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());
	}
	
	public static ServerAdapter getServerAdapter(){
		if (mInstance == null) {
			mInstance = new ServerAdapter();
		}
		
		return mInstance;
	}
	
	public UUID validateUser(String Login, String Passwd) {
		System.out.println("ServerProxy::validateUser " + Login +" " + Passwd );
		return UUID.randomUUID();
	}

	public boolean registerUser(String Login, String Passwd, String Email) {
		System.out.println("ServerProxy::registerUser " + Login +" " + Passwd + " "+ Email);
		return true;
	}

	public void closeSession(UUID sessId) {
		System.out.println("Close session");
	}
	
	
}
