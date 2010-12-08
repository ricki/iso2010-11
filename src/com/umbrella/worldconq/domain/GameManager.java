package com.umbrella.worldconq.domain;

public class GameManager {

	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;

	public GameManager() {
		setCurrentGameListModel(new GameListModel());
		setOpenGameListModel(new GameListModel());
	}

	public void setCurrentGameListModel(GameListModel mCurrentGameListModel) {
		this.mCurrentGameListModel = mCurrentGameListModel;
	}

	public GameListModel getCurrentGameListModel() {
		return mCurrentGameListModel;
	}

	public void setOpenGameListModel(GameListModel mOpenGameListModel) {
		this.mOpenGameListModel = mOpenGameListModel;
	}

	public GameListModel getOpenGameListModel() {
		return mOpenGameListModel;
	}
}
