package com.umbrella.worldconq.ui;

import domain.Territory;

public interface GameEventListener {
	public void territoryUnderAttack(Territory src, Territory dst);

	public void negotiationRequested(int money, int soldiers);
}
