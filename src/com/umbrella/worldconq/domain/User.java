package com.umbrella.worldconq.domain;

import java.util.UUID;

public class User {

	private UUID ID;
	private String Name;
	private String Email;

	public User(UUID id, String name, String email) throws ExceptionEmail {
		setID(id);
		setName(name);
		setEmail(email);
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

	public void setEmail(String email) throws ExceptionEmail {
		if (validateEmail(email)) {
			Email = email;
		} else {
			throw new ExceptionEmail("Email mal formado");
		}
	}

	public String toString() {
		return getID() + "," + getName() + "," + getEmail();
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
