package com.umbrella.worldconq.stubserver;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 6834935516762327016L;
	
	public String userName;
	public int money;
	public boolean online;
	public boolean hasTurn;
}
