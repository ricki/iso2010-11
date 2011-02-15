package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;

public interface ClientCallback {

	UUID getId();

	void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal);

	void negotiationRequested(int money, int soldiers);

	void resolveAttack();

	void resolveNegotiation(int money, int soldiers);

	void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event);

	void timeExpired(UUID game, communications.IClient.TimeType whatTime);
}
