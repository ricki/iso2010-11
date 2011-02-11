package com.umbrella.worldconq.domain;

import com.umbrella.worldconq.comm.ServerAdapter;

import domain.Game;

public class GameEngine {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;
	private final Game mGame;
	private final Session session;
	private final ServerAdapter adapter;

	public GameEngine(Game game, Session session, ServerAdapter adapter) {
		mGame = game;
		this.session = session;
		this.adapter = adapter;

		this.setMapListModel(new MapModel(game.strToPlayer(
			this.session.getUser(),
			game)));
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
