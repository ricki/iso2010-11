package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import communications.IClient.TimeType;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;
import exceptions.InvalidArsenalException;
import exceptions.InvalidTerritoryException;
import exceptions.NotCurrentPlayerGameException;

public interface ClientCallback {

	public UUID getId();

	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws InvalidTerritoryException;

	public void negotiationRequested(int money, int soldiers) throws InvalidArsenalException;

	public void resolveAttack();

	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) throws NotCurrentPlayerGameException;

	public void timeExpired(UUID game, TimeType whatTime);
}
