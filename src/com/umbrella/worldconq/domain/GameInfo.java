package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GameInfo {

	private UUID mId;
	private String mName;
	private String mDescription;
	private ArrayList<Player> mPlayers;
	private ArrayList<Calendar> mGameSessions;
	private int mOpenTerritories;

	public GameInfo(UUID id, String name, String description,
			ArrayList<Player> players, ArrayList<Calendar> gameSessions,
			int openTerritories) {
		setId(id);
		setName(name);
		setDescription(description);
		setOpenTerritories(openTerritories);
		setPlayers(players);
		setGameSessions(gameSessions);
	}
	
	public GameInfo(String name, String description, ArrayList<Calendar> gameSessions) {
		setName(name);
		setDescription(description);
		setGameSessions(gameSessions);
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

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public ArrayList<Player> getPlayers() {
		return mPlayers;
	}

	public void setPlayers(ArrayList<Player> players) {
		mPlayers = players;
	}

	public ArrayList<Calendar> getGameSessions() {
		return mGameSessions;
	}

	public void setGameSessions(ArrayList<Calendar> gameSessions) {
		mGameSessions = gameSessions;
	}

	public int getOpenTerritories() {
		return mOpenTerritories;
	}

	public void setOpenTerritories(int openTerritories) {
		mOpenTerritories = openTerritories;
	}

	public String toString() {
		return getId() + "," + getName() + "," + getDescription() + ","
				+ getPlayers().toString() + "," + getGameSessions().toString() + ","
				+ getOpenTerritories();
	}

}
