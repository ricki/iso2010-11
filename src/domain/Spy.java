package domain;

import java.io.Serializable;

import persistence.PersistentObj;

public class Spy implements Serializable, PersistentObj {

	private static final long serialVersionUID = -8653528775818114475L;

	private int uses;
	private int location; // Association
	
	public Spy(int territory) {
		this.setLocation(territory);
	}

	public Spy(int territory, int uses) {
		this.setLocation(territory);
		this.setUses(uses);
	}
	
	
	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Spy))
			return false;
		Spy p = (Spy) o;
		
		return this.getLocation()==p.getLocation();
	}

}
