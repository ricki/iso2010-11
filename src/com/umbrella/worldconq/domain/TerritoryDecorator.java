package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import domain.Player;
import domain.Territory;

public class TerritoryDecorator extends domain.Territory implements Cloneable {
	private static final long serialVersionUID = 1L;
	private final String namelist[] = {
			"Gran Bretaña", "Iceland", "Europa del Norte", "Escandinavia",
			"Europa del Sur", "Ucrania", "Europa Occidental", "Afghanistan",
			"China", "India", "Irkutsk", "Japón", "Kamchatka", "Oriente Medio",
			"Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Congo",
			"África Oriental", "Egipto", "Madagascar", "África del Norte",
			"Sudáfrica", "Alaska", "Alberta", "América Central",
			"Estados Unidos del Este", "Groenlandia",
			"Territorios del Noroeste", "Ontario", "Quebec",
			"Estados Unidos del Oeste", "Argentina", "Brasil", "Perú",
			"Venezuela", "Australia Oriental", "Indonesia", "Nueva Guinea",
			"Australia Occidental"
	};
	private final int pricelist[] = {
			279, 197, 191, 296, 183, 288, 109, 212, 294, 298, 189, 171, 234,
			273, 247, 140, 275, 193, 261, 280, 146, 143, 211, 176, 188, 122,
			172, 285, 274, 141, 198, 236, 129, 118, 281, 116, 251, 170, 125,
			245, 124, 150
	};
	private final int adjgraph[][] = {
			/* 1-Gran Bretaña */{
					1, 2, 3, 6
			},
						/* 2-Iceland */{
					0, 29, 3
			},
						/* 3-Europa del Norte */{
					0, 3, 6, 4, 5
			},
						/* 4-Escandinavia */{
					0, 1, 2, 5
			},
						/* 5-Europa del Sur */{
					2, 5, 6, 21, 23
			},
						/* 6-Ucrania */{
					2, 3, 4, 7, 13, 17
			},
						/* 7-Europa Occidental */{
					0, 2, 4, 23
			},

			/* 8-Afghanistan */{
					8, 9, 5, 13, 17
			},
						/* 9-China */{
					7, 9, 14, 15, 16, 17
			},
						/* 10-India */{
					7, 8, 13, 15
			},
						/* 11-Irkutsk */{
					16, 14, 12, 18
			},
						/* 12-Japón */{
					14, 12
			},
						/* 13-Kamchatka */{
					10, 11, 14, 18, 25
			},
						/* 14-Oriente Medio */{
					7, 9, 4, 5, 21
			},
						/* 15-Mongolia */{
					8, 10, 11, 12, 16
			},
						/* 16-Siam */{
					8, 9, 39
			},
						/* 17-Siberia */{
					8, 10, 14, 17, 18
			},
						/* 18-Ural */{
					7, 8, 16, 5
			},
						/* 19-Yakutsk */{
					12, 16, 10
			},
						/* 20-Congo */{
					20, 23, 24
			},
						/* 21-África Oriental */{
					19, 21, 22, 23, 24
			},
						/* 22-Egipto */{
					20, 23, 13,
					4
			},
						/* 23-Madagascar */{
					20, 24
			},
						/* 24-África del Norte */{
					19, 21, 20, 4, 6
			},
						/* 25-Sudáfrica */{
					19, 20, 22
			},
						/* 26-Alaska */{
					12, 30, 31, 33
			},
						/* 27-Alberta */{
					25, 30, 31, 33
			},
						/* 28-América Central */{
					28, 33, 37
			},
						/* 29-Estados Unidos del Este */{
					31, 32, 33, 27
			},
						/* 30-Groenlandia */{
					1, 30, 31, 32
			},
						/* 31-Territorios del Noroeste */{
					25, 26, 31, 29
			},
						/* 32-Ontario */{
					26, 29, 33, 30, 32, 28
			},
						/* 33-Quebec */{
					31, 29, 28
			},
						/* 34-Estados Unidos del Oeste */{
					26, 31, 28, 27
			},
						/* 35-Argentina */{
					36, 35
			},
						/* 36-Brasil */{
					34, 36, 37
			},
						/* 37-Perú */{
					34, 37, 35
			},
						/* 38-Venezuela */{
					27, 35, 36
			},
						/* 39-Australia Oriental */{
					40, 41
			},
						/* 40-Indonesia */{
					15, 40, 41
			},
						/* 41-Nueva Guinea */{
					39, 41, 38
			},
						/* 42-Australia Occidental */{
					38, 39, 40
			}
	};

