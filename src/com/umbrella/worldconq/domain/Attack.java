package com.umbrella.worldconq.domain;

import java.util.Random;

import domain.Arsenal;

public class Attack {
	private final Arsenal arsenal;
	private final TerritoryDecorator src;
	private final TerritoryDecorator dst;
	private final int aCannons[];

	public Attack(Arsenal arsenal, TerritoryDecorator src, TerritoryDecorator dst) {
		this.src = src;
		this.dst = dst;
		this.arsenal = arsenal;

		final int srcCannons[] = src.getNumCannons();
		int arsenalCannons = arsenal.getCannons();

		aCannons = new int[3];
		aCannons[2] = arsenalCannons > srcCannons[2] ? srcCannons[2] : arsenalCannons;
		arsenalCannons = arsenalCannons - aCannons[2];

		aCannons[1] = arsenalCannons > srcCannons[1] ? srcCannons[1] : arsenalCannons;
		arsenalCannons = arsenalCannons - aCannons[1];

		aCannons[0] = arsenalCannons;
	}

	public TerritoryDecorator getOrigin() {
		return src;
	}

	public TerritoryDecorator getDestination() {
		return dst;
	}

	public Arsenal getArsenal() {
		return arsenal;
	}

	public void resolve() {

		int soldiers = dst.getNumSoldiers();
		int antiMissiles = dst.getNumAntiMissiles();

		/* Modificamos el territorio origen */

		src.setNumICBMs(src.getNumICBMs() - arsenal.getICBMs());
		src.setNumMissiles(src.getNumMissiles() - arsenal.getMissiles());
		src.setNumSoldiers(src.getNumSoldiers() - arsenal.getSoldiers());

		final int srcCannons[] = src.getNumCannons();
		for (int i = 0; i < srcCannons.length; i++)
			srcCannons[i] = srcCannons[i] - aCannons[i];
		src.setNumCannons(srcCannons);

		/* ICBM */

		if (arsenal.getICBMs() <= antiMissiles)
			antiMissiles = antiMissiles - arsenal.getICBMs();
		else {
			soldiers = soldiers - 20 * (arsenal.getICBMs() - antiMissiles);
			antiMissiles = 0;
			if (soldiers <= 0) {
				this.conquerTerritory();
				return;
			}
		}

		/* Misiles */

		if (arsenal.getMissiles() <= antiMissiles)
			antiMissiles = antiMissiles - arsenal.getMissiles();
		else {
			soldiers = soldiers - 15 * (arsenal.getMissiles() - antiMissiles);
			antiMissiles = 0;
			if (soldiers <= 0) {
				this.conquerTerritory();
				return;
			}
		}

		/* Cañones */
		final Random rand = new Random(System.currentTimeMillis());
		int t1;
		int t2;

		for (int i = 0; i < arsenal.getCannons(); i++) {
			/* Primera tirada de los cañones */
			t1 = rand.nextInt(5);
			t2 = rand.nextInt(5);
			if (t1 > t2) {
				soldiers--;
				if (soldiers <= 0) {
					this.conquerTerritory();
					return;
				}
			}

			/* Segunda tirada de los cañones */
			t1 = rand.nextInt(5);
			t2 = rand.nextInt(5);
			if (t1 > t2) {
				soldiers--;
				if (soldiers <= 0) {
					this.conquerTerritory();
					return;
				}
			}
		}

		/* Soldados */
		final int srcSodiers = arsenal.getSoldiers();
		for (int i = 0; i < srcSodiers; i++) {
			t1 = rand.nextInt(5);
			t2 = rand.nextInt(5);
			if (t1 > t2) {
				soldiers--;
				if (soldiers <= 0) {
					this.conquerTerritory();
					return;
				}
			} else
				arsenal.setSoldiers(arsenal.getSoldiers() - 1);
		}

		dst.setNumAntiMissiles(antiMissiles);
		dst.setNumSoldiers(soldiers);
	}

	private void conquerTerritory() {
		dst.setPlayer(src.getPlayer());
		dst.setNumICBMs(0);
		dst.setNumMissiles(0);
		dst.setNumAntiMissiles(0);
		final int NumCannons[] = new int[3];
		NumCannons[0] = aCannons[1];
		NumCannons[1] = aCannons[2];
		NumCannons[2] = 0;
		dst.setNumCannons(NumCannons);
		dst.setNumSoldiers(arsenal.getSoldiers());
	}
}
