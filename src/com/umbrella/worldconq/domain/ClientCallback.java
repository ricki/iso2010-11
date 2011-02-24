package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;
import exceptions.InvalidTerritoryException;

public interface ClientCallback {

	public UUID getId();

	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws InvalidTerritoryException;

	public void negotiationRequested(int money, int soldiers);

	public void resolveAttack();

	//public void resolveNegotiation(int money, int soldiers);

	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event);

	public void timeExpired(UUID game, communications.IClient.TimeType whatTime);
}
