package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import es.uclm.iso2.rmi.GameInfo;

public class GameListModel extends AbstractTableModel {

	private static final long serialVersionUID = 7356294494185290462L;

	private static final String[] colTitles = {
			"Nombre",
			"Descripción",
			"N° Jugadores",
			"N° Territorios Libres"
	};

	private final ArrayList<GameInfo> mGameList;

	public GameListModel() {
		super();
		mGameList = new ArrayList<GameInfo>();
	}

	public void setData(ArrayList<GameInfo> data) {
		mGameList.clear();
		mGameList.addAll(data);
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
		return mGameList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;

		switch (columnIndex) {
		case 0:
			return mGameList.get(rowIndex).getName();
		case 1:
			return mGameList.get(rowIndex).getDescription();
		case 2:
			return new Integer(mGameList.get(rowIndex).getPlayers().size());
		case 3:
			return new Integer(mGameList.get(rowIndex).getnFreeTerritories());
		default:
			return null;
		}
	}

}
