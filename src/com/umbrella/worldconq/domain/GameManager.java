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
		String user = WorldConqApp.getUserManager().getActiveSession().getUser();
		ArrayList<GameInfo> l = WorldConqApp.getServerAdapter().fetchGameList();

		ArrayList<GameInfo> listPlayer = new ArrayList<GameInfo>();
		ArrayList<GameInfo> listOpen = new ArrayList<GameInfo>();
		int countPlayers = 0;
		for (int i = 0; i < l.size(); i++) {
			for (int j = 0; j < l.get(i).getPlayers().size(); j++) {
				if (user.equals(l.get(i).getPlayers().get(j).getUserName())) {
					listPlayer.add(l.get(i));
				} else {
					countPlayers++;
				}

				if ((countPlayers == l.get(i).getPlayers().size())
						&& l.get(i).getOpenTerritories() > 0) {
					listOpen.add(l.get(i));
				}

			}
			countPlayers=0;
		}

		mCurrentGameListModel.setData(listPlayer);
		mOpenGameListModel.setData(listOpen);
	}
	
	public static void createGame(	String mName, String mDescription,
			ArrayList<Calendar> mGameSessions) throws Exception{
			WorldConqApp.getServerAdapter().createGame(new GameInfo(mName, mDescription, 
					mGameSessions));
	}
}
