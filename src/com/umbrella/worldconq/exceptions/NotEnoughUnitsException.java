package com.umbrella.worldconq.exceptions;

public class NotEnoughUnitsException extends Exception {
	private static final long serialVersionUID = 2597697856415060245L;

	public NotEnoughUnitsException(int value, int max) {
		super("Value: " + value + ", Max: " + max);
	}
}
