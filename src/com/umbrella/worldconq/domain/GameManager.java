package com.umbrella.worldconq.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.ui.GameEventListener;

import domain.Game;
import domain.GameInfo;
import exceptions.AlreadyInGameException;
import exceptions.FullGameException;
import exceptions.GameNotFoundException;
import exceptions.InvalidGameInfoException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;

public class GameManager {

	private UserManager usrMgr;
	private final ServerAdapter srvAdapter;
	private final ClientAdapter cltAdapter;

	private GameListModel mCurrentGameListModel;
	private GameListModel mOpenGameListModel;
	private GameEngine mGameEngine;

	public GameManager(ServerAdapter srvAdapter, ClientAdapter cltAdapter) {
		if (srvAdapter == null)
			throw new NullPointerException();
		if (cltAdapter == null)
			throw new NullPointerException();

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

	public GameEngine getGameEngine() {
		return mGameEngine;
	}

	public void updateGameList() throws RemoteException, InvalidSessionException {
		final String user = usrMgr.getSession().getUser();
		final ArrayList<GameInfo> fullList = srvAdapter.fetchGameList(usrMgr.getSession());
		final ArrayList<GameInfo> currentList = new ArrayList<GameInfo>();
		final ArrayList<GameInfo> openList = new ArrayList<GameInfo>();
		final Calendar now = Calendar.getInstance();
		final Calendar now2h = Calendar.getInstance();
		now2h.add(Calendar.HOUR, -2);

		for (final GameInfo info : fullList) {
			if (info.getPlayers().contains(user)) {
				for (final Calendar c : info.getGameSessions()) {
					if (c.before(now) && c.after(now2h)) {
						currentList.add(info);
						break;
					}
				}
			} else if (info.getnFreeTerritories() > 0) {
				for (final Calendar c : info.getGameSessions()) {
					if (c.after(now2h)) {
						openList.add(info);
						break;
					}
				}
			}
		}

		mCurrentGameListModel.setData(currentList);
		mOpenGameListModel.setData(openList);
	}

	public void createGame(String name, String description, ArrayList<Calendar> gameSessions, int turnTime, int defTime, int negTime)
			throws RemoteException, InvalidGameInfoException, InvalidSessionException {

		if (name == null)
			throw new NullPointerException();
		if (description == null)
			throw new NullPointerException();
		if (gameSessions == null)
			throw new NullPointerException();
		if (name.isEmpty())
			throw new EmptyStringException();
		if (turnTime <= 0)
			throw new NegativeValueException();
		if (defTime <= 0)
			throw new NegativeValueException();
		if (negTime <= 0)
			throw new NegativeValueException();

		final ArrayList<String> listPlayer = new ArrayList<String>();
		listPlayer.add(usrMgr.getSession().getUser());
		srvAdapter.createGame(usrMgr.getSession(),
			new GameInfo(UUID.randomUUID(), name,
			description, listPlayer, gameSessions, 42, turnTime, defTime,
			negTime));
		this.updateGameList();
	}

	public void joinGame(int gameIndex) throws RemoteException, FullGameException, GameNotFoundException, InvalidSessionException, AlreadyInGameException {
		final GameInfo info = mOpenGameListModel.getGameAt(gameIndex);
		srvAdapter.joinGame(usrMgr.getSession(), info);
	}

	public void connectToGame(int gameIndex, GameEventListener gameListener) throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException, AlreadyInGameException {
		final GameInfo info = mCurrentGameListModel.getGameAt(gameIndex);
		final Session session = usrMgr.getSession();
		final Game game = srvAdapter.playGame(session, info);
		mGameEngine = new GameEngine(game, session, srvAdapter,
			gameListener);
		cltAdapter.setCallback(mGameEngine);
	}

	public void disconnectFromGame() throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, NotCurrentPlayerGameException {
		srvAdapter.quitGame(usrMgr.getSession(), mGameEngine.getGame());
		mGameEngine = null;
	}
}
