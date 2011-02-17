package com.umbrella.worldconq.domain;

import domain.Arsenal;

public class Attack {
	private final Arsenal arsenal;
	private final TerritoryDecorator src;
	private final TerritoryDecorator dst;

	public Attack(Arsenal arsenal, TerritoryDecorator src, TerritoryDecorator dst) {
		this.src = src;
		this.dst = dst;
		this.arsenal = arsenal;
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
}
