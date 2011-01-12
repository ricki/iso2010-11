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
	private static int[] colorTerritory = {
			-6382080, -4938639, -128, -9342720, -256, -10987477, -7039927,
			-5784176, -13356007, -65536, -8372160, -8388608, -32640, -16750593,
			-16724737, -16776961, -16744193, -16760704, -16777088, -10452737,
			-5351680, -32768, -10670336, -6001920, -28325, -8372224, -8323129,
			-13673450, -16744320, -5439744, -8323328, -16744384, -16744448,
			-16760832, -8323200, -16735488, -16766464, -16735360, -12582848,
			-8388353, -65281, -8388544
	};

	//color agua -7228984

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

	public static int getIndex(int color) {
		int ret = -1;
		for (int i = 0; i < colorTerritory.length; i++) {
			if (color == colorTerritory[i]) ret = i;
		}
		return ret;
	}

	//	public static ArrayList<Integer> getAdjacentIndices(int idx) {
	//		final ArrayList<Integer> adj = new ArrayList<Integer>();
	//		return adj;
	//	}
}
