package domain;

import java.io.Serializable;

public class Spy implements Serializable {

	private static final long serialVersionUID = -8653528775818114475L;

	private int uses;
	private Territory location; // Association

	public Spy() {
		super();
		uses = 0;
		location = null;
	}

	public Spy(int uses, Territory location) {
		super();
		this.uses = uses;
		this.location = location;
	}

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
