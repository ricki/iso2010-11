package es.uclm.iso2.rmi;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

	private static final long serialVersionUID = 9028688712200020470L;

	private GameInfo gameInfo;
	private ArrayList<Territory> map;
	private ArrayList<Player> players;

	public Game(GameInfo gameInfo, ArrayList<Territory> map, ArrayList<Player> players) {
		super();
		this.gameInfo = gameInfo;
		this.map = map;
		this.players = players;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public ArrayList<Territory> getMap() {
		return map;
	}

	public void setMap(ArrayList<Territory> map) {
		this.map = map;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
