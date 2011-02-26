package com.umbrella.worldconq.testing;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.umbrella.worldconq.domain.MapModel;
import com.umbrella.worldconq.domain.PlayerListModel;
import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Player;
import domain.Spy;
import domain.Territory;

public class TerritoryDecoratorTest extends TestCase {

	private MapModel map;
	private Player selfPlayer;
	private PlayerListModel playerList;

	@Override
	public void setUp() {
		selfPlayer = new Player("Antonio", 10000, true, true,
			new ArrayList<Spy>());

		final ArrayList<Player> players = new ArrayList<Player>();
		players.add(selfPlayer);
		playerList = new PlayerListModel(selfPlayer, players);

		map = new MapModel(selfPlayer, playerList);

		final TerritoryDecorator T1 = new TerritoryDecorator(new Territory(0,
			null, "Antonio", 10, new int[] {
					0, 0, 0
			}, 0, 0, 0), map, playerList);

		map.updateTerritory(T1);

		final TerritoryDecorator T2 = new TerritoryDecorator(new Territory(14,
			null, "Ambrosio", 8, new int[] {
					4, 9, 2
			}, 3, 9, 0), map, playerList);

		map.updateTerritory(T2);

		final TerritoryDecorator T3 = new TerritoryDecorator(new Territory(6,
			null, null, 9, new int[] {
					0, 0, 0
			}, 0, 0, 0), map, playerList);

		map.updateTerritory(T3);
	}

	public void testgetclone1() {
		final TerritoryDecorator T1C = (TerritoryDecorator) map.getTerritoryAt(
			0).clone();
		assertEquals(map.getTerritoryAt(0), T1C);
		assertEquals(map.getTerritoryAt(0).getId(), T1C.getId());

		final TerritoryDecorator T2C = (TerritoryDecorator) map.getTerritoryAt(
			14).clone();
		assertEquals(map.getTerritoryAt(14), T2C);
		assertEquals(map.getTerritoryAt(14).getId(), T2C.getId());

		final TerritoryDecorator T3C = (TerritoryDecorator) map.getTerritoryAt(
			6).clone();
		assertEquals(map.getTerritoryAt(6), T3C);
		assertEquals(map.getTerritoryAt(6).getId(), T3C.getId());
	}

	public void testgetEqual1() {
		assertNotSame(map.getTerritoryAt(6), map.getTerritoryAt(14));
		assertNotSame(map.getTerritoryAt(0), map.getTerritoryAt(14));
	}

	public void testgetName1() {
		assertNotSame(map.getTerritoryAt(6).getName(),
			map.getTerritoryAt(14).getName());
		assertNotSame(map.getTerritoryAt(0), map.getTerritoryAt(14));
	}

	public void testgetAdjacentTerritories1() {

		assertTrue(map.getTerritoryAt(0).getAdjacentTerritories().contains(
			map.getTerritoryAt(1)));

		assertFalse(map.getTerritoryAt(0).getAdjacentTerritories().contains(
			map.getTerritoryAt(30)));

		assertTrue(map.getTerritoryAt(6).getAdjacentTerritories().contains(
			map.getTerritoryAt(0)));

		assertFalse(map.getTerritoryAt(6).getAdjacentTerritories().contains(
			map.getTerritoryAt(17)));
	}

	@Override
	public void tearDown() {
	}
}
