package com.umbrella.worldconq.domain;

import java.util.UUID;

public class User {

	private UUID mId;
	private String mName;
	private String mEmail;

	public User(UUID id, String name, String email) throws MalformedEMailException {
		setId(id);
		setName(name);
		setEmail(email);
	}

	public UUID getId() {
		return mId;
	}

	public void setId(UUID id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) throws MalformedEMailException {
		if (validateEmail(email)) {
			mEmail = email;
		} else {
			throw new MalformedEMailException(email + " is not a valid eMail.");
		}
	}

	public String toString() {
		return getId() + "," + getName() + "," + getEmail();
	}

	private boolean validateEmail(String email) {
		boolean ret = true;
		// compruebo que al menos tenga una arroba y un punto
		if (email.length() == 0) {
			ret = false;
		} else {
			String[] arroba = new String[email.split("@").length];
			for (int i = 0; i < arroba.length; i++) {
				arroba[i] = email.split("@")[i];
			}
			if (arroba.length == 2) { // contiene arroba
				String[] punto = new String[arroba[1].split("\\.").length];
				for (int j = 0; j < punto.length; j++) {
					punto[j] = arroba[1].split("\\.")[j];
				}
				if (punto.length == 1) { // contiene punto
					ret = false;
				}
			} else {
				ret = false;
			}
		}
		return ret;
	}

}
