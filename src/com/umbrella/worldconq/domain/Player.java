package com.umbrella.worldconq.domain;

public class Player {
	private int mMoney;
	private User mUser;

	public Player(int money, User user) {
		setMoney(money);
		setUser(user);
	}

	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int money) {
		mMoney = money;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		mUser = user;
	}
	
	public String toString() {
		return getMoney() + "," + getUser().toString();
	}

}
