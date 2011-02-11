package communications;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import domain.Arsenal;
import domain.Game;
import domain.GameInfo;
import domain.Player;
import domain.Territory;
import domain.EventType;

import exceptions.AlreadyInGameException;
import exceptions.FullGameException;
import exceptions.GameNotFoundException;
import exceptions.InvalidGameInfoException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTerritoryException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

public interface IServer extends Remote {

	void registerUser(String name, String password, String email)
			throws RemoteException, UserAlreadyExistsException;

	UUID loginUser(String name, String password, Remote callback)
			throws RemoteException, WrongLoginException;

	void logoutUser(UUID session) throws RemoteException,
			InvalidSessionException;

	ArrayList<GameInfo> listGames(UUID sessId) throws RemoteException,
			InvalidSessionException;

	UUID createGame(UUID session, GameInfo info) throws RemoteException,
			InvalidGameInfoException, InvalidSessionException;

	void joinGame(UUID session, UUID game) throws RemoteException,
			FullGameException, GameNotFoundException, InvalidSessionException,
			AlreadyInGameException;

	void resignGame(UUID session, UUID game) throws RemoteException,
			GameNotFoundException, InvalidSessionException,
			NotCurrentPlayerGameException;

	Game playGame(UUID session, UUID game) throws RemoteException,
			GameNotFoundException, InvalidSessionException,
			InvalidTimeException, NotCurrentPlayerGameException;

	void quitGame(UUID session, UUID game) throws RemoteException,
			GameNotFoundException, InvalidSessionException,
			InvalidTimeException, NotCurrentPlayerGameException;

	void updateGame(UUID session, UUID game, ArrayList<Player> playerUpdate,
			ArrayList<Territory> territoryUpdate, EventType event)
			throws RemoteException, GameNotFoundException,
			InvalidSessionException, NotCurrentPlayerGameException;

	void attackTerritory(UUID session, UUID game, Territory src, Territory dst,
			Arsenal arsenal) throws RemoteException, GameNotFoundException,
			InvalidSessionException, InvalidTerritoryException,
			InvalidTimeException;

	void acceptAttack(UUID session, UUID game) throws RemoteException,
			GameNotFoundException, InvalidSessionException,
			InvalidTimeException;

	void requestedNegotiation(UUID session, UUID game, int money, int soldiers)
			throws RemoteException, GameNotFoundException,
			InvalidSessionException, InvalidTimeException;

	void endTurn(UUID session, UUID game) throws RemoteException,
			InvalidTimeException, InvalidSessionException;

}
