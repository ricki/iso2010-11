package es.uclm.iso2.rmi;

import java.io.Serializable;

public class Spy implements Serializable {

	private static final long serialVersionUID = -8653528775818114475L;

	private int uses;
	private Territory location; // Association

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public Territory getLocation() {
		return location;
	}

	public void setLocation(Territory location) {
		this.location = location;
	}

}
