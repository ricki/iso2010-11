package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import com.umbrella.worldconq.WorldConqApp;

import es.uclm.iso2.rmi.GameInfo;
import es.uclm.iso2.rmi.Territory;

public class GameEngine {
	private final WorldConqApp app;
	private MapModel mMapListModel;

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
		final ArrayList<Territory> list = app.getServerAdapter().playGame(sess,
			game).getMap();

		mMapListModel.setData(list);
	}
}
