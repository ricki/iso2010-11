package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;

import es.uclm.iso2.rmi.GameInfo;

public class GameManager {

	private final UserManager usrMgr;
	private final ServerAdapter srvAdapter;

	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;

	public GameManager(UserManager usrMgr, ServerAdapter srvAdapter) {
		this.usrMgr = usrMgr;
		this.srvAdapter = srvAdapter;
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
		final String user = usrMgr.getSession().getUser();
		final ArrayList<GameInfo> fullList = srvAdapter.fetchGameList();
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
		srvAdapter.createGame(new GameInfo(null, name,
			description, null, gameSessions, 0, 0, 0, 0));
	}

	public void joinGame(int gameSelected) throws Exception {
		if (gameSelected > mOpenGameListModel.getRowCount()) {
			throw new InvalidArgumentException();
		} else {
			final GameInfo gameUuid = mOpenGameListModel.getGameAt(gameSelected);
			final Session user = usrMgr.getSession();
			srvAdapter.joinGame(user, gameUuid);
		}
	}
}
