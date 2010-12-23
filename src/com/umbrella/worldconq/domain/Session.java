package com.umbrella.worldconq.domain;

import java.util.UUID;

public class Session {
	
	private UUID mId;
	private String mUser;
	
	public Session(UUID id){
		setId(id);
		setUser(null);
	}
	
	public void setId(UUID id){
		this.mId = id;
	}
	
	public UUID getId(){
		return this.mId;
	}


	public void setUser(String mUser) {
		this.mUser = mUser;
	}

	public String getUser() {
		return mUser;
	}

}
