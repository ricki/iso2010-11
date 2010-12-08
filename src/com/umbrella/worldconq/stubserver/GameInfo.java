package com.umbrella.worldconq.stubserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = -5063690982525576603L;
	
	public UUID ID;
	public String Name;
	public String Description;
	public ArrayList<String> Players;
	public ArrayList<Calendar> GameSessions;
	public int NumeroTerritoriosLibres;

}
