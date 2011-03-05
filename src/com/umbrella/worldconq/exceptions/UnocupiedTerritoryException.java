package com.umbrella.worldconq.exceptions;

public class UnocupiedTerritoryException extends Exception {
	private static final long serialVersionUID = -2843328928804941825L;

	public UnocupiedTerritoryException(int index) {
		super("Index: " + index);
	}
}
