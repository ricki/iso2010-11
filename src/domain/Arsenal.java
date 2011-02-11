package domain;

import java.io.Serializable;

public class Arsenal implements Serializable {

	private static final long serialVersionUID = -3764507517594396495L;
	private int soldiers;
	private int cannons;
	private int missiles;
	private int ICBMs;

	public Arsenal() {
		super();
		soldiers = 0;
		cannons = 0;
		missiles = 0;
		ICBMs = 0;
	}

	public Arsenal(int soldiers, int cannons, int missiles, int ICBMs) {
		super();
		this.soldiers = soldiers;
		this.cannons = cannons;
		this.missiles = missiles;
		this.ICBMs = ICBMs;
	}

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

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Arsenal))
			return false;
		Arsenal p = (Arsenal) o;
		if (!(soldiers == p.getSoldiers()))
			return false;
		if (!(cannons == p.getCannons()))
			return false;
		if (!(missiles == p.getMissiles()))
			return false;
		if (!(ICBMs == p.getICBMs()))
			return false;
		return true;
	}

}
