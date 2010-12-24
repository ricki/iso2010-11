package com.umbrella.worldconq;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.ui.MainWindow;
import com.umbrella.worldconq.ui.StartupWindow;

public class WorldConqApp {

	public static WorldConqApp mInstace = null;

	private UserManager mUserManager = null;
	private GameManager mGameManager = null;

	private StartupWindow mStartupWindow = null;
	private MainWindow mMainWindow = null;

	private ServerAdapter mServerAdapter = null;

	public UserManager getUserManager() {
		if (mUserManager == null) mUserManager = new UserManager();
		return mUserManager;
	}

	public GameManager getGameManager() {
		if (mGameManager == null) mGameManager = new GameManager();
		return mGameManager;
	}

	public ServerAdapter getServerAdapter() {
		if (mServerAdapter == null) mServerAdapter = new ServerAdapter();
		return mServerAdapter;
	}

	public StartupWindow getStartupWindow() {
		if (mStartupWindow == null) mStartupWindow = new StartupWindow();
		return mStartupWindow;
	}

	public MainWindow getMainWindow() {
		if (mMainWindow == null) mMainWindow = new MainWindow();
		return mMainWindow;
	}

}
