package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;

import com.umbrella.worldconq.WorldConqApp;

import es.uclm.iso2.rmi.GameInfo;

public class GameManager {

	private final WorldConqApp app;
	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;
	private GameEngine mGameEngine;

	public GameManager() {
		app = WorldConqApp.getWorldConqApp();
		this.setCurrentGameListModel(new GameListModel());
		this.setOpenGameListModel(new GameListModel());
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
		final String user = app.getUserManager().getSession().getUser();
		final ArrayList<GameInfo> fullList = app.getServerAdapter().fetchGameList();
		final ArrayList<GameInfo> currentList = new ArrayList<GameInfo>();
		final ArrayList<GameInfo> openList = new ArrayList<GameInfo>();

		for (final GameInfo info : fullList) {
			if (info.getPlayers().contains(user)) {
				currentList.add(info);
			} else if (info.getnFreeTerritories() > 0) {
				openList.add(info);
			}
		}

		mCurrentGameListModel.setData(currentList);
		mOpenGameListModel.setData(openList);
	}

	public void createGame(String name, String description, ArrayList<Calendar> gameSessions) throws Exception {
		app.getServerAdapter().createGame(new GameInfo(null, name,
			description, null, gameSessions, 0, 0, 0, 0));
	}

	public void joinGame(int gameIndex) {
		final GameInfo gameUuid = mOpenGameListModel.getGameAt(gameIndex);
		final Session user = app.getUserManager().getSession();
		try {
			app.getServerAdapter().joinGame(user, gameUuid);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectToGame(int gameIndex) throws Exception {
		final GameInfo gameUuid = mCurrentGameListModel.getGameAt(gameIndex);
		//final Session user = app.getUserManager().getSession();
		try {
			//app.getServerAdapter().playGame(user, gameUuid);
			mGameEngine = new GameEngine();
			mGameEngine.updatePlay(gameUuid);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GameEngine getGameEngine() {
		return mGameEngine;
	}
}
