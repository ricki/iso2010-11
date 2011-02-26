package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.umbrella.worldconq.exceptions.InvalidArgumentException;

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
		data.add(selfPlayer);
		this.fireTableDataChanged();
	}

	public PlayerListModel(Player selfPlayer, ArrayList<Player> data) throws InvalidArgumentException {
		super();
		if (selfPlayer == null || data == null) throw new InvalidArgumentException();
		this.selfPlayer = selfPlayer;
		this.data = new ArrayList<Player>();

		this.updatePlayer(selfPlayer);
		this.setData(data);
	}

	public void setData(ArrayList<Player> data) throws InvalidArgumentException {
		if (data != null) {
			for (final Player p : data) {
				this.updatePlayer(p);
			}
		} else {
			throw new InvalidArgumentException();
		}
	}

	public void updatePlayer(Player player) {
		if (player == null) return;

		boolean found = false;

		for (final Player p : data) {
			if (p.equals(player)) {
				found = true;
				p.setMoney(player.getMoney());
				p.setOnline(player.isOnline());
				p.setHasTurn(player.isHasTurn());
				p.setSpies(player.getSpies());
				break;
			}
		}

		if (!found) {
			data.add(player);
		}

		this.fireTableDataChanged();
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
		if (name != null) {
			for (final Player p : data) {
				if (p.getName().equals(name)) return p;
			}
		}
		return null;

	}
}
