package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;

import com.umbrella.worldconq.WorldConqApp;

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

	public void updateGameList() throws Exception {
		// FIXME : Implementación temporal
		ArrayList<GameInfo> l = WorldConqApp.getServerAdapter().fetchGameList();
		mCurrentGameListModel.setData(l);
		mOpenGameListModel.setData(l);
	}
	
	public static void createGame(	String mName, String mDescription,
			ArrayList<Calendar> mGameSessions) throws Exception{
			WorldConqApp.getServerAdapter().createGame(new GameInfo(mName, mDescription, 
					mGameSessions));
	}
}
