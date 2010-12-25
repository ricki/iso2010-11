package com.umbrella.worldconq.domain;

import java.util.UUID;

public class Session {

	private UUID mId;
	private String mUser;

	public Session(UUID id, String user) {
		this.setId(id);
		this.setUser(user);
	}

	public void setId(UUID id) {
		mId = id;
	}

	public UUID getId() {
		return mId;
	}

	public void setUser(String mUser) {
		this.mUser = mUser;
	}

	public String getUser() {
		return mUser;
	}

}
