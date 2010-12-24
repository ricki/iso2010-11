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

	private WorldConqApp() {
		mUserManager = null;
		mGameManager = null;
		mStartupWindow = null;
		mMainWindow = null;
		mServerAdapter = null;
	}

	public static WorldConqApp getWorldConqApp() {
		if (mInstace == null) mInstace = new WorldConqApp();
		return mInstace;
	}

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

	public void setStartupMode() {
		if (mMainWindow != null) {
			mMainWindow.setVisible(false);
			mMainWindow.dispose();
			mMainWindow = null;
		}
		this.getStartupWindow().setVisible(true);
	}

	public void freeResources() {
		if (mStartupWindow != null) {
			mStartupWindow.setVisible(false);
			mStartupWindow.dispose();
			mStartupWindow = null;
		}

		if (mMainWindow != null) {
			mMainWindow.setVisible(false);
			mMainWindow.dispose();
			mMainWindow = null;
		}

		if (mGameManager != null) mGameManager = null;

		if (mUserManager != null) mUserManager = null;

		if (mServerAdapter != null) {
			mServerAdapter.disconnect();
			mServerAdapter = null;
		}
	}

}
