package com.umbrella.worldconq.exceptions;

public class OcupiedTerritoryException extends Exception {
	private static final long serialVersionUID = -5058617537638675395L;

	public OcupiedTerritoryException(int index) {
		super("Index: " + index);
	}
}
