package com.umbrella.worldconq.domain;

public class Player {
	private int Money;
	private User User;

	public Player(int money, User user) {
		setMoney(money);
		setUser(user);
	}

	public int getMoney() {
		return Money;
	}

	public void setMoney(int money) {
		Money = money;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}
	
	public String toString() {
		return getMoney() + "," + getUser().toString();
	}

}
