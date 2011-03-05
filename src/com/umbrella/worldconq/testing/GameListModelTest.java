package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.domain.GameListModel;

import domain.GameInfo;

public class GameListModelTest extends TestCase {
	Process ServerProcess;
	BufferedReader in;

	private GameInfo game1;
	private GameInfo game2;
	private ArrayList<GameInfo> mGameList;

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

			game1 = new GameInfo();
			game1.setName("prueba");
			game1.setDescription("descripción prueba");
			game1.setPlayers(new ArrayList<String>());
			game2 = new GameInfo();
			game2.setName("prueba 2");
			game2.setDescription("descripción prueba 2");
			mGameList = new ArrayList<GameInfo>();
			mGameList.add(game1);
			mGameList.add(game2);

		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetColumnName1() {
		System.out.println("TestCase::testGetColumnName1");

		try {
			final GameListModel mGameListModel = new GameListModel();
			final String nombre = mGameListModel.getColumnName(0);
			assertTrue(nombre != null);
			System.out.println(nombre);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetColumnName2() {
		System.out.println("TestCase::testGetColumnName2");

		try {
			final GameListModel mGameListModel = new GameListModel();
			final String nombre = mGameListModel.getColumnName(-1);
			fail("Esperaba Exception");
			System.out.println(nombre);
		} catch (final Exception e) {

		}
	}

	public void testGetColumnName3() {
		System.out.println("TestCase::testGetColumnName3");
		try {
			final GameListModel mGameListModel = new GameListModel();
			final String nombre = mGameListModel.getColumnName(6);
			fail("Esperaba Exception");
			System.out.println(nombre);
		} catch (final Exception e) {

		}
	}

	public void testGetColumnCount() {
		System.out.println("TestCase::testGetColumnCount");
		try {
			final GameListModel mGameListModel = new GameListModel();
			final int numero = mGameListModel.getColumnCount();
			assertTrue(numero >= 0);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testSetData1() {
		System.out.println("TestCase::testSetData1");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			assertTrue(mGameListModel != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testSetData2() {
		System.out.println("TestCase::testSetData2");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(null);
			fail("Se esperaba excepcion");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testGetGameAt1() {
		System.out.println("TestCase::testGetGameAt1");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final GameInfo game = mGameListModel.getGameAt(0);
			assertTrue(game != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetGameAt2() {
		System.out.println("TestCase::testGetGameAt2");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.getGameAt(20);
			fail("Esperaba Exception");
		} catch (final Exception e) {

		}
	}

	public void testGetRowCount() {
		System.out.println("TestCase::testGetRowCount");
		try {
			final GameListModel mGameListModel = new GameListModel();
			final int before = mGameListModel.getRowCount();
			assertTrue(before == 0);
			mGameListModel.setData(mGameList);
			final int after = mGameListModel.getColumnCount();
			assertTrue(after > 0);
			assertTrue(before < after);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt1() {
		System.out.println("TestCase::testGetValueAt1");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(0, 0);
			assertTrue(result != null);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt2() {
		System.out.println("TestCase::testGetValueAt2");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(1, 10);
			assertTrue(result == null);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt3() {
		System.out.println("TestCase::testGetValueAt3");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(10, 1);
			assertTrue(result == null);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt4() {
		System.out.println("TestCase::testGetValueAt4");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(10, 10);
			assertTrue(result == null);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt5() {
		System.out.println("TestCase::testGetValueAt5");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(1, 1);
			assertTrue(result != null);
		} catch (final Exception e) {

		}
	}

	public void testGetValueAt6() {
		System.out.println("TestCase::testGetValueAt6");
		try {
			final GameListModel mGameListModel = new GameListModel();
			mGameListModel.setData(mGameList);
			final Object result = mGameListModel.getValueAt(0, 2);
			assertTrue(result != null);
		} catch (final Exception e) {

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
			mGameList = null;
		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}
}
