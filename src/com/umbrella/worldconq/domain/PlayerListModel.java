package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Player;

public class PlayerListModel extends AbstractTableModel {

	private static final long serialVersionUID = 3953960579591306950L;

	private static final String[] colTitles = {
			"Jugador", "Turno", "Online"
	};

	private final ArrayList<Player> mPlayerList;
	private Player selfPlayer;

	public PlayerListModel(Player selfPlayer) {
		super();
		this.selfPlayer = selfPlayer;
		mPlayerList = new ArrayList<Player>();
	}

	public PlayerListModel(Player selfPlayer, ArrayList<Player> data) {
		super();
		this.selfPlayer = selfPlayer;
		mPlayerList = new ArrayList<Player>();
		mPlayerList.addAll(data);
		this.fireTableDataChanged();
	}

	public void setData(ArrayList<Player> data) {
		mPlayerList.clear();
		mPlayerList.addAll(data);
		this.fireTableDataChanged();
	}

	public void updatePlayer(Player player) {
		selfPlayer = player;
	}

	public Player getSelfPlayer() {
		return selfPlayer;
	}

	@Override
	public String getColumnName(int col) {
		return colTitles[col];
	}

	@Override
	public int getColumnCount() {
		return colTitles.length;
	}

	@Override
	public int getRowCount() {
		return mPlayerList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;

		switch (columnIndex) {
		case 0:
			return mPlayerList.get(rowIndex).getName();
		case 1:
			return mPlayerList.get(rowIndex).isHasTurn();
		case 2:
			return mPlayerList.get(rowIndex).isOnline();
		default:
			return null;
		}
	}

	public Player getActivePlayer() {
		for (final Player p : mPlayerList) {
			if (p.isHasTurn()) return p;
		}
		return null;
	}

	public Player getPlayerAt(int index) {
		return mPlayerList.get(index);
	}

	public Player getPlayerByName(String name) {
		for (final Player p : mPlayerList) {
			if (p.getName().equals(name)) return p;
		}
		return null;
	}

}
