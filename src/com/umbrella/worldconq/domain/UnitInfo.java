package com.umbrella.worldconq.domain;

/* 
 - Soldado: 100  gallifantes.
 - Cañón: 300 gallifantes.
 - Misil: 500 gallifantes.
 - Misil intercontinental: 800 gallifantes.
 - Antimisil: 400 gallifantes.
 - Espía: 600 gallifantes.
 */

public class UnitInfo {
	static final private int priceList[] = {
			100, 300, 500, 800, 400, 600
	};

	public static int getPriceSoldier() {
		return priceList[0];
	}

	public static int getPriceCannon() {
		return priceList[1];
	}

	public static int getPriceMissil() {
		return priceList[2];
	}

	public static int getPriceICBM() {
		return priceList[3];
	}

	public static int getPriceAntiMissile() {
		return priceList[4];
	}

	public static int getPricSpy() {
		return priceList[5];
	}
}
