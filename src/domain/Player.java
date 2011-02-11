package domain;

import java.io.Serializable;
import java.util.ArrayList;

// ESTO ES PLAYER DE LOS CLIENTES
public class Player implements Serializable {

	private static final long serialVersionUID = -282411403193425543L;

	private String name;
	private int money;
	private boolean online;
	private boolean hasTurn;
	private ArrayList<Spy> spies;

	public Player(String name, int money) {
		this.setSpies(spies);
		this.setOnline(false);
		this.setHasTurn(false);
		this.setMoney(money);

		this.setName(name);

	}

	public Player(String name) {
		this.setName(name);

	}

	public Player(String name, int money, boolean online, boolean hasTurn,
			ArrayList<Spy> spies) {
		this.setSpies(spies);
		this.setOnline(online);
		this.setHasTurn(hasTurn);
		this.setMoney(money);

		this.setName(name);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isHasTurn() {
		return hasTurn;
	}

	public void setHasTurn(boolean hasTurn) {
		this.hasTurn = hasTurn;
	}

	public ArrayList<Spy> getSpies() {
		return spies;
	}

	public void setSpies(ArrayList<Spy> spies) {
		this.spies = spies;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Player))
			return false;
		Player p = (Player) o;
		return name.equals(p.getName());
	}

}
