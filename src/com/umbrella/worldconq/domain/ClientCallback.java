package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import communications.IClient.TimeType;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;

public interface ClientCallback {

	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal);

	public void negotiationRequested(int money, int soldiers);

	public void resolveAttack();

	public void resolveNegotiation(int money, int sodiers);

	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event);

	public void timeExpired(TimeType whatTime);

}
