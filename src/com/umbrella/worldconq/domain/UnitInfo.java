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
	static final private int costList[] = {
			100, 300, 500, 800, 400, 600
	};

	public static int getSoldierCost() {
		return costList[0];
	}

	public static int getCannonCost() {
		return costList[1];
	}

	public static int getMissileCost() {
		return costList[2];
	}

	public static int getICBMCost() {
		return costList[3];
	}

	public static int getAntiMissileCost() {
		return costList[4];
	}

	public static int getSpyCost() {
		return costList[5];
	}
}
