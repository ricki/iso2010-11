package com.umbrella.worldconq.testing;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.GameEngine;
import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.TerritoryDecorator;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
import com.umbrella.worldconq.ui.GameEventListener;

import domain.Arsenal;
import domain.EventType;
import domain.Player;
import domain.Spy;
import domain.Territory;
import exceptions.InvalidTerritoryException;

public class GameEngineTest extends TestCase {

	Process ServerProcess;
	private ServerAdapter srvAdapter;
	private ClientAdapter cltAdapter;
	private GameManager gameMgr;
	private UserManager usrMgr;
	private GameEngine gameEngine;

	@Override
	@Before
	public void setUp() throws Exception {
		System.out.println("TestCase::setUp");
		final String comand = "java -cp " + this.getClasspath()
				+ " com.umbrella.worldconq.stubserver.Server";

		try {
			ServerProcess = Runtime.getRuntime().exec(comand);
			Thread.sleep(1000);
		} catch (final Exception e) {
			fail(e.toString());
		}

		try {
			System.setProperty("java.security.policy",
				ClassLoader.getSystemResource("data/open.policy").toString());

			cltAdapter = new ClientAdapter();

			srvAdapter = new ServerAdapter();
			srvAdapter.setRemoteInfo(
				"WorldConqStubServer",
				InetAddress.getByName("localhost"),
				3234);
			srvAdapter.connect();

			gameMgr = new GameManager(srvAdapter, cltAdapter);
			usrMgr = new UserManager(srvAdapter, gameMgr, cltAdapter);
			gameMgr.setUserManager(usrMgr);
			usrMgr.createSession("JorgeCA", "jorge");

			//Conectar a la partida
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel() != null);
			assertTrue(gameMgr.getOpenGameListModel() != null);
			assertTrue(gameMgr.getGameEngine() == null);
			gameMgr.connectToGame(0, null);
			assertTrue(gameMgr.getGameEngine() != null);

		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory1() {
		System.out.println("TestCase::testAttackTerritory1");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Territorio 0 de jorge
			assertEquals(
				gameEngine.getMapListModel().getTerritoryAt(0).getPlayer().getName(),
				"JorgeCA");
			//Territorio 2 de angel
			assertEquals(
				gameEngine.getMapListModel().getTerritoryAt(2).getPlayer().getName(),
				"Aduran");
			//Territorio 2 no es de jorge
			assertTrue(!gameEngine.getMapListModel().getTerritoryAt(2).getPlayer().getName().equals(
				"JorgeCA"));
			//El territorio 2 es adyacente al territorio 0
			assertTrue(gameEngine.getMapListModel().getTerritoryAt(0).getAdjacentTerritories().contains(
				gameEngine.getMapListModel().getTerritoryAt(2)));
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory2() {
		System.out.println("TestCase::testAttackTerritory2");
		try {
			//Voy a atacar dos veces
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Territorio 0 de jorge
			assertEquals(
				gameEngine.getMapListModel().getTerritoryAt(0).getPlayer().getName(),
				"JorgeCA");
			//Territorio 2 de angel
			assertEquals(
				gameEngine.getMapListModel().getTerritoryAt(2).getPlayer().getName(),
				"Aduran");
			//Territorio 2 no es de jorge
			assertTrue(!gameEngine.getMapListModel().getTerritoryAt(2).getPlayer().getName().equals(
				"JorgeCA"));
			//El territorio 2 es adyacente al territorio 0
			assertTrue(gameEngine.getMapListModel().getTerritoryAt(0).getAdjacentTerritories().contains(
				gameEngine.getMapListModel().getTerritoryAt(2)));
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			fail("Esperaba PendingAttackException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException ya hay un ataque en proceso");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory3() {
		System.out.println("TestCase::testAttackTerritory3");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(-1, 2, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio origen -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory4() {
		System.out.println("TestCase::testAttackTerritory4");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(42, 2, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio origen 42");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory5() {
		System.out.println("TestCase::testAttackTerritory5");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(41, 2, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio origen 41, Jorge esta en 0");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory6() {
		System.out.println("TestCase::testAttackTerritory6");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, -1, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio destino -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory7() {
		System.out.println("TestCase::testAttackTerritory7");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 42, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio destino 42");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory8() {
		System.out.println("TestCase::testAttackTerritory8");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 0, 0, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException territorio origen = destino y destino no es Angel");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory9() {
		System.out.println("TestCase::testAttackTerritory9");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, -1, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException soldados -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory10() {
		System.out.println("TestCase::testAttackTerritory10");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 21, 0, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException soldados 21");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory11() {
		System.out.println("TestCase::testAttackTerritory11");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, -1, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException canones -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory12() {
		System.out.println("TestCase::testAttackTerritory12");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 7, 1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException canones 7");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory13() {
		System.out.println("TestCase::testAttackTerritory13");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 6, -1, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException misiles -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory14() {
		System.out.println("TestCase::testAttackTerritory14");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 6, 2, 6);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException misiles 2");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory15() {
		System.out.println("TestCase::testAttackTerritory15");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 6, 1, -1);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException icbm -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory16() {
		System.out.println("TestCase::testAttackTerritory16");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 6, 1, 7);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException icbm 7");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory17() {
		System.out.println("TestCase::testAttackTerritory17");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 0, 0, 0, 0);
			fail("Esperaba InvalidArgumentException");
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException atacar sin tropas");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAttackTerritory18() {
		System.out.println("TestCase::testAttackTerritory18");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//attackTerritory(src, dst, soldiers, cannons, missiles, icbm)
			gameEngine.attackTerritory(0, 2, 20, 6, 0, 0);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
		} catch (final PendingAttackException e) {
			System.out.println("PendingAttackException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testTerritoryUnderAttack1() {
		System.out.println("TestCase::testTerritoryUnderAttack1");
		try {
			//Genero los parametros
			final int[] p1 = {
					1, 2, 3
			};
			final int[] p2 = {
					1, 2, 3
			};
			final ArrayList<Territory> territoryList = new ArrayList<Territory>();
			final Territory tAtacante = new Territory(2,
				Territory.Continent.Europe,
				"Aduran", 10, p1, 2, 0, 1);
			final Territory tDefensor = new Territory(0,
				Territory.Continent.Europe,
				"JorgeCA", 20, p2, 1, 6, 1);
			territoryList.add(tAtacante);
			territoryList.add(tDefensor);

			final Arsenal tropas = new Arsenal(5, 4, 1, 0);
			gameEngine = gameMgr.getGameEngine();
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("Aduran", 250, true, false,
				new ArrayList<Spy>()));
			final ArrayList<Spy> spyList = new ArrayList<Spy>();
			spyList.add(new Spy(2, tAtacante.getIdTerritory()));

			playerList.add(new Player("JorgeCA", 200, true, true, spyList));
			gameEngine.updateClient(playerList, territoryList,
				EventType.TurnChanged);
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			gameEngine.territoryUnderAttack(tAtacante, tDefensor, tropas);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
		} catch (final InvalidTerritoryException e) {
			fail("InvalidTerritoryException");
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
	}

	public void testTerritoryUnderAttack2() {
		System.out.println("TestCase::testTerritoryUnderAttack2");
		try {
			//Genero los parametros
			final int[] p1 = {
					1, 2, 3
			};
			final int[] p2 = {
					1, 2, 3
			};
			final ArrayList<Territory> territoryList = new ArrayList<Territory>();
			final Territory tAtacante = new Territory(2,
				Territory.Continent.Europe,
				"Aduran", 10, p1, 2, 0, 1);
			final Territory tDefensor = new Territory(0,
				Territory.Continent.Europe,
				"JorgeCA", 20, p2, 1, 6, 1);
			territoryList.add(tAtacante);
			territoryList.add(tDefensor);

			final Arsenal tropas = new Arsenal(5, 4, 1, 0);
			gameEngine = gameMgr.getGameEngine();
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("Aduran", 250, true, false,
				new ArrayList<Spy>()));
			final ArrayList<Spy> spyList = new ArrayList<Spy>();
			spyList.add(new Spy(2, tAtacante.getIdTerritory()));

			playerList.add(new Player("JorgeCA", 200, true, true, spyList));
			gameEngine.updateClient(playerList, territoryList,
				EventType.TurnChanged);
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			gameEngine.territoryUnderAttack(null, tDefensor, tropas);
			fail("InvalidArgumentException");
		} catch (final InvalidTerritoryException e) {
			System.out.println("InvalidTerritoryException atacante null");
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
	}

	public void testTerritoryUnderAttack3() {
		System.out.println("TestCase::testTerritoryUnderAttack3");
		try {
			//Genero los parametros
			final int[] p1 = {
					1, 2, 3
			};
			final int[] p2 = {
					1, 2, 3
			};
			final ArrayList<Territory> territoryList = new ArrayList<Territory>();
			final Territory tAtacante = new Territory(2,
				Territory.Continent.Europe,
				"Aduran", 10, p1, 2, 0, 1);
			final Territory tDefensor = new Territory(0,
				Territory.Continent.Europe,
				"JorgeCA", 20, p2, 1, 6, 1);
			territoryList.add(tAtacante);
			territoryList.add(tDefensor);

			final Arsenal tropas = new Arsenal(5, 4, 1, 0);
			gameEngine = gameMgr.getGameEngine();
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("Aduran", 250, true, false,
				new ArrayList<Spy>()));
			final ArrayList<Spy> spyList = new ArrayList<Spy>();
			spyList.add(new Spy(2, tAtacante.getIdTerritory()));

			playerList.add(new Player("JorgeCA", 200, true, true, spyList));
			gameEngine.updateClient(playerList, territoryList,
				EventType.TurnChanged);
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			gameEngine.territoryUnderAttack(tAtacante, null, tropas);
			fail("InvalidArgumentException");
		} catch (final InvalidTerritoryException e) {
			System.out.println("InvalidTerritoryException atacante null");
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
	}

	public void testTerritoryUnderAttack4() {
		System.out.println("TestCase::testTerritoryUnderAttack4");
		try {
			//Genero los parametros
			final int[] p1 = {
					1, 2, 3
			};
			final int[] p2 = {
					1, 2, 3
			};
			final ArrayList<Territory> territoryList = new ArrayList<Territory>();
			final Territory tAtacante = new Territory(2,
				Territory.Continent.Europe,
				"Aduran", 10, p1, 2, 0, 1);
			final Territory tDefensor = new Territory(0,
				Territory.Continent.Europe,
				"JorgeCA", 20, p2, 1, 6, 1);
			territoryList.add(tAtacante);
			territoryList.add(tDefensor);

			gameEngine = gameMgr.getGameEngine();
			final ArrayList<Player> playerList = new ArrayList<Player>();
			playerList.add(new Player("Aduran", 250, true, false,
				new ArrayList<Spy>()));
			final ArrayList<Spy> spyList = new ArrayList<Spy>();
			spyList.add(new Spy(2, tAtacante.getIdTerritory()));

			playerList.add(new Player("JorgeCA", 200, true, true, spyList));
			gameEngine.updateClient(playerList, territoryList,
				EventType.TurnChanged);
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			gameEngine.territoryUnderAttack(tAtacante, tDefensor, null);
			fail("InvalidArgumentException");
		} catch (final InvalidTerritoryException e) {
			System.out.println("InvalidTerritoryException atacante null");
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
	}

	//Terminado AcceptAttack

	public void testAcceptAttack1() {
		System.out.println("TestCase::testAcceptAttack1");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Aceptar el ataque
			gameEngine.acceptAttack();
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNull(o);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testAcceptAttack2() {
		System.out.println("TestCase::testAcceptAttack2");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Aceptar el ataque
			gameEngine.acceptAttack();
			fail("Esperaba Exception");
		} catch (final Exception e) {
			System.out.println(e.toString() + " mCurrentAttack es null");
		}
	}

	// Faltan con los parámetos

	public void testRequestNegotiation1() {
		System.out.println("TestCase::requestNegotiation1");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(0, 10);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNull(o);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRequestNegotiation2() {
		System.out.println("TestCase::testRequestNegotiation2");
		try {
			gameEngine = gameMgr.getGameEngine();
			final Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(0, 10);
			fail("Esperaba Exception");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException");
		} catch (final Exception e) {
			System.out.println(e.toString() + " mCurrentAttack es null");
		}
	}

	public void testRequestNegotiation3() {
		System.out.println("TestCase::requestNegotiation3");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(-1, 0);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException dinero -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRequestNegotiation4() {
		System.out.println("TestCase::requestNegotiation4");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(251, 0);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException dinero 250");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRequestNegotiation5() {
		System.out.println("TestCase::requestNegotiation5");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(250, -1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException soldados -1");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRequestNegotiation6() {
		System.out.println("TestCase::requestNegotiation6");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Pedir Negociacion
			gameEngine.requestNegotiation(250, 11);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException soldados 11");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	/*
	 * public void testRequestNegotiation7() {
	 * System.out.println("TestCase::requestNegotiation7"); try { gameEngine =
	 * gameMgr.getGameEngine(); Object o =
	 * PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
	 * assertNull(o); //Hacer el ataque gameEngine.attackTerritory(0, 2, 0, 0,
	 * 1, 6); o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
	 * assertNotNull(o); //Pedir Negociacion gameEngine.requestNegotiation(250,
	 * 0); o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
	 * assertNull(o); } catch (final InvalidArgumentException e) {
	 * fail("InvalidArgumentException"); } catch (final Exception e) {
	 * fail(e.toString()); } }
	 */

	public void testResolveAttack1() {
		System.out.println("TestCase::testResolveAttack1");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Resolver el ataque
			gameEngine.resolveAttack();
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNull(o);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	/*
	 * public void testResolveAttack2() {
	 * System.out.println("TestCase::testResolveAttack2"); try { gameEngine =
	 * gameMgr.getGameEngine(); final Object o =
	 * PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
	 * assertNull(o); //Resolver el ataque sin atacar primero
	 * gameEngine.resolveAttack(); fail("Exception"); } catch (final Exception
	 * e) { System.out.println(e.toString()); } }
	 */

	public void testResolveNegotiation1() {
		System.out.println("TestCase::testResolveNegotiation1");
		try {
			gameEngine = gameMgr.getGameEngine();
			Object o = PrivateAccessor.getPrivateField(gameEngine,
				"mCurrentAttack");
			assertNull(o);
			//Hacer el ataque
			gameEngine.attackTerritory(0, 2, 0, 0, 1, 6);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNotNull(o);
			//Resolver la negociacion
			//resolveNegotiation(money, soldiers);
			gameEngine.resolveNegotiation(1, 1);
			o = PrivateAccessor.getPrivateField(gameEngine, "mCurrentAttack");
			assertNull(o);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		System.out.println("TestCase::tearDown");
		ServerProcess.destroy();
		try {
			ServerProcess.destroy();
			ServerProcess.waitFor();
			srvAdapter.disconnect();
		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}

	private class TestUnterAttack implements GameEventListener {

		boolean territoryUnderAttackCalled = false;

		public void territoryUnderAttack(final TerritoryDecorator src, final TerritoryDecorator dst, final Arsenal arsenal) {
			territoryUnderAttackCalled = true;
		}

		boolean territoryUnderAttackWasCalled() {
			return territoryUnderAttackCalled;
		}

		@Override
		public void attackEvent(TerritoryDecorator src, TerritoryDecorator dst) {
			// TODO Auto-generated method stub

		}

		@Override
		public void buyTerritoryEvent(TerritoryDecorator t) {
			// TODO Auto-generated method stub

		}

		@Override
		public void buyUnitsEvent(TerritoryDecorator t) {
			// TODO Auto-generated method stub

		}

		@Override
		public void negotiationEvent(TerritoryDecorator src, TerritoryDecorator dst) {
			// TODO Auto-generated method stub

		}

		@Override
		public void negotiationRequested(int money, int soldiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void winnerEvent(Player p) {
			// TODO Auto-generated method stub

		}

	}
}
