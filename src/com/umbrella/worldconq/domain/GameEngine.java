package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import com.umbrella.worldconq.WorldConqApp;

import es.uclm.iso2.rmi.GameInfo;
import es.uclm.iso2.rmi.Player;
import es.uclm.iso2.rmi.Spy;
import es.uclm.iso2.rmi.Territory;

public class GameEngine {
	private final WorldConqApp app;
	private MapModel mMapListModel;
	private String mNamePlayerTurn;
	private ArrayList<String> mNamePlayerOnline;

	public GameEngine() {
		app = WorldConqApp.getWorldConqApp();
		this.setMapListModel(new MapModel());
	}

	public void setMapListModel(MapModel mMapListModel) {
		this.mMapListModel = mMapListModel;
	}

	public MapModel getMapListModel() {
		return mMapListModel;
	}

	public void updatePlay(GameInfo game) throws Exception {
		final Session sess = app.getUserManager().getSession();
		final ArrayList<Territory> fullList = app.getServerAdapter().playGame(
			sess, game).getMap();
		final ArrayList<Territory> finalList = new ArrayList<Territory>();
		Player user = null;
		for (final Territory t : fullList) {
			if (t.getOwner() != null
					&& t.getOwner().getName().equals(sess.getUser())) {
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
		final ArrayList<Player> playerList = app.getServerAdapter().playGame(
			sess, game).getPlayers();
		int numPlayer = -1;
		mNamePlayerOnline = new ArrayList<String>();
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).isHasTurn() == true) {
				numPlayer = i;
			}
			if (playerList.get(i).isOnline() == true) {
				mNamePlayerOnline.add(playerList.get(i).getName());
			}
		}
		mNamePlayerTurn = app.getServerAdapter().playGame(sess, game).getPlayers().get(
			numPlayer).getName();
	}

	public String getNamePlayerTurn() {
		return mNamePlayerTurn;
	}

	public ArrayList<String> getNamePlayerOnline() {
		return mNamePlayerOnline;
	}

}
