package es.uclm.iso2.rmi;

import java.io.Serializable;

public class Arsenal implements Serializable {

	private static final long serialVersionUID = -3764507517594396495L;
	private int soldiers;
	private int cannons;
	private int missiles;
	private int ICBMs;

	public int getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getCannons() {
		return cannons;
	}

	public void setCannons(int cannons) {
		this.cannons = cannons;
	}

	public int getMissiles() {
		return missiles;
	}

	public void setMissiles(int missiles) {
		this.missiles = missiles;
	}

	public int getICBMs() {
		return ICBMs;
	}

	public void setICBMs(int iCBMs) {
		ICBMs = iCBMs;
	}

}
