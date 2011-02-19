package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;

public interface ClientCallback {

	public UUID getId();

	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws Exception;

	public void negotiationRequested(int money, int soldiers) throws Exception;

	public void resolveAttack() throws Exception;

	public void resolveNegotiation(int money, int soldiers) throws Exception;

	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event);

	public void timeExpired(UUID game, communications.IClient.TimeType whatTime);
}
