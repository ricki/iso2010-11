package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import com.umbrella.worldconq.comm.ServerAdapter;

import es.uclm.iso2.rmi.Game;
import es.uclm.iso2.rmi.Player;
import es.uclm.iso2.rmi.Spy;
import es.uclm.iso2.rmi.Territory;

public class GameEngine {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;

	public GameEngine(Game game, Session session, ServerAdapter adapter) {
		this.setMapListModel(new MapModel());
		this.setPlayerListModel(new PlayerListModel());

		final ArrayList<Territory> fullList = game.getMap();
		final ArrayList<Territory> finalList = new ArrayList<Territory>();
		Player user = null;
		for (final Territory t : fullList) {
			if (t.getOwner() != null
					&& t.getOwner().getName().equals(session.getUser())) {
				finalList.add(t);
				user = t.getOwner();
			} else {
				final Player p = new Player();
				p.setName("¿?");
				final int[] ca = {
						-1, -1, -1
				};
				finalList.add(new Territory(t.getIdTerritory(),
					t.getContinent(), p, -1, ca, -1, -1, -1));
			}
		}
		if (user != null) {
			for (final Spy s : user.getSpies()) {
				finalList.add(TerritoryData.getIndex(s.getLocation()),
					s.getLocation());
			}
		}
		mMapListModel.setData(finalList);

		final ArrayList<Player> playerList = game.getPlayers();
		mPlayerListModel.setData(playerList);
	}

	public void setMapListModel(MapModel mMapListModel) {
		this.mMapListModel = mMapListModel;
	}

	public MapModel getMapListModel() {
		return mMapListModel;
	}

	public void setPlayerListModel(PlayerListModel mPlayerListModel) {
		this.mPlayerListModel = mPlayerListModel;
	}

	public PlayerListModel getPlayerListModel() {
		return mPlayerListModel;
	}
}
