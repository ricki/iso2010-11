package com.umbrella.worldconq.testing;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.umbrella.worldconq.domain.MapModel;
import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Player;
import domain.Territory;
import domain.Territory.Continent;

public class MapModelTest extends TestCase {

	private MapModel map;
	private Player selfPlayer;

	private static final String[] colTitles = {
			"Territorio", "Jugador", "Nº Soldados", "Nº Canones 1",
			"Nº Canones 2", "Nº Canones 3", "Nº Misiles", "Nº ICBMs",
			"Nº AntiMisiles"
	};

	@Override
	public void setUp() {
		selfPlayer = new Player("Antonio", 100000);
		map = new MapModel(selfPlayer);
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
		for (int index = 0; index < map.getColumnCount(); index++) {
			assertTrue(map.getColumnName(index).equals(colTitles[index]));
		}
	}

	public void testgetValueAt1() {
		assertTrue(map.getValueAt(-1, 0) == null);
	}

	public void testgetValueAt2() {
		assertTrue(map.getValueAt(-1, map.getRowCount()) == null);
	}

	@Override
	public void tearDown() {
	}

}
