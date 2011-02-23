package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Player;

public class PlayerListModel extends AbstractTableModel {

	private static final long serialVersionUID = 3953960579591306950L;

	private static final String[] colTitles = {
			"Jugador", "Turno", "Online"
	};

	private final ArrayList<Player> data;
	private final Player selfPlayer;

	public PlayerListModel(Player selfPlayer) {
		super();
		this.selfPlayer = selfPlayer;
		data = new ArrayList<Player>();
	}

	public PlayerListModel(Player selfPlayer, ArrayList<Player> data) {
		super();
		this.selfPlayer = selfPlayer;
		this.data = new ArrayList<Player>();
		this.data.addAll(data);
		this.fireTableDataChanged();
	}

	public void setData(ArrayList<Player> data) {
		this.data.clear();
		this.data.addAll(data);
		this.fireTableDataChanged();
	}

	public void updatePlayer(Player player) {
		data.add(player);
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
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;

		switch (columnIndex) {
		case 0:
			return data.get(rowIndex).getName();
		case 1:
			return data.get(rowIndex).isHasTurn();
		case 2:
			return data.get(rowIndex).isOnline();
		default:
			return null;
		}
	}

	public Player getActivePlayer() {
		for (final Player p : data) {
			if (p.isHasTurn()) return p;
		}
		return null;
	}

	public Player getPlayerAt(int index) {
		return data.get(index);
	}

	public Player getPlayerByName(String name) {
		for (final Player p : data) {
			if (p.getName().equals(name)) return p;
		}
		return null;
	}

}
