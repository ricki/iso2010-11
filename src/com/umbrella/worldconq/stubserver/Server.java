package com.umbrella.worldconq.stubserver;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.umbrella.worldconq.domain.Session;
import com.umbrella.worldconq.domain.TerritoryData;
import communications.IServer;
import domain.Arsenal;
import domain.EventType;
import domain.Game;
import domain.GameInfo;
import domain.Player;
import domain.Spy;
import domain.Territory;
import domain.Territory.Continent;

import exceptions.GameNotFoundException;
import exceptions.InvalidGameInfoException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTerritoryException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 8434201731175738674L;
	private final int puerto = 3234;
	private final String miIP;
	private final Registry reg;

	private final String[][] Users = {
			{
					"JorgeCA", "jorge", "jorge.colao@gmail.com"
			},
			{
					"ricki", "ricki", "ricardo.ruedas@gmail.com"
			},
			{
					"pobleteag", "antonio", "pobleteag@gmail.com"
			},
			{
					"DaniLR", "daniel", "daniel.leonromero@gmail.com"
			},
			{
					"Aduran", "angel", "anduraniz@gmail.com"
			},
			{
					"LauraN", "laura", "arualitan@gmail.com"
			},
			{
					"deejaytoni", "toni", "deejaytoni@gmail.com"
			}
	};

	private final ArrayList<String[]> registerUsers;

	private final ArrayList<GameInfo> gameList;

	private final ArrayList<Session> sessionsList;

	private final ArrayList<Game> testGameList;

	public Server() throws Exception, RemoteException {
		super();
		System.setProperty("java.security.policy",
			ClassLoader.getSystemResource("data/open.policy").toString());

		gameList = new ArrayList<GameInfo>();
		registerUsers = new ArrayList<String[]>();
		sessionsList = new ArrayList<Session>();
		testGameList = new ArrayList<Game>();

		for (final String[] user : Users)
			registerUsers.add(user);

		{ // GameInfo 01
			final int[] p = {
					1, 2, 3
			};
			final Game testGame = new Game();
			final ArrayList<String> player = new ArrayList<String>();
			// new Player("JorgeCA", 1000, true, true, new ArrayList<Spy>());
			// new Player("Aduran", 1000, true, true, new ArrayList<Spy>());
			player.add("JorgeCA");
			player.add("Aduran");
			final ArrayList<Calendar> session = new ArrayList<Calendar>();
			session.add(Calendar.getInstance());
			gameList.add(new GameInfo(UUID.randomUUID(), "game01",
				"desc from game01", player, session, 3, 0, 0, 0));
			// añadimos los datos a la partida necesarios para la clase Game
			testGame.setGameInfo(gameList.get(0));
			// lista de jugadores
			final ArrayList<Player> playerList = new ArrayList<Player>();
			final ArrayList<Spy> spyList = new ArrayList<Spy>();
			final Territory t = new Territory(3, Territory.Continent.Europe,
				null, 10, p, 2, 0, 1); // territorio Angel
			playerList.add(new Player("Aduran", 250, true, false,
				new ArrayList<Spy>()));
			t.setOwner(playerList.get(0));
			spyList.add(new Spy(2, t));
			playerList.add(new Player("JorgeCA", 200, true, true, spyList));
			testGame.setPlayers(playerList);
			//añadimos los datos a la partida necesarios para la clase Territory
			final ArrayList<Territory> mapList = new ArrayList<Territory>();
			this.rellenarMapaInicial(mapList);

			mapList.remove(TerritoryData.getIndex(Territory.Continent.Europe, 1));
			mapList.add(TerritoryData.getIndex(Territory.Continent.Europe, 1),
				new Territory(1, Territory.Continent.Europe,
					playerList.get(1), 20, p, 1, 0, 1));
			mapList.remove(TerritoryData.getIndex(t));
			mapList.add(TerritoryData.getIndex(t), t);
			testGame.setMap(mapList);
			testGameList.add(testGame);
		}
		{ // GameInfo 02
			final Game testGame = new Game();
			final ArrayList<String> player = new ArrayList<String>();
			// new Player("ricki", 1000, true, true, new ArrayList<Spy>());
			// new Player("DaniLR", 1000, true, false, new ArrayList<Spy>());
			// new Player("deejaytoni", 1000, true, false, new ArrayList<Spy>());
			player.add("ricki");
			player.add("DaniLR");
			player.add("deejaytoni");
			final ArrayList<Calendar> session = new ArrayList<Calendar>();
			session.add(Calendar.getInstance());
			gameList.add(new GameInfo(UUID.randomUUID(), "game02",
				"desc from game02", player, session, 6, 0, 0, 0));
			// añadimos los datos a la partida necesarios para la clase Game
			testGame.setGameInfo(gameList.get(1));
			// lista de jugadores
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("ricki", 200, true, true, null));
			playerList.add(new Player("DaniLR", 250, true, false, null));
			playerList.add(new Player("deejaytoni", 300, true, false, null));
			testGame.setPlayers(playerList);
			//añadimos los datos a la partida necesarios para la clase Territory
			final ArrayList<Territory> mapList = new ArrayList<Territory>();
			this.rellenarMapaInicial(mapList);
			final int[] p = {
					1, 2, 3
			};
			this.borrarCosasMapa(mapList, 1, Territory.Continent.Africa);
			mapList.add(new Territory(1, Territory.Continent.Africa,
				playerList.get(0), 20, p, 1, 0, 1));
			this.borrarCosasMapa(mapList, 3, Territory.Continent.Africa);
			mapList.add(new Territory(3, Territory.Continent.Africa,
				playerList.get(1), 10, p, 2, 0, 1));
			this.borrarCosasMapa(mapList, 4, Territory.Continent.Africa);
			mapList.add(new Territory(4, Territory.Continent.Africa,
				playerList.get(2), 15, p, 2, 0, 1));
			testGame.setMap(mapList);
			testGameList.add(testGame);
		}
		{ // GameInfo 03
			final Game testGame = new Game();
			final ArrayList<String> player = new ArrayList<String>();
			// new Player("pobleteag", 1000, true, true, new ArrayList<Spy>());
			// new Player("LauraN", 1000, true, false, new ArrayList<Spy>());
			player.add("pobleteag");
			player.add("LauraN");
			final ArrayList<Calendar> session = new ArrayList<Calendar>();
			session.add(Calendar.getInstance());
			gameList.add(new GameInfo(UUID.randomUUID(), "game03",
				"desc from game03", player, session, 6, 0, 0, 0));
			// añadimos los datos a la partida necesarios para la clase Game
			testGame.setGameInfo(gameList.get(2));
			// lista de jugadores
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("pobleteag", 200, true, true, null));
			playerList.add(new Player("LauraN", 250, true, false, null));
			testGame.setPlayers(playerList);
			//añadimos los datos a la partida necesarios para la clase Territory
			final ArrayList<Territory> mapList = new ArrayList<Territory>();
			this.rellenarMapaInicial(mapList);
			final int[] p = {
					1, 2, 3
			};
			this.borrarCosasMapa(mapList, 1, Territory.Continent.Asia);
			mapList.add(new Territory(1, Territory.Continent.Asia,
				playerList.get(0), 20, p, 1, 0, 1));
			this.borrarCosasMapa(mapList, 3, Territory.Continent.Asia);
			mapList.add(new Territory(3, Territory.Continent.Asia,
				playerList.get(1), 10, p, 2, 0, 1));
			testGame.setMap(mapList);
			testGameList.add(testGame);
		}

		miIP = (InetAddress.getLocalHost()).toString();
		System.out.println("Conexion establecida por:");
		System.out.println("IP=" + miIP + ", y puerto=" + puerto);

		reg = LocateRegistry.createRegistry(puerto);
		reg.rebind("WorldConqStubServer", this);

		System.out.println("Esperando peticiones...");
	}

	private void borrarCosasMapa(ArrayList<Territory> mapList, int id, Continent c) {
		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getIdTerritory() == id
					&& mapList.get(i).getContinent() == c) {
				mapList.remove(i);
			}
		}

	}

	public void rellenarMapaInicial(ArrayList<Territory> mapList) {
		final int[] numTerritorios = {
				9, 4, 7, 6, 12, 4
		};
		final int[] ca = {
				-1, -1, -1
		};
		for (int i = 0; i < 6; i++) {
			for (int j = 1; j <= numTerritorios[i]; j++) {
				switch (i) {
				case 2:
					mapList.add(new Territory(j,
						Territory.Continent.Europe,
						null, -1, ca, -1, -1, -1));
					break;
				case 4:
					mapList.add(new Territory(j,
						Territory.Continent.Asia,
						null, -1, ca, -1, -1, -1));
					break;
				case 3:
					mapList.add(new Territory(j,
						Territory.Continent.Africa,
						null, -1, ca, -1, -1, -1));
					break;
				case 0:
					mapList.add(new Territory(j,
						Territory.Continent.NorthAmerica,
						null, -1, ca, -1, -1, -1));
					break;
				case 1:
					mapList.add(new Territory(j,
						Territory.Continent.SouthAmerica,
						null, -1, ca, -1, -1, -1));
					break;
				case 5:
					mapList.add(new Territory(j,
						Territory.Continent.Oceania,
						null, -1, ca, -1, -1, -1));
					break;

				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Server();
	}

	@Override
	public void registerUser(String name, String password, String email) throws RemoteException, UserAlreadyExistsException {
		System.out.println("IServer::registerUser " + name);

		final String[] user = {
				name, password, email
		};

		for (int i = 0; i < registerUsers.size(); i++) {
			if (registerUsers.get(i)[0].compareTo(name) == 0)
				throw new UserAlreadyExistsException();
		}
		registerUsers.add(user);
	}

	@Override
	public UUID loginUser(String name, String password, Remote callback) throws RemoteException, WrongLoginException {
		System.out.println("IServer::loginUser " + name);
		boolean encontrado = false;
		UUID id = null;
		for (int i = 0; i < registerUsers.size() && encontrado == false; i++) {
			if (registerUsers.get(i)[0].compareTo(name) == 0
					&& registerUsers.get(i)[1].compareTo(password) == 0) {
				encontrado = true;
				id = UUID.randomUUID();
				final Session session = new Session(id, name);
				sessionsList.add(session);
			}
		}
		if (encontrado == false) throw new WrongLoginException();

		return id;
	}

	@Override
	public void logoutUser(UUID session) throws RemoteException, InvalidSessionException {
		System.out.println("IServer::logoutUser " + session);
	}

	@Override
	public ArrayList<GameInfo> listGames() throws RemoteException {
		System.out.println("IServer::listGames");
		return gameList;
	}

	@Override
	public UUID createGame(GameInfo info) throws RemoteException, InvalidGameInfoException {
		System.out.println("IServer::createGame");
		System.out.println("Nombre de la partida: " + info.getName());
		System.out.println("Descripción de la partida: "
				+ info.getDescription());
		for (final Calendar cal : info.getGameSessions()) {
			System.out.println("Sesión: " + cal.getTime());
		}
		return UUID.randomUUID();
	}

	@Override
	public void joinGame(UUID session, UUID partida) throws RemoteException, GameNotFoundException, InvalidSessionException {
		System.out.println("IServer::joinGame");
		boolean foundGame = false;
		boolean foundSession = false;
		//compruebo que la sesion se encuentra dentro de la lista de sesiones activas.
		for (int i = 0; i < sessionsList.size() && foundSession == false; i++) {
			if (sessionsList.get(i).getId().compareTo(session) == 0) {
				foundSession = true;
				//La sesion existe, ahora compruebo que la partida existe.
				for (int j = 0; j < gameList.size() && foundGame == false; j++) {
					if (gameList.get(j).getId().compareTo(partida) == 0) {
						gameList.get(j).getPlayers().add(
							sessionsList.get(i).getUser());
						foundGame = true;
					}
				}
			}
		}
		if (foundSession == false) {
			throw new InvalidSessionException();
		} else if (foundGame == false) {
			throw new GameNotFoundException();
		}
	}

	@Override
	public void resignGame(UUID session, UUID partida) throws RemoteException, GameNotFoundException, InvalidSessionException {
		System.out.println("IServer::resignGame");
	}

	@Override
	public Game playGame(UUID session, UUID game) throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException {
		System.out.println("IServer::playGame");
		Game ret = new Game();
		for (int i = 0; i < testGameList.size(); i++) {
			if (game.equals(testGameList.get(i).getGameInfo().getId())) {
				ret = testGameList.get(i);
			}
		}
		return ret;
	}

	@Override
	public void quitGame(UUID session, UUID game) throws RemoteException, GameNotFoundException, InvalidSessionException {
		System.out.println("IServer::quitGame");
	}

	@Override
	public void updateGame(UUID session, UUID game, ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException {
		System.out.println("IServer::updateGame");
	}

	@Override
	public void attackTerritory(UUID session, UUID game, Territory src, Territory dst, Arsenal arsenal) throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTerritoryException {
		System.out.println("IServer::attackTerritory");
	}

	@Override
	public void acceptAttack(UUID idSession, UUID idPartida) throws RemoteException, GameNotFoundException, InvalidSessionException {
		System.out.println("IServer::acceptAttack");
	}

	@Override
	public void requestedNegotiation(UUID session, UUID game, int money, int soldiers) throws RemoteException, GameNotFoundException, InvalidSessionException {
		System.out.println("IServer::requestedNegotiation");
	}

	@Override
	public void endTurn(UUID session, UUID game) throws Exception, RemoteException {
		System.out.println("IServer::updateGame");
	}

}
