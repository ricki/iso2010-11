package com.umbrella.worldconq.domain;

public class Player {
	private String mUserName;
	private int mMoney;
//	private User mUser;
	private boolean mOnline;

	public Player(String userName, int money, boolean online) {
		setUserName(userName);
		setMoney(money);
//		setUser(user);
		setOnline(online);
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getUserName() {
		return mUserName;
	}

	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int money) {
		mMoney = money;
	}

//	public User getUser() {
//		return mUser;
//	}

//	public void setUser(User user) {
//		mUser = user;
//	}
	
	public void setOnline(boolean online) {
		this.mOnline = online;
	}

	public boolean isOnline() {
		return mOnline;
	}

	public String toString() {
		return getMoney() + "," + getUserName();
	}

}
