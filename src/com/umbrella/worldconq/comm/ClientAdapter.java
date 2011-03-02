package com.umbrella.worldconq.comm;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

import com.umbrella.worldconq.domain.ClientCallback;
import communications.IClient;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Territory;
import exceptions.GameNotFoundException;
import exceptions.InvalidTerritoryException;

public class ClientAdapter extends UnicastRemoteObject
		implements IClient, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3191656668310586049L;
	private ClientCallback mClientCallback;

	public ClientAdapter() throws RemoteException {
		mClientCallback = null;
	}

	public ClientAdapter(ClientCallback mClientCallback) throws RemoteException {
		this.mClientCallback = mClientCallback;
	}

	public void setCallback(ClientCallback cb) {
		mClientCallback = cb;

	}

	public ClientCallback getCallback() {
		return mClientCallback;
	}

	private void checkValidGame(UUID game) throws GameNotFoundException {
		if (mClientCallback == null || !mClientCallback.getId().equals(game))
			throw new GameNotFoundException("");
	}

	@Override
	public void updateClient(UUID game, ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) throws RemoteException, GameNotFoundException {
		this.checkValidGame(game);
		mClientCallback.updateClient(playerUpdate, territoryUpdate, event);

	}

	@Override
	public void territoryUnderAttack(UUID game, Territory src, Territory dst, Arsenal arsenal) throws RemoteException, GameNotFoundException, InvalidTerritoryException {
		this.checkValidGame(game);
		mClientCallback.territoryUnderAttack(src, dst, arsenal);

	}

	@Override
	public void negotiationRequested(UUID game, int money, int soldiers) throws RemoteException, GameNotFoundException {
		this.checkValidGame(game);
		mClientCallback.negotiationRequested(money, soldiers);

	}

	@Override
	public void attackAccepted(UUID game) throws RemoteException, GameNotFoundException {
		this.checkValidGame(game);
		mClientCallback.resolveAttack();
	}

	@Override
	public void timeExpired(UUID game, TimeType whatTime) throws RemoteException, GameNotFoundException {
		this.checkValidGame(game);
		mClientCallback.timeExpired(game, whatTime);
	}

}
