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
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;

public class GameManagerTest extends TestCase {

	Process ServerProcess;
	private ServerAdapter srvAdapter;
	private ClientAdapter cltAdapter;
	private GameManager gameMgr;
	private UserManager usrMgr;
	private ArrayList<Calendar> antes;
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
			usrMgr = new UserManager(srvAdapter, gameMgr);

			gameMgr.setUserManager(usrMgr);

			usrMgr.createSession("JorgeCA", "jorge");

			final Calendar c = Calendar.getInstance();
			hoy = new ArrayList<Calendar>();
			hoy.add(c);

			final Calendar a = Calendar.getInstance();
			a.set(2009, Calendar.MARCH, 1, 14, 0);
			antes = new ArrayList<Calendar>();
			antes.add(a);

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
			usrMgr.getGameManager().updateGameList();
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
			gameMgr.createGame("", "", null, 112, 20, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por each choice 1");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame2() {
		System.out.println("TestCase::testGameManagerCreateGame2");
		try {
			gameMgr.createGame("", "", hoy, 1, 0, 1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por each choice 2");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame3() {
		System.out.println("TestCase::testGameManagerCreateGame3");
		try {
			gameMgr.createGame(null, "partida guerra mundo",
				antes, 0, 0, 0);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por each choice 3");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame4() {
		System.out.println("TestCase::testGameManagerCreateGame4");
		try {
			gameMgr.createGame("partida", null, despues, 1, 1,
				1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por each choice 4");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame5() {
		System.out.println("TestCase::testGameManagerCreateGame5");
		try {
			gameMgr.createGame("", "", despues, 1, 1, 1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por nombre vacio");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame6() {
		System.out.println("TestCase::testGameManagerCreateGame6");
		try {
			gameMgr.createGame(null, "partida guerra mundo",
				despues, 1, 1, 1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por nombre null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame7() {
		System.out.println("TestCase::testGameManagerCreateGame7");
		try {
			gameMgr.createGame("partida", null, hoy, 1, 1, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por descripcion null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame8() {
		System.out.println("TestCase::testGameManagerCreateGame8");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", antes, 1, 1, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por fecha anterior");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame9() {
		System.out.println("TestCase::testGameManagerCreateGame9");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", null, 1, 1, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fecha null");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame10() {
		System.out.println("TestCase::testGameManagerCreateGame10");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 0, 20, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por turno 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame11() {
		System.out.println("TestCase::testGameManagerCreateGame11");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 0, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por defensa 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame12() {
		System.out.println("TestCase::testGameManagerCreateGame12");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 20, 0);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por nombre negociacion 0");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame13() {
		System.out.println("TestCase::testGameManagerCreateGame13");
		try {
			gameMgr.createGame("partida",
				"partida guerra mundo", hoy, 112, 20, 33);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame14() {
		System.out.println("TestCase::testGameManagerCreateGame14");
		try {
			gameMgr.createGame("partida", "", despues, 1, 1, 1);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por arriba");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			gameMgr.connectToGame(-1, null);
			fail("Esperaba InvalidArgumentException");
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			gameMgr.connectToGame(0, null);
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			gameMgr.connectToGame(1, null);
			fail("Esperaba InvalidArgumentException");
			assertTrue(gameMgr.getGameEngine() != null);
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por arriba");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
			gameMgr.connectToGame(0, null);
			assertTrue(gameMgr.getGameEngine() != null);

			gameMgr.disconnectFromGame();
			assertTrue(gameMgr.getGameEngine() == null);
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por arriba");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
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
}
