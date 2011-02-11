package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Game implements Serializable {

	private static final long serialVersionUID = 9028688712200020470L;

	private GameInfo gameInfo;
	private ArrayList<Territory> map;
	private ArrayList<Player> players;

	public Game() {

	}

	public Game(GameInfo gameInfo, ArrayList<Territory> map,
			ArrayList<Player> players) {
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

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Game))
			return false;
		Game p = (Game) o;
		return gameInfo.equals(p.getGameInfo());
	}

	public Player strToPlayer(String name, Game g) {

		ArrayList<Player> pl = g.getPlayers();
		Player p;

		for (Iterator<Player> iterator = pl.iterator(); iterator.hasNext();) {

			p = iterator.next();
			if (p.getName().equals(name))
				return p;

		}
		return null;

	}
}
