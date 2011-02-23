package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import domain.Player;
import domain.Spy;
import domain.Territory;

public class MapModel extends AbstractTableModel {

	private static final long serialVersionUID = 5679583600970671969L;

	private static final String[] colTitles = {
			"Territorio", "Jugador", "Nº Soldados", "Nº Canones 1",
			"Nº Canones 2", "Nº Canones 3", "Nº Misiles", "Nº ICBMs",
			"Nº AntiMisiles"
	};

	private final ArrayList<TerritoryDecorator> data;
	private final Player selfPlayer;

	public MapModel(Player selfPlayer, PlayerListModel playerModel) {
		super();
		data = new ArrayList<TerritoryDecorator>();
		for (int i = 0; i < 42; i++) {
			final Territory t = new Territory(i, null, null, 0, null, 0, 0, 0);
			data.add(new TerritoryDecorator(t, this, playerModel));
		}
		this.selfPlayer = selfPlayer;
	}

	public void setData(ArrayList<TerritoryDecorator> data) {
		if (data != null) {
			for (final TerritoryDecorator t : data) {
				this.updateTerritory(t);
			}
		}
	}

	public void updateTerritory(TerritoryDecorator territory) {
		if (territory != null
				&& territory.getId() >= 0
				&& territory.getId() < 42) {
			final TerritoryDecorator t = data.get(territory.getId());
			t.setPlayer(territory.getPlayer());
			t.setNumSoldiers(territory.getNumSoldiers());
			t.setNumCannons(territory.getNumCannons());
			t.setNumMissiles(territory.getNumMissiles());
			t.setNumICBMs(territory.getNumICBMs());
			t.setNumAntiMissiles(territory.getNumAntiMissiles());
			this.fireTableDataChanged();
		}
	}

	public TerritoryDecorator getTerritoryAt(int index) {
		return data.get(index);
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

		boolean hasSpy = false;
		final TerritoryDecorator t = data.get(rowIndex);

		if (t.getPlayer() != null) {

			for (final Spy s : selfPlayer.getSpies()) {
				if (s.getLocation() == rowIndex) {
					hasSpy = true;
				}
			}
			if (t.getPlayer().equals(selfPlayer) || hasSpy) {

				switch (columnIndex) {
				case 0:
					return rowIndex;
				case 1:
					return t.getPlayer().getName();
				case 2:
					return t.getNumSoldiers();
				case 3:
					return t.getNumCannons()[0];
				case 4:
					return t.getNumCannons()[1];
				case 5:
					return t.getNumCannons()[2];
				case 6:
					return t.getNumMissiles();
				case 7:
					return t.getNumICBMs();
				case 8:
					return t.getNumAntiMissiles();
				default:
					return null;
				}
			} else {
				switch (columnIndex) {
				case 0:
					return rowIndex;
				default:
					return "¿?";
				}
			}

		} else {
			switch (columnIndex) {
			case 0:
				return rowIndex;
			default:
				return "¿?";
			}
		}
	}
}
