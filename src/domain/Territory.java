package domain;

import java.io.Serializable;

import persistence.PersistentObj;

public class Territory implements Serializable, PersistentObj {

	private static final long serialVersionUID = 4177888547129389571L;

	public enum Continent {
		Europe,
		Asia,
		Africa,
		NorthAmerica,
		SouthAmerica,
		Oceania
	}

	private int idTerritory;
	private Continent continent;
	private String owner;
	private int numSoldiers;
	private int numCannons[] = new int[3]; // Cannons with one, two or three
	private int numMissiles;
	private int numICBMs; // Intercontinental Ballistic Missiles
	private int numAntiMissiles;

	public Territory(int id, Continent cont, String owner, int sold,
			int[] cannons, int missiles, int icbms, int antimissiles) {
		this.setIdTerritory(id);
		this.setContinent(cont);
		this.setNumSoldiers(sold);
		this.setOwner(owner);
		this.setNumCannons(cannons);
		this.setNumMissiles(missiles);
		this.setNumICBMs(icbms);
		this.setNumAntiMissiles(antimissiles);
	}

	public int getIdTerritory() {
		return idTerritory;
	}

	public void setIdTerritory(int idTerritory) {
		this.idTerritory = idTerritory;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	public int getNumSoldiers() {
		return numSoldiers;
	}

	public void setNumSoldiers(int numSoldiers) {
		this.numSoldiers = numSoldiers;
	}

	public int[] getNumCannons() {
		return numCannons;
	}

	public void setNumCannons(int[] numCannons) {
		this.numCannons = numCannons;
	}

	public int getNumMissiles() {
		return numMissiles;
	}

	public void setNumMissiles(int numMissiles) {
		this.numMissiles = numMissiles;
	}

	public int getNumICBMs() {
		return numICBMs;
	}

	public void setNumICBMs(int numICBMs) {
		this.numICBMs = numICBMs;
	}

	public int getNumAntiMissiles() {
		return numAntiMissiles;
	}

	public void setNumAntiMissiles(int numAntiMissiles) {
		this.numAntiMissiles = numAntiMissiles;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Territory))
			return false;
		final Territory p = (Territory) o;
		return idTerritory == p.getIdTerritory();

	}

}
