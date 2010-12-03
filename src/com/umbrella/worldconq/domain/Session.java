package com.umbrella.worldconq.domain;

import java.util.UUID;

public class Session {
	
	private UUID mId;
	
	public Session(UUID id){
		setId(id);
	}
	
	public void setId(UUID id){
		this.mId = id;
	}
	
	public UUID getSessID(){
		return this.mId;
	}

}
