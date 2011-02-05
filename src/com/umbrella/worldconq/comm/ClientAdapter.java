package com.umbrella.worldconq.comm;

import com.umbrella.worldconq.domain.GameEngine;

public class ClientAdapter {

	private GameEngine mGameEngine;

	public ClientAdapter() {
		mGameEngine = null;
	}

	public ClientAdapter(GameEngine mGameEngine) {
		this.mGameEngine = mGameEngine;
	}

	public void setGameEngine(GameEngine mGameEngine) {
		this.mGameEngine = mGameEngine;
	}
}
