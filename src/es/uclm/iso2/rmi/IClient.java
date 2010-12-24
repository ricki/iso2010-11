package es.uclm.iso2.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface IClient {

	public enum TimeType {
		TurnExpired, DefenseExpired, NegotiationExpired
	}

	void updateClient(UUID game, ArrayList<Player> playerUpdate,
			ArrayList<Territory> territoryUpdate,EventType event) throws RemoteException,
			GameNotFoundException, NotCurrentPlayerGameException;

	void territoryUnderAttack(UUID game, Territory src, Territory dst,
			Arsenal arsenal) throws RemoteException, GameNotFoundException,
			InvalidTerritoryException;

	void negotiationRequested(UUID game, int money, int soldiers)
			throws RemoteException, GameNotFoundException,
			InvalidArsenalException;

	void attackAccepted(UUID game) throws RemoteException,
			GameNotFoundException;

	void timeExpired(UUID game, TimeType whatTime) throws RemoteException,
			GameNotFoundException;

}
