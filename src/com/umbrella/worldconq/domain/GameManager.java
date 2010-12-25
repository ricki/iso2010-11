package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;

import com.umbrella.worldconq.WorldConqApp;

import es.uclm.iso2.rmi.GameInfo;

public class GameManager {

	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;

	public GameManager() {
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
		final String user = WorldConqApp.getWorldConqApp().getUserManager().getSession().getUser();
		final ArrayList<GameInfo> l = WorldConqApp.getWorldConqApp().getServerAdapter().fetchGameList();

		final ArrayList<GameInfo> listPlayer = new ArrayList<GameInfo>();
		final ArrayList<GameInfo> listOpen = new ArrayList<GameInfo>();
		int countPlayers = 0;
		for (int i = 0; i < l.size(); i++) {
			for (int j = 0; j < l.get(i).getPlayers().size(); j++) {
				if (user.equals(l.get(i).getPlayers().get(j))) {
					listPlayer.add(l.get(i));
				} else {
					countPlayers++;
				}

				if ((countPlayers == l.get(i).getPlayers().size())
						&& l.get(i).getnFreeTerritories() > 0) {
					listOpen.add(l.get(i));
				}

			}
			countPlayers = 0;
		}

		mCurrentGameListModel.setData(listPlayer);
		mOpenGameListModel.setData(listOpen);
	}

	public static void createGame(String mName, String mDescription,
			ArrayList<Calendar> mGameSessions) throws Exception {
		WorldConqApp.getWorldConqApp().getServerAdapter().createGame(
			new GameInfo(null, mName,
				mDescription,
				null, mGameSessions, 0, 0, 0, 0));
	}
}
