package com.umbrella.worldconq.ui;

import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Arsenal;

public interface GameEventListener {
	public void territoryUnderAttack(TerritoryDecorator src, TerritoryDecorator dst, Arsenal arsenal);

	public void negotiationRequested(int money, int soldiers);

	public void attackEvent(TerritoryDecorator src, TerritoryDecorator dst);

	public void negotiationEvent(TerritoryDecorator src, TerritoryDecorator dst);

	public void buyTerritoryEvent(TerritoryDecorator t);

	public void buyUnitsEvent(TerritoryDecorator t);
}
