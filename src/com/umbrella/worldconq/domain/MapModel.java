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

	private final ArrayList<Territory> mMapList;
	private final Player mPlayer;

	public MapModel(Player player) {
		super();
		mMapList = new ArrayList<Territory>();
		mPlayer = player;
	}

	public void setData(ArrayList<Territory> data) {
		mMapList.clear();
		mMapList.addAll(data);
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
		return mMapList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;

		boolean hasSpy = false;

		if (mMapList.get(rowIndex).getOwner() != null) {

			for (final Spy s : mPlayer.getSpies()) {
				if (s.getLocation() == rowIndex) {
					hasSpy = true;
				}
			}
			if (mMapList.get(rowIndex).getOwner().equals(
				mPlayer.getName())
					|| hasSpy) {

				switch (columnIndex) {
				case 0:
					return TerritoryData.getIndex(
						mMapList.get(rowIndex).getContinent(), mMapList.get(
						rowIndex).getIdTerritory());

				case 1:
					return mMapList.get(rowIndex).getOwner();
				case 2:
					return mMapList.get(rowIndex).getNumSoldiers();
				case 3:
					return mMapList.get(rowIndex).getNumCannons()[0];
				case 4:
					return mMapList.get(rowIndex).getNumCannons()[1];
				case 5:
					return mMapList.get(rowIndex).getNumCannons()[2];
				case 6:
					return mMapList.get(rowIndex).getNumMissiles();
				case 7:
					return mMapList.get(rowIndex).getNumICBMs();
				case 8:
					return mMapList.get(rowIndex).getNumAntiMissiles();
				default:
					//mirar esto que no sabemos bien que poner
					return null;
				}
			} else {
				switch (columnIndex) {
				case 0:
					return TerritoryData.getIndex(
						mMapList.get(rowIndex).getContinent(), mMapList.get(
						rowIndex).getIdTerritory());
				default:
					return "¿?";
				}
			}

		} else {
			switch (columnIndex) {
			case 0:
				return TerritoryData.getIndex(
					mMapList.get(rowIndex).getContinent(), mMapList.get(
					rowIndex).getIdTerritory());
			default:
				return "¿?";
			}
		}
	}
}
