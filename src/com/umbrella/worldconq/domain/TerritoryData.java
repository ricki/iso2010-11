package com.umbrella.worldconq.domain;

import es.uclm.iso2.rmi.Territory;
import es.uclm.iso2.rmi.Territory.Continent;

public class TerritoryData {
	private final static String[] nameList = {
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
	private static int[] priceTerritory = {
			279, 197, 191, 296, 183, 288, 109, 212, 294, 298, 189, 171, 234,
			273, 247, 140, 275, 193, 261, 280, 146, 143, 211, 176, 188, 122,
			172, 285, 274, 141, 198, 236, 129, 118, 281, 116, 251, 170, 125,
			245, 124, 150
	};

	public static int getIndex(Continent c, int idx) {
		final int[] numTerritorios = {
				9, 4, 7, 6, 12, 4
		};
		final int IndexC = c.ordinal();
		int ret = 0;
		for (int i = 0; i < IndexC; i++) {
			ret += numTerritorios[i];
		}
		ret += idx;
		return ret - 1;

	}

	public static int getIndex(Territory t) {
		return getIndex(t.getContinent(), t.getIdTerritory());
	}

	public static String getName(int idx) {
		return nameList[idx];
	}

	public static String getName(Territory t) {
		return nameList[getIndex(t)];
	}

	public static int getPrice(int idx) {
		return priceTerritory[idx];
	}

	//	public static ArrayList<Integer> getAdjacentIndices(int idx) {
	//		final ArrayList<Integer> adj = new ArrayList<Integer>();
	//		return adj;
	//	}
}
