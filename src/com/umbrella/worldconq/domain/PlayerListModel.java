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

		if (selfPlayer == null)
			throw new NullPointerException();

		this.selfPlayer = selfPlayer;
		data = new ArrayList<Player>();
		data.add(selfPlayer);
		this.fireTableDataChanged();
	}

	public PlayerListModel(Player selfPlayer, ArrayList<Player> data) {
		super();

		if (selfPlayer == null)
			throw new NullPointerException();

		this.selfPlayer = selfPlayer;
		this.data = new ArrayList<Player>();

		this.updatePlayer(selfPlayer);
		this.setData(data);
	}

	public void setData(ArrayList<Player> data) {
		if (data != null) {
			for (final Player p : data) {
				this.updatePlayer(p);
			}
		}
	}

	public void updatePlayer(Player player) {
		if (player != null) {
			final int pos = data.indexOf(player);
			if (pos >= 0) {
				final Player p = data.get(pos);
				p.setMoney(player.getMoney());
				p.setOnline(player.isOnline());
				p.setHasTurn(player.isHasTurn());
				p.setSpies(player.getSpies());
			} else {
				data.add(player);
			}
			this.fireTableDataChanged();
		}
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
		final Player p = data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return p.getName();
		case 1:
			return p.isHasTurn();
		case 2:
			return p.isOnline();
		default:
			throw new IndexOutOfBoundsException("Index: " + columnIndex
					+ ", Size: " + this.getColumnCount());
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
