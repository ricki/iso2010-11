package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
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
	BufferedReader in;
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
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel() != null
					&& usrMgr.getGameManager().getOpenGameListModel() != null);
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
			usrMgr.getGameManager().createGame("", "", null, 112, 20, 33);
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
			usrMgr.getGameManager().createGame("", "", hoy, 0, -1, 0);
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
			usrMgr.getGameManager().createGame(null, "partida guerra mundo",
				antes, -1, -1, -1);
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
			usrMgr.getGameManager().createGame("partida", null, despues, 0, 0,
				0);
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
			usrMgr.getGameManager().createGame("", "", despues, 0, 0, 0);
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
			usrMgr.getGameManager().createGame(null, "partida guerra mundo",
				despues, 0, 0, 0);
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
			usrMgr.getGameManager().createGame("partida", null, hoy, 0, 0, 33);
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
			usrMgr.getGameManager().createGame("partida",
				"partida guerra mundo", antes, 0, 0, 33);
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
			usrMgr.getGameManager().createGame("partida",
				"partida guerra mundo", null, 0, 0, 33);
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
			usrMgr.getGameManager().createGame("partida",
				"partida guerra mundo", hoy, -1, 20, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por turno -1");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame11() {
		System.out.println("TestCase::testGameManagerCreateGame11");
		try {
			usrMgr.getGameManager().createGame("partida",
				"partida guerra mundo", hoy, 112, -1, 33);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por defensa -1");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame12() {
		System.out.println("TestCase::testGameManagerCreateGame12");
		try {
			usrMgr.getGameManager().createGame("partida",
				"partida guerra mundo", hoy, 112, 20, -1);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException por nombre negociacion -1");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerCreateGame13() {
		System.out.println("TestCase::testGameManagerCreateGame13");
		try {
			usrMgr.getGameManager().createGame("partida",
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
			usrMgr.getGameManager().createGame("partida", "", despues, 0, 0, 0);
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
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() > 0);

			usrMgr.getGameManager().joinGame(-1);
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerJoinGame2() {
		System.out.println("TestCase::testGameManagerJoinGame2");
		try {
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() > 0);

			final int filasActuales = usrMgr.getGameManager().getCurrentGameListModel().getRowCount();
			final int filasOpen = usrMgr.getGameManager().getOpenGameListModel().getRowCount();
			usrMgr.getGameManager().joinGame(0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(filasActuales < usrMgr.getGameManager().getCurrentGameListModel().getRowCount());
			assertTrue(filasOpen > usrMgr.getGameManager().getOpenGameListModel().getRowCount());
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerJoinGame3() {
		System.out.println("TestCase::testGameManagerJoinGame3");
		try {
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() > 0);

			final int filasActuales = usrMgr.getGameManager().getCurrentGameListModel().getRowCount();
			final int filasOpen = usrMgr.getGameManager().getOpenGameListModel().getRowCount();
			usrMgr.getGameManager().joinGame(0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(filasActuales < usrMgr.getGameManager().getCurrentGameListModel().getRowCount());
			assertTrue(filasOpen > usrMgr.getGameManager().getOpenGameListModel().getRowCount());
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerJoinGame4() {
		System.out.println("TestCase::testGameManagerJoinGame4");
		try {
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel() != null
					&& usrMgr.getGameManager().getOpenGameListModel() != null);

			usrMgr.getGameManager().joinGame(2);
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
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel() != null);

			assertTrue(usrMgr.getGameManager().getGameEngine() == null);
			usrMgr.getGameManager().connectToGame(-1, null);
			assertTrue(usrMgr.getGameManager().getGameEngine() != null);
		} catch (final InvalidArgumentException e) {
			System.out.println("InvalidArgumentException fuera de rango por debajo");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerConnectGame2() {
		System.out.println("TestCase::testGameManagerConnectGame2");
		try {
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel() != null);

			assertTrue(usrMgr.getGameManager().getGameEngine() == null);
			usrMgr.getGameManager().connectToGame(0, null);
			assertTrue(usrMgr.getGameManager().getGameEngine() != null);
		} catch (final InvalidArgumentException e) {
			fail("InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGameManagerConnectGame3() {
		System.out.println("TestCase::testGameManagerConnectGame3");
		try {
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() == 0
					&& usrMgr.getGameManager().getOpenGameListModel().getRowCount() == 0);
			usrMgr.getGameManager().updateGameList();
			assertTrue(usrMgr.getGameManager().getCurrentGameListModel().getRowCount() > 0
					&& usrMgr.getGameManager().getOpenGameListModel() != null);

			assertTrue(usrMgr.getGameManager().getGameEngine() == null);
			usrMgr.getGameManager().connectToGame(1, null);
			assertTrue(usrMgr.getGameManager().getGameEngine() != null);
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
