package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GameInfo {

	private UUID ID;
	private String Name;
	private String Description;
	private ArrayList<Player> Players;
	private ArrayList<Calendar> TimeGame;
	private int OpenTerritories;

	public GameInfo(UUID id, String name, String description,
			ArrayList<Player> players, ArrayList<Calendar> timeGame,
			int openTerritories) {
		setID(id);
		setName(name);
		setDescription(description);
		setOpenTerritories(openTerritories);
		setPlayers(players);
		setTimeGame(timeGame);
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

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public ArrayList<Player> getPlayers() {
		return Players;
	}

	public void setPlayers(ArrayList<Player> players) {
		Players = players;
	}

	public ArrayList<Calendar> getTimeGame() {
		return TimeGame;
	}

	public void setTimeGame(ArrayList<Calendar> timeGame) {
		TimeGame = timeGame;
	}

	public int getOpenTerritories() {
		return OpenTerritories;
	}

	public void setOpenTerritories(int openTerritories) {
		OpenTerritories = openTerritories;
	}

	public String toString() {
		return getID() + "," + getName() + "," + getDescription() + ","
				+ getPlayers().toString() + "," + getTimeGame().toString() + ","
				+ getOpenTerritories();
	}

}
