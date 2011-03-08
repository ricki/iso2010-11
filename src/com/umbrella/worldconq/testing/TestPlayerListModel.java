package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.domain.PlayerListModel;

import domain.Player;

public class TestPlayerListModel extends TestCase {
	Process ServerProcess;
	BufferedReader in;

	Player owner;
	Player opponent;
	ArrayList<Player> data;

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
			owner = new Player("propio");
			owner.setHasTurn(false);
			owner.setOnline(false);
			opponent = new Player("oponente");
			opponent.setHasTurn(true);
			opponent.setOnline(true);
			data = new ArrayList<Player>();
			data.add(opponent);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetColumnName1() {
		System.out.println("TestCase::testGetColumnName1");

		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final String column = mPlayerListModel.getColumnName(1);
			assertTrue(column != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetColumnName2() {
		System.out.println("TestCase::testGetColumnName2");

		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			mPlayerListModel.getColumnName(5);
			fail("Esperaba Exception");
		} catch (final Exception e) {

		}
	}

	public void testConstructor() {
		System.out.println("TestCase::testConstructor");

		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			assertTrue(mPlayerListModel != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testConstructor2() {
		System.out.println("TestCase::testConstructor2");

		try {
			new PlayerListModel(null, null);
			fail("Se esperaba excepcion");
		} catch (final Exception e) {

		}
	}

	public void testSetData1() {
		System.out.println("TestCase::testSetData1");

		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			mPlayerListModel.setData(data);
			assertTrue(mPlayerListModel != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testSetData2() {
		System.out.println("TestCase::testSetData2");

		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			mPlayerListModel.setData(null);
			//fail("Se esperaba excepcion InvalidArgumentException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGetSelfPlayer() {
		System.out.println("TestCase:: getSeltPlayer");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final Player me = mPlayerListModel.getSelfPlayer();
			assertTrue(owner == me);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testUpdatePlayer() {
		System.out.println("TestCase:: updatePlayer");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final String owner_name = owner.getName();
			final int owner_money = owner.getMoney();

			owner.setMoney(100000);
			owner.setName("cambio de nombre");
			mPlayerListModel.updatePlayer(owner);
			assertFalse(mPlayerListModel.getSelfPlayer().getName().equals(
				owner_name));
			assertFalse(mPlayerListModel.getSelfPlayer().getName().equals(
				owner_money));
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetRowCount() {
		System.out.println("TestCase:: GetRowCount");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final int rows = mPlayerListModel.getRowCount();
			assertTrue(rows >= 0);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetColumnCount() {
		System.out.println("TestCase:: GetColumnCount");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final int columns = mPlayerListModel.getColumnCount();
			assertTrue(columns >= 0);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetActivePlayer() {
		System.out.println("TestCase:: GetActivePlayer");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Player active = mPlayerListModel.getActivePlayer();
			assertTrue(opponent.getName().equals(active.getName()));
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetActivePlayer2() {
		System.out.println("TestCase:: GetActivePlayer2");
		try {
			opponent.setHasTurn(false);
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Player active = mPlayerListModel.getActivePlayer();
			assertTrue(active == null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPlayerByName1() {
		System.out.println("TestCase:: testGetPlayerByName1");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Player active = mPlayerListModel.getPlayerByName("oponente");
			assertTrue(active != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPlayerByName2() {
		System.out.println("TestCase:: testGetPlayerByName2");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner);
			final Player player = mPlayerListModel.getPlayerByName(null);
			assertTrue(player == null);
		} catch (final Exception e) {

		}
	}

	public void testGetPlayerByName3() {
		System.out.println("TestCase:: testGetPlayerByName3");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Player active = mPlayerListModel.getPlayerByName("no existe");
			assertTrue(active == null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPlayerAt() {
		System.out.println("TestCase:: testGetPlayerAt");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Player player = mPlayerListModel.getPlayerAt(0);
			assertTrue(player != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPlayerAt2() {
		System.out.println("TestCase:: testGetPlayerAt2");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			mPlayerListModel.getPlayerAt(5);
			fail("Se esperaba excepcion");
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt1() {
		System.out.println("TestCase:: testGetValueAt1");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Object nombre = mPlayerListModel.getValueAt(0, 0);
			assertTrue(nombre != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetValueAt2() {
		System.out.println("TestCase:: testGetValueAt2");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Object turno = mPlayerListModel.getValueAt(0, 1);
			assertTrue(turno.equals(false));
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetValueAt3() {
		System.out.println("TestCase:: testGetValueAt3");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Object conectado = mPlayerListModel.getValueAt(0, 2);
			assertTrue(conectado != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetValueAt4() {
		System.out.println("TestCase:: testGetValueAt4");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Object conectado = mPlayerListModel.getValueAt(3, 0);
			assertTrue(conectado == null);
		} catch (final IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetValueAt5() {
		System.out.println("TestCase:: testGetValueAt5");
		try {
			final PlayerListModel mPlayerListModel = new PlayerListModel(owner,
				data);
			final Object conectado = mPlayerListModel.getValueAt(0, 10);
			assertTrue(conectado == null);
		} catch (final IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException");
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
			owner = null;
			data = null;
		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}

}
