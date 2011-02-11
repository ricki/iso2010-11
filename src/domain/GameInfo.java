package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 2499820007479700300L;
	private UUID id;
	private String name;
	private String description;
	private ArrayList<String> players;
	private ArrayList<Calendar> gameSessions;
	private int nFreeTerritories;
	private int turnTime;
	private int defenseTime;
	private int negotiationTime;

	public GameInfo() {
		id = null;
		name = null;
		description = null;
		players = null;
		gameSessions = null;
		nFreeTerritories = 0;
		turnTime = 0;
		defenseTime = 0;
		negotiationTime = 0;
	}

	public GameInfo(UUID id, String name, String description,
			ArrayList<String> players, ArrayList<Calendar> gameSessions,
			int nFreeTerritories, int turnTime, int defenseTime,
			int negotiationTime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.players = players;
		this.gameSessions = gameSessions;
		this.nFreeTerritories = nFreeTerritories;
		this.turnTime = turnTime;
		this.defenseTime = defenseTime;
		this.negotiationTime = negotiationTime;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<String> players) {
		this.players = players;
	}

	public ArrayList<Calendar> getGameSessions() {
		return gameSessions;
	}

	public void setGameSessions(ArrayList<Calendar> gameSessions) {
		this.gameSessions = gameSessions;
	}

	public int getnFreeTerritories() {
		return nFreeTerritories;
	}

	public void setnFreeTerritories(int nFreeTerritories) {
		this.nFreeTerritories = nFreeTerritories;
	}

	public int getTurnTime() {
		return turnTime;
	}

	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}

	public int getDefenseTime() {
		return defenseTime;
	}

	public void setDefenseTime(int defenseTime) {
		this.defenseTime = defenseTime;
	}

	public int getNegotiationTime() {
		return negotiationTime;
	}

	public void setNegotiationTime(int negotiationTime) {
		this.negotiationTime = negotiationTime;
	}
    
	public boolean equals(Object o) {
		if (o==null) return false;
		if (!(o instanceof GameInfo)) return false;
		GameInfo p=(GameInfo) o;
		return id.equals(p.getId());
	}
}
