package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.comm.ServerAdapter;

import domain.Game;
import domain.Player;


public class GameEngine {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;
	private final Game mGame;
	private final Session mSession;
	private final ServerAdapter mAdapter;

	public GameEngine(Game game, Session session, ServerAdapter adapter) {
		mGame = game;
		mSession = session;
		mAdapter = adapter;

		// esto se quitara (hasta las rayas)y se sustituria por ultima linea
		//ademas tambien quitar la funcion getMyUser() de MapModel
		final Player p = new Player();
		p.setName(mSession.getUser());
		this.setMapListModel(new MapModel(p));
		//this.setMapListModel(new MapModel(getOwner())); algo asi (funcion de Laura)
		//---------------------------------------------------------------
		this.setPlayerListModel(new PlayerListModel());

		mMapListModel.setData(mGame.getMap());
		mPlayerListModel.setData(mGame.getPlayers());
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
