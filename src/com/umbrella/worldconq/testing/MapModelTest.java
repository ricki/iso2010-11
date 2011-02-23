package com.umbrella.worldconq.testing;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.umbrella.worldconq.domain.MapModel;
import com.umbrella.worldconq.domain.PlayerListModel;
import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Player;
import domain.Spy;
import domain.Territory;
import domain.Territory.Continent;

public class MapModelTest extends TestCase {

	private MapModel map;
	private Player selfPlayer;
	private PlayerListModel playerList;

	private static final String[] colTitles = {
			"Territorio", "Jugador", "Nº Soldados", "Nº Canones 1",
			"Nº Canones 2", "Nº Canones 3", "Nº Misiles", "Nº ICBMs",
			"Nº AntiMisiles"
	};

	@Override
	public void setUp() {
		selfPlayer = new Player("Antonio", 10000, true, true,
			new ArrayList<Spy>());
		map = new MapModel(selfPlayer);
		final ArrayList<Player> players = new ArrayList<Player>();
		players.add(selfPlayer);
		players.add(new Player("Ambrosio", 1000, true, true,
			new ArrayList<Spy>()));
		playerList = new PlayerListModel(selfPlayer, players);

	}

	public void testMapModel() {
		System.out.println("TestCase:: testMapModel");
		for (int index = 0; index < 42; index++)
			assertTrue(map.getTerritoryAt(index) == null);
	}

	public void testSetData() {
		System.out.println("TestCase:: testSetData");
		final ArrayList<TerritoryDecorator> data = new ArrayList<TerritoryDecorator>();
		for (int index = 0; index < 42; index++) {
			data.add(index, new TerritoryDecorator(
				new Territory(index, Continent.Africa, "", 0,
					new int[3], 0, 0, 0), null,
				null));
		}
		map.setData(data);
		for (int index = 0; index < 42; index++) {
			assertTrue(map.getTerritoryAt(index) != null);
			assertTrue(map.getTerritoryAt(index).getId() == index);
		}
	}

	public void testUpdateTerritory() {
		System.out.println("TestCase:: testUpdateTerritory");
		final TerritoryDecorator territory = new TerritoryDecorator(
			new Territory(10, Continent.Africa, selfPlayer.getName(), 0,
				new int[3], 0, 0, 0), null,
			null);
		map.updateTerritory(territory);
		for (int index = 0; index < map.getRowCount(); index++) {
			if (index == 10) {
				assertTrue(map.getTerritoryAt(index) != null);
				assertTrue(map.getTerritoryAt(index).getOwner().equals(
					"Antonio"));
			} else
				assertTrue(map.getTerritoryAt(index) == null);
		}
	}

	public void testgetColumnName() {
		System.out.println("TestCase:: testgetColumnName");
		for (int index = 0; index < map.getColumnCount(); index++) {
			assertTrue(map.getColumnName(index).equals(colTitles[index]));
		}
	}

	public void testgetValueAt1() {
		System.out.println("TestCase:: testgetValueAt1");
		assertTrue(map.getValueAt(-1, 0) == null);
	}

	public void testgetValueAt2() {
		System.out.println("TestCase:: testgetValueAt2");
		assertTrue(map.getValueAt(map.getRowCount(), 0) == null);
	}

	public void testgetValueAt3() {
		System.out.println("TestCase:: testgetValueAt3");
		assertTrue(map.getValueAt(0, -1) == null);
	}

	public void testgetValueAt4() {
		System.out.println("TestCase:: testgetValueAt4");
		assertTrue(map.getValueAt(0, map.getColumnCount()) == null);
	}

	public void testgetValueAt5() {
		System.out.println("TestCase:: testgetValueAt5");

		final TerritoryDecorator T1 = new TerritoryDecorator(new Territory(0,
			Continent.Africa, "Antonio", 10, new int[] {
					0, 0, 0
			}, 0, 0, 0), null, playerList);

		map.updateTerritory(T1);

		final TerritoryDecorator T2 = new TerritoryDecorator(new Territory(14,
			Continent.Africa, "Ambrosio", 8, new int[] {
					4, 9, 2
			}, 3, 9, 0), null, playerList);

		map.updateTerritory(T2);
		final TerritoryDecorator T3 = new TerritoryDecorator(new Territory(6,
			Continent.Africa, "", 9, new int[] {
					0, 0, 0
			}, 0, 0, 0), null, playerList);

		map.updateTerritory(T3);

		assertTrue(map.getValueAt(14, 1).equals("¿?"));
		assertTrue(map.getValueAt(14, 0).equals(14));
		assertTrue(map.getValueAt(6, 0).equals(6));
		assertTrue(map.getValueAt(6, 2).equals("¿?"));

		/* Se depliega un espia */

		final ArrayList<Spy> spys = new ArrayList<Spy>();
		spys.add(new Spy(14, 1));
		selfPlayer.setSpies(spys);
		playerList.updatePlayer(selfPlayer);
		assertTrue(map.getValueAt(14, 0).equals(14));
		assertTrue(map.getValueAt(14, 1).equals("Ambrosio"));
		assertTrue(map.getValueAt(14, 2).equals(8));
		assertTrue(map.getValueAt(14, 3).equals(4));
		assertTrue(map.getValueAt(14, 4).equals(9));
		assertTrue(map.getValueAt(14, 5).equals(2));
		assertTrue(map.getValueAt(14, 6).equals(3));
		assertTrue(map.getValueAt(14, 7).equals(9));
		assertTrue(map.getValueAt(14, 8).equals(0));
	}

	@Override
	public void tearDown() {
	}

}
