package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GameInfo {

	private UUID ID;
	private String Name;
	private String Description;
	private ArrayList<User> User;
	private ArrayList<Calendar> TimeGame;
	private int OpenTerritories;

	public GameInfo() {
		ID = null;
		Name = "";
		Description = "";
		OpenTerritories = 0;
		User = null;
		TimeGame = null;
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

	public ArrayList<User> getUser() {
		return User;
	}

	public void setUser(ArrayList<User> user) {
		User = user;
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

}
