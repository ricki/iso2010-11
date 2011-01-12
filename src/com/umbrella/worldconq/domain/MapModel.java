package com.umbrella.worldconq.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import es.uclm.iso2.rmi.Territory;

public class MapModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final String[] colTitles = {
			"Territorio",
			"Jugador",
			"Nº Soldados",
			"Nº Canones 1",
			"Nº Canones 2",
			"Nº Canones 3",
			"Nº Misiles",
			"Nº ICBMs",
			"Nº AntiMisiles"
	};

	private final ArrayList<Territory> mMapList;

	public MapModel() {
		super();
		mMapList = new ArrayList<Territory>();
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

	public String getRowInfo(int idx) {
		final String ret = "<html>\n<P ALIGN=\"center\"><BIG>"
				+ TerritoryData.getName(idx)
				+ "</BIG><BR>\n<B> Controlado por: <EM>"
				+ this.getValueAt(idx, 1)
				+ "</em></b></P>\n<HR>\n<P ALIGN=\"right\">\nSoldados: "
				+ this.getValueAt(idx, 2)
				+ "<BR>\nCañones Tipo 1: " + this.getValueAt(idx, 3)
				+ "<BR>\nCañones Tipo 2: " + this.getValueAt(idx, 4)
				+ "<BR>\nCañones Tipo 3: " + this.getValueAt(idx, 5)
				+ "<BR>\nMisiles: " + this.getValueAt(idx, 6)
				+ "<BR>\nICBMs: " + this.getValueAt(idx, 7)
				+ "<BR>\nAntimisiles: " + this.getValueAt(idx, 8)
				+ "<BR>\n</P>";
		return ret;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;

		switch (columnIndex) {
		case 0:
			return TerritoryData.getIndex(
				mMapList.get(rowIndex).getContinent(),
				mMapList.get(rowIndex).getIdTerritory());

		case 1:
			if (mMapList.get(rowIndex).getOwner() == null)
				return null;
			else
				return mMapList.get(rowIndex).getOwner().getName();
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
	}

}