	private final PlayerListModel playerList;
	private Territory decoratedTerritory;
	private final MapModel map;

	public TerritoryDecorator(Territory t, MapModel map, PlayerListModel playerList) {
		super(0, null, null, 0, null, 0, 0, 0);
		decoratedTerritory = t;
		this.map = map;
		this.playerList = playerList;
	}

	public Territory getDecoratedTerritory() {
		return decoratedTerritory;
	}

	public void setDecoratedTerritory(Territory decoratedTerritory) {
		this.decoratedTerritory = decoratedTerritory;
	}

	@Override
	public int getIdTerritory() {
		return decoratedTerritory.getIdTerritory();
	}

	@Override
	public void setIdTerritory(int id) {
		if (decoratedTerritory != null)
			decoratedTerritory.setIdTerritory(id);
	}

	@Override
	public Continent getContinent() {
		return decoratedTerritory.getContinent();
	}

	@Override
	public void setContinent(Continent continent) {
		if (decoratedTerritory != null)
			decoratedTerritory.setContinent(continent);
	}

	@Override
	public int getNumSoldiers() {
		return decoratedTerritory.getNumSoldiers();
	}

	@Override
	public void setNumSoldiers(int numSoldiers) {
		if (decoratedTerritory != null)
			decoratedTerritory.setNumSoldiers(numSoldiers);
	}

	@Override
	public int[] getNumCannons() {
		return decoratedTerritory.getNumCannons();
	}

	public int getNumTotalCannons() {
		int shots = 0;
		for (int i = 0; i < decoratedTerritory.getNumCannons().length; i++)
			shots = shots + decoratedTerritory.getNumCannons()[i];

		return shots;
	}

	@Override
	public void setNumCannons(int[] numCannons) {
		if (decoratedTerritory != null)
			decoratedTerritory.setNumCannons(numCannons);
	}

	@Override
	public int getNumMissiles() {
		return decoratedTerritory.getNumMissiles();
	}

	@Override
	public void setNumMissiles(int numMissiles) {
		if (decoratedTerritory != null)
			decoratedTerritory.setNumMissiles(numMissiles);
	}

	@Override
	public int getNumICBMs() {
		return decoratedTerritory.getNumICBMs();
	}

	@Override
	public void setNumICBMs(int numICBMs) {
		if (decoratedTerritory != null)
			decoratedTerritory.setNumICBMs(numICBMs);
	}

	@Override
	public int getNumAntiMissiles() {
		return decoratedTerritory.getNumAntiMissiles();
	}

	@Override
	public void setNumAntiMissiles(int numAntiMissiles) {
		if (decoratedTerritory != null)
			decoratedTerritory.setNumAntiMissiles(numAntiMissiles);
	}

	@Override
	public void setOwner(String owner) {
		if (decoratedTerritory != null)
			decoratedTerritory.setOwner(owner);
	}

	@Override
	public String getOwner() {
		return decoratedTerritory.getOwner();
	}

	public int getId() {
		return decoratedTerritory.getIdTerritory();
	}

	public void setId(int id) {
		if (decoratedTerritory != null)
			decoratedTerritory.setIdTerritory(id);
	}

	public String getName() {
		return namelist[decoratedTerritory.getIdTerritory()];
	}

	public Player getPlayer() {
		Player p = null;
		final String name = decoratedTerritory.getOwner();
		if (name != null) {
			p = playerList.getPlayerByName(name);
		}
		return p;
	}

	public void setPlayer(Player p) {
		if (decoratedTerritory != null)
			decoratedTerritory.setOwner(p.getName());
	}

	public int getPrice() {
		return pricelist[decoratedTerritory.getIdTerritory()];
	}

	public ArrayList<TerritoryDecorator> getAdjacentTerritories() {
		final ArrayList<TerritoryDecorator> adjlist = new ArrayList<TerritoryDecorator>();
		for (final int i : adjgraph[this.getId()]) {
			adjlist.add(map.getTerritoryAt(i));
		}
		return adjlist;
	}

	private Object cloneTerritory() {
		return new Territory(decoratedTerritory.getIdTerritory(),
			decoratedTerritory.getContinent(), decoratedTerritory.getOwner(),
			decoratedTerritory.getNumSoldiers(),
			decoratedTerritory.getNumCannons(),
			decoratedTerritory.getNumMissiles(),
			decoratedTerritory.getNumICBMs(),
			decoratedTerritory.getNumAntiMissiles());
	}

	@Override
	public Object clone() {
		return new TerritoryDecorator((Territory) this.cloneTerritory(), map,
			playerList);
	}
}
