package com.umbrella.worldconq.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import com.umbrella.worldconq.domain.TerritoryData;

public class MapView extends JComponent {

	private static final long serialVersionUID = 1L;

	private java.awt.Image fondo;
	private java.awt.Image pais;
	private int x;
	private int y;
	private final TableModel dm;
	protected ListSelectionModel lsm;
	private JEditorPane infoPlayer;

	private static int[] xTerritory = {
			514, 492, 568, 579, 581, 632, 521, 728, 839, 793, 877, 1069, 1008,
			657, 877, 931, 797, 746, 883, 591, 645, 593, 730, 483, 602, 5, 100,
			93, 132, 375, 96, 190, 265, 80, 264, 247, 214, 224, 1073, 943,
			1083, 1009
	};
	private static int[] yTerritory = {
			102, 69, 105, 51, 130, 53, 132, 114, 140, 188, 78, 157, 54, 172,
			120, 234, 26, 45, 40, 312, 257, 212, 418, 193, 389, 50, 95, 215,
			140, 8, 38, 95, 85, 142, 442, 339, 355, 306, 413, 271, 351, 425
	};
	private static String[] imageTerritory = {
			"eu1sel.png", "eu2sel.png", "eu3sel.png", "eu4sel.png",
			"eu5sel.png", "eu6sel.png", "eu7sel.png", "as1sel.png",
			"as2sel.png", "as3sel.png", "as4sel.png", "as5sel.png",
			"as6sel.png", "as7sel.png", "as8sel.png", "as9sel.png",
			"as10sel.png", "as11sel.png", "as12sel.png", "af1sel.png",
			"af2sel.png", "af3sel.png", "af4sel.png", "af5sel.png",
			"af6sel.png", "na1sel.png", "na2sel.png", "na3sel.png",
			"na4sel.png", "na5sel.png", "na6sel.png", "na7sel.png",
			"na8sel.png", "na9sel.png", "sa1sel.png", "sa2sel.png",
			"sa3sel.png", "sa4sel.png", "oc1sel.png", "oc2sel.png",
			"oc3sel.png", "oc4sel.png"
	};
	private static int[] colorTerritory = {
			-16750593, -16724737, -16776961, -16153347, -16760704, -16777088,
			-10452737, -8323129, -13673450, -16744320, -5439744, -8323328,
			-16744384, -16744448, -16760832, -8323200, -16735488, -16766464,
			-16735360, -5351680, -32768, -10670336, -6001920, -28325, -8372224,
			-6382080, -4938639, -128, -9342720, -256, -10987477, -7039927,
			-5784176, -13356007, -65536, -8372160, -8388608, -32640, -12582848,
			-8388353, -65281, -8388544
	}; //color agua -7228984

	public MapView(TableModel dm) {
		super();
		this.dm = dm;
		//lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public static int getIndex(int color) {
		int ret = -1;
		for (int i = 0; i < colorTerritory.length; i++) {
			if (color == colorTerritory[i]) ret = i;
		}
		return ret;
	}

	public java.awt.Image getPais() {
		return pais;
	}

	public void setPais(java.awt.Image pais) {
		this.pais = pais;
	}

	public static int getX(int idx) {
		return xTerritory[idx];
	}

	public static int getY(int idx) {
		return yTerritory[idx];
	}

	public static String getImageTerrirtory(int idx) {
		return imageTerritory[idx];
	}

	public void ponerSel(int numTerritory) {
		BufferedImage bufferImage = null;
		try {
			bufferImage = ImageIO.read(ClassLoader.getSystemResource("image/"
					+ getImageTerrirtory(numTerritory)));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = getX(numTerritory);
		y = getY(numTerritory);
		this.setPais(bufferImage);
	}

	public java.awt.Image getFondo() {
		return fondo;
	}

	public void setFondo(java.awt.Image fondo) {
		this.fondo = fondo;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, null);
		if (pais != null) {
			g.drawImage(pais, x, y, null);
		}
	}

	public void getRowInfo(int idx) {
		final String ret = "<html>\n<P ALIGN=\"center\"><BIG>"
				+ TerritoryData.getName(idx)
				+ "</BIG><BR>\n<B> Controlado por: <EM>"
				+ dm.getValueAt(idx, 1)
				+ "</em></b></P>\n<HR>\n<P ALIGN=\"right\">\nSoldados: "
				+ dm.getValueAt(idx, 2)
				+ "<BR>\nCañones Tipo 1: " + dm.getValueAt(idx, 3)
				+ "<BR>\nCañones Tipo 2: " + dm.getValueAt(idx, 4)
				+ "<BR>\nCañones Tipo 3: " + dm.getValueAt(idx, 5)
				+ "<BR>\nMisiles: " + dm.getValueAt(idx, 6)
				+ "<BR>\nICBMs: " + dm.getValueAt(idx, 7)
				+ "<BR>\nAntimisiles: " + dm.getValueAt(idx, 8)
				+ "<BR>\n</P>";
		this.getInfoPlayer().setContentType("text/html");
		this.getInfoPlayer().setText(ret);
	}

	public void setInfoPlayer(JEditorPane infoPlayer) {
		infoPlayer.setEditable(false);
		infoPlayer.setSize(150, 300);
		this.infoPlayer = infoPlayer;
	}

	public JEditorPane getInfoPlayer() {
		return infoPlayer;
	}
}
