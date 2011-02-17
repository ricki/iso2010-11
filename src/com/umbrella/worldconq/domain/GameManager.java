package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Calendar;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;
import com.umbrella.worldconq.ui.GameEventListener;

import domain.Game;
import domain.GameInfo;

public class GameManager {

	private UserManager usrMgr;
	private final ServerAdapter srvAdapter;
	private final ClientAdapter cltAdapter;

	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;
	private GameEngine mGameEngine;

	public GameManager(ServerAdapter srvAdapter, ClientAdapter cltAdapter) {
		usrMgr = null;
		this.srvAdapter = srvAdapter;
		this.cltAdapter = cltAdapter;
		this.setCurrentGameListModel(new GameListModel());
		this.setOpenGameListModel(new GameListModel());
	}

	public UserManager getUserManager() {
		return usrMgr;
	}

	public void setUserManager(UserManager usrMgr) {
		this.usrMgr = usrMgr;
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
		final ArrayList<GameInfo> fullList = srvAdapter.fetchGameList(usrMgr.getSession());
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

	public void createGame(String name, String description, ArrayList<Calendar> gameSessions, int turnTime, int defTime, int negTime) throws Exception {

		if (name == null || gameSessions == null || description == null ||
				name.isEmpty() || turnTime < 0 || defTime < 0 || negTime < 0) throw new InvalidArgumentException();
		for (final Calendar c : gameSessions) {
			if (c == null || c.before(Calendar.getInstance())) throw new InvalidArgumentException();
		}
		srvAdapter.createGame(usrMgr.getSession(), new GameInfo(null, name,
			description, null, gameSessions, 0, turnTime, defTime, negTime));
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

	public void connectToGame(int gameIndex, GameEventListener gameListener) throws Exception {
		final GameInfo gameUuid = mCurrentGameListModel.getGameAt(gameIndex);
		final Session session = usrMgr.getSession();
		final Game game = srvAdapter.playGame(session, gameUuid);
		mGameEngine = new GameEngine(game, session, srvAdapter, gameListener);
		cltAdapter.setCallback(mGameEngine);
	}

	public GameEngine getGameEngine() {
		return mGameEngine;
	}

	public void disconnectFromGame() throws Exception {
		srvAdapter.quitGame(usrMgr.getSession(), mGameEngine.getGame());
		mGameEngine = null;
	}
}
