package com.umbrella.worldconq.ui;

import java.io.IOException;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JEditorPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.umbrella.worldconq.domain.MapModel;

public class TerritoryInfoView extends JEditorPane implements
		TableModelListener {

	private static final long serialVersionUID = -6746009312601675672L;

	private final MapModel mMapm;
	private ListSelectionModel lsm;
	private JEditorPane infoPlayer;

	public TerritoryInfoView(MapModel mm) throws IOException {
		super();
		mMapm = mm;
		lsm = new DefaultListSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mMapm.addTableModelListener(this);
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		final int idx = lsm.getMinSelectionIndex();
		if ((idx != -1) && (mMapm.getTerritoryAt(idx) != null)) {
			final String ret = "<html>\n<P ALIGN=\"center\"><BIG>"
					+ mMapm.getTerritoryAt(idx).getName()
					+ "</BIG><BR>\n<B> Controlado por: <EM>"
					+ mMapm.getValueAt(idx, 1)
					+ "</em></b></P>\n<HR>"
					+ "<TABLE BORDER=0>"
					+ "<TR><TD Align=\"right\">Soldados:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 2)
					+ "<TR><TD Align=\"right\">Cañones Tipo 1:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 3)
					+ "<TR><TD Align=\"right\">Cañones Tipo 2:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 4)
					+ "<TR><TD Align=\"right\">Cañones Tipo 3:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 5)
					+ "<TR><TD Align=\"right\">Misiles:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 6)
					+ "<TR><TD Align=\"right\">ICBMs:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 7)
					+ "<TR><TD Align=\"right\">Antimisiles:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 8)
					+ "<TR><TD Align=\"right\">Precio:<TD Align=\"center\">"
					+ mMapm.getValueAt(idx, 9)
					+ "</TABLE>\n</P>";
			this.getInfoPlayer().setContentType("text/html");
			this.getInfoPlayer().setText(ret);
		} else {
			//this.setPais(null);
			this.getInfoPlayer().setText("");
		}

	}

	public void setInfoPlayer(JEditorPane infoPlayer) {
		infoPlayer.setEditable(false);
		this.infoPlayer = infoPlayer;
	}

	public JEditorPane getInfoPlayer() {
		return infoPlayer;
	}

	public ListSelectionModel getListSelectionModel() {
		return lsm;
	}

	public void setListSelectionModel(ListSelectionModel lsm) {
		this.lsm = lsm;
	}
}
