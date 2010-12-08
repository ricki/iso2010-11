package com.umbrella.worldconq;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.ui.MainWindow;
import com.umbrella.worldconq.ui.StartupWindow;

public class WorldConqApp {

	public static WorldConqApp wcApp = new WorldConqApp();

	private static UserManager mUserManager = null;
	private static GameManager mGameManager = null;

	private static StartupWindow mStartupWindow = null;
	private static MainWindow mMainWindow = null;

	private static ServerAdapter mServerAdapter = null;

	public static UserManager getUserManager() {
		if (mUserManager == null) mUserManager = new UserManager();
		return mUserManager;
	}

	public static GameManager getGameManager() {
		if (mGameManager == null) mGameManager = new GameManager();
		return mGameManager;
	}

	public static ServerAdapter getServerAdapter() {
		if (mServerAdapter == null) mServerAdapter = new ServerAdapter();
		return mServerAdapter;
	}

	public static StartupWindow getStartupWindow() {
		if (mStartupWindow == null) mStartupWindow = new StartupWindow();
		return mStartupWindow;
	}

	public static MainWindow getMainWindow() {
		if (mMainWindow == null) mMainWindow = new MainWindow();
		return mMainWindow;
	}

}
