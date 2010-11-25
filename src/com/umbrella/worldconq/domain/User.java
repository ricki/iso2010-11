package com.umbrella.worldconq.domain;

import java.util.UUID;

public class User {

	private UUID ID;
	private String Name;
	private String Email;

	public User() {
		ID = null;
		Name = "";
		Email = "";
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID id) {
		ID = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public boolean setEmail(String email) {
		boolean dev = false;
		if (isEmail(email)){
			Email = email;
			dev = true;
		}
		return dev;
	}

	private boolean isEmail(String email) {
		boolean dev = true;
		// compruebo que al menos tenga una arroba y un punto
		if (email.length() == 0) {
			dev = false;
		} else {
			String[] arroba = new String[email.split("@").length];
			for (int i = 0; i < arroba.length; i++) {
				arroba[i] = email.split("@")[i];
			}
			if (arroba.length == 2) {	// contiene arroba
				String[] punto = new String[arroba[1].split("\\.").length];
				for (int j = 0; j < punto.length; j++) {
					punto[j] = arroba[1].split("\\.")[j];
				}
				if (punto.length == 1) {	// contiene punto
					dev = false;
				}
			} else {
				dev = false;
			}
		}
		return dev;
	}

}
