package com.umbrella.worldconq.ui;

import com.umbrella.worldconq.domain.TerritoryDecorator;

public interface GameEventListener {
	public void territoryUnderAttack(TerritoryDecorator src, TerritoryDecorator dst);

	public void negotiationRequested(int money, int soldiers);
}
