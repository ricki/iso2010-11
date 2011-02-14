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

	public PlayerListModel() {
		super();
		mPlayerList = new ArrayList<Player>();
	}

	public void setData(ArrayList<Player> data) {
		mPlayerList.clear();
		mPlayerList.addAll(data);
		this.fireTableDataChanged();
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

	public Player getPlayerAt(int index) {
		return mPlayerList.get(index);
	}

	public Player getPlayerByName(String name) {
		Player res = null;
		for (final Player p : mPlayerList) {
			if (p.getName().equals(name)) res = p;
		}
		return res;
	}

	// falta poner public void updatePlayer(Player player){}
}
