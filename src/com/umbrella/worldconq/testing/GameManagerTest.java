package com.umbrella.worldconq.testing;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.comm.ClientAdapter;
import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.TerritoryDecorator;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.exceptions.EmptyStringException;
import com.umbrella.worldconq.exceptions.NegativeValueException;
import com.umbrella.worldconq.ui.GameEventListener;

import domain.Arsenal;
import domain.Player;

public class GameManagerTest extends TestCase {

	Process ServerProcess;
	private ServerAdapter srvAdapter;
	private ClientAdapter cltAdapter;
	private GameManager gameMgr;
	private UserManager usrMgr;
	private ArrayList<Calendar> despues;
	private ArrayList<Calendar> hoy;

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

			final Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, 2);
			hoy = new ArrayList<Calendar>();
			hoy.add(c);

			final Calendar d = Calendar.getInstance();
			d.set(2012, Calendar.MARCH, 1, 14, 0);
			despues = new ArrayList<Calendar>();
			despues.add(d);

		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGameManagerUpdateGame1() {
		System.out.println("TestCase::testGameManagerUpdateGame1");

		try {

			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel() != null);
			assertTrue(gameMgr.getOpenGameListModel() != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	// los parametros de createGame se consideran invalidos: 
	// 		name: null,cadena vacia; 
	// 		description: null; 
	// 		gameSession: null, fecha anterior; 
	// 		turnTime: -1; 
	// 		defTime: -1; 
	// 		negTime: -1;

	public void testGameManagerCreateGame1() {
		System.out.println("TestCase::testGameManagerCreateGame1");
		try {
			gameMgr.createGame("partida", "partida guerra mundo", despues, 112,
				1, 1);
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba por each choice 1");
		}
	}

	public void testGameManagerCreateGame2() {
		System.out.println("TestCase::testGameManagerCreateGame2");
		try {
			gameMgr.createGame(null, null, hoy, 1, 20, 33);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException por each choice 2");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testGameManagerCreateGame3() {
		System.out.println("TestCase::testGameManagerCreateGame3");
		try {
			gameMgr.createGame("", "", null, 0, 0, 0);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException por each choice 3");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testGameManagerCreateGame4() {
		System.out.println("TestCase::testGameManagerCreateGame4");
		try {
			gameMgr.createGame("", "", despues, 1, 1, 1);
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException por nombre vacio");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame5() {
		System.out.println("TestCase::testGameManagerCreateGame5");
		try {
			gameMgr.createGame(null, "partida guerra mundo",
				despues, 1, 1, 1);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException por nombre null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testGameManagerCreateGame6() {
		System.out.println("TestCase::testGameManagerCreateGame6");
		try {
			gameMgr.createGame("partida", null, hoy, 1, 1, 33);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException por descripcion null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testGameManagerCreateGame7() {
		System.out.println("TestCase::testGameManagerCreateGame7");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", null, 1, 1, 33);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException fecha null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testGameManagerCreateGame8() {
		System.out.println("TestCase::testGameManagerCreateGame8");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 0, 20, 33);
			fail("Esperaba NegativeValueException");
		} catch (final NegativeValueException e) {
			System.out.println("NegativeValueException por turno 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NegativeValueException");
		}
	}

	public void testGameManagerCreateGame9() {
		System.out.println("TestCase::testGameManagerCreateGame9");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 0, 33);
			fail("Esperaba NegativeValueException");
		} catch (final NegativeValueException e) {
			System.out.println("NegativeValueException por defensa 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NegativeValueException");
		}
	}

	public void testGameManagerCreateGame10() {
		System.out.println("TestCase::testGameManagerCreateGame10");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 20, 0);
			fail("Esperaba NegativeValueException");
		} catch (final NegativeValueException e) {
			System.out.println("NegativeValueException por nombre negociacion 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NegativeValueException");
		}
	}

	public void testGameManagerCreateGame11() {
		System.out.println("TestCase::testGameManagerCreateGame11");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 20, 33);
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
		}
	}

	public void testGameManagerCreateGame12() {
		System.out.println("TestCase::testGameManagerCreateGame12");
		try {
			gameMgr.createGame("partida", "", despues, 1, 1, 1);
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
		}
	}

	// los parametros de joinGame se consideran invalidos: 
	// 		gameSelected: 
	//	 		limite bajo: -1; 
	// 			limite alto: tope+1;

	public void testGameManagerJoinGame1() {
		System.out.println("TestCase::testGameManagerJoinGame1");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() > 0);

			gameMgr.joinGame(-1);
			fail("Esperaba ArrayIndexOutOfBoundsException");
		} catch (final ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba ArrayIndexOutOfBoundsException");
		}
	}

	public void testGameManagerJoinGame2() {
		System.out.println("TestCase::testGameManagerJoinGame2");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() > 0);

			final int filasActuales = gameMgr.getCurrentGameListModel().getRowCount();
			final int filasOpen = gameMgr.getOpenGameListModel().getRowCount();
			gameMgr.joinGame(1);
			gameMgr.updateGameList();
			assertTrue(filasActuales < gameMgr.getCurrentGameListModel().getRowCount());
			assertTrue(filasOpen > gameMgr.getOpenGameListModel().getRowCount());
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
		}
	}

	public void testGameManagerJoinGame3() {
		System.out.println("TestCase::testGameManagerJoinGame3");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() > 0);

			final int filasActuales = gameMgr.getCurrentGameListModel().getRowCount();
			final int filasOpen = gameMgr.getOpenGameListModel().getRowCount();
			gameMgr.joinGame(0);
			gameMgr.updateGameList();
			assertTrue(filasActuales < gameMgr.getCurrentGameListModel().getRowCount());
			assertTrue(filasOpen > gameMgr.getOpenGameListModel().getRowCount());
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
		}
	}

	public void testGameManagerJoinGame4() {
		System.out.println("TestCase::testGameManagerJoinGame4");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel() != null);
			assertTrue(gameMgr.getOpenGameListModel() != null);

			gameMgr.joinGame(2);
			fail("Esperaba IndexOutOfBoundsException");
		} catch (final IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException fuera de rango por arriba");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba IndexOutOfBoundsException");
		}
	}

	// los parametros de joinGame se consideran invalidos: 
	// 		gameSelected: 
	//	 		limite bajo: -1; 
	// 			limite alto: tope+1;

	public void testGameManagerConnectGame1() {
		System.out.println("TestCase::testGameManagerConnectGame1");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel() != null);

			assertTrue(gameMgr.getGameEngine() == null);
			gameMgr.connectToGame(-1, new TestGameEventListener());
			fail("Esperaba ArrayIndexOutOfBoundsException");
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba ArrayIndexOutOfBoundsException");
		}
	}

	public void testGameManagerConnectGame2() {
		System.out.println("TestCase::testGameManagerConnectGame2");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel() != null);

			assertTrue(gameMgr.getGameEngine() == null);
			gameMgr.connectToGame(0, new TestGameEventListener());
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
		}
	}

	public void testGameManagerConnectGame3() {
		System.out.println("TestCase::testGameManagerConnectGame3");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel() != null);

			assertTrue(gameMgr.getGameEngine() == null);
			gameMgr.connectToGame(1, new TestGameEventListener());
			fail("Esperaba IndexOutOfBoundsException");
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException fuera de rango por arriba");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba IndexOutOfBoundsException");
		}
	}

	public void testGameManagerDisconnectFromGame1() {
		System.out.println("TestCase::testGameManagerDisconnectFromGame1");
		try {
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() == 0);
			assertTrue(gameMgr.getOpenGameListModel().getRowCount() == 0);
			gameMgr.updateGameList();
			assertTrue(gameMgr.getCurrentGameListModel().getRowCount() > 0);
			assertTrue(gameMgr.getOpenGameListModel() != null);

			assertTrue(gameMgr.getGameEngine() == null);
			final TestGameEventListener tgel = new TestGameEventListener();
			gameMgr.connectToGame(0, tgel);
			assertTrue(gameMgr.getGameEngine() != null);

			gameMgr.disconnectFromGame();
			assertTrue(gameMgr.getGameEngine() == null);
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba Exception");
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

	private class TestGameEventListener implements GameEventListener {

		@Override
		public void territoryUnderAttack(TerritoryDecorator src, TerritoryDecorator dst, Arsenal arsenal) {
			// TODO Auto-generated method stub

		}

		@Override
		public void negotiationRequested(int money, int soldiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void attackEvent(TerritoryDecorator src, TerritoryDecorator dst) {
			// TODO Auto-generated method stub

		}

		@Override
		public void negotiationEvent(TerritoryDecorator src, TerritoryDecorator dst) {
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
		public void winnerEvent(Player p) {
			// TODO Auto-generated method stub

		}

		@Override
		public void turnChangedEvent(Player p) {
			// TODO Auto-generated method stub

		}

	}
}
