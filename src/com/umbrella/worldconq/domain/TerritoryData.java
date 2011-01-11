package com.umbrella.worldconq.domain;

import es.uclm.iso2.rmi.Territory;
import es.uclm.iso2.rmi.Territory.Continent;

public class TerritoryData {
	private final static String[] nameList = {
			"Alaska", "Alberta", "América Central", "Estados Unidos del Este",
			"Groenlandia", "Territorios del Noroeste", "Ontario", "Quebec",
			"Estados Unidos del Oeste", "Argentina", "Brasil", "Perú",
			"Venezuela", "Gran Bretaña", "Iceland", "Europa del Norte",
			"Escandinavia", "Europa del Sur", "Ucrania",
			"Europa Occidental", "Congo", "África Oriental", "Egipto",
			"Madagascar", "África del Norte", "Sudáfrica", "Afghanistan",
			"China", "India", "Irkutsk", "Japón", "Kamchatka", "Oriente Medio",
			"Mongolia", "Siam", "Siberia", "Ural", "Yakutsk",
			"Australia Oriental", "Indonesia", "Nueva Guinea",
			"Australia Occidental"
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

	//public static ArrayList<int> getAdjacentIndices(int idx){
	//	Falta por hacer
	//}
}
