package com.umbrella.worldconq.domain;

public class Session {
	
	private String SessID;
	
	public Session(){
		this.SessID="";
		
	}
	
	public void setSessID(String id_session){
		this.SessID=id_session;
	}
	
	public String getSessID(){
		return this.SessID;
	}

}
