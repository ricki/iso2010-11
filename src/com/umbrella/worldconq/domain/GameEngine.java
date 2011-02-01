package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.comm.ServerAdapter;

import es.uclm.iso2.rmi.Game;

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

		this.setMapListModel(new MapModel(mSession));
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
