package com.umbrella.worldconq.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.PlayerListModel;
import com.umbrella.worldconq.domain.TerritoryData;

public class MapView extends JComponent {

	private static final long serialVersionUID = 1L;

	private java.awt.Image fondo;
	private java.awt.Image pais;
	private int x;
	private int y;
	private final TableModel dm;
	private final GameManager gm;
	protected ListSelectionModel lsm;
	private JEditorPane infoPlayer;
	private JEditorPane listPlayer;
	private JEditorPane actionGame;
	private static int gameSelected;
	private final BufferedImage bufferImageMap;

	private final BufferedImage[] bufferTerritoryImage = new BufferedImage[42];

	private static BufferedImage bufferImageColorPixel;

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

	public MapView(TableModel dm, GameManager gm) throws IOException {
		super();
		this.dm = dm;
		this.gm = gm;
		gameSelected = -1;
		this.setPreferredSize(new Dimension(920, 471));
		bufferImageColorPixel = ImageIO.read(ClassLoader.getSystemResource("image/half.Map_risk_buffer.png"));
		bufferImageMap = ImageIO.read(ClassLoader.getSystemResource("image/half.Map_risk.png"));
		for (int i = 0; i < 42; i++) {
			bufferTerritoryImage[i] = ImageIO.read(ClassLoader.getSystemResource("image/half."
					+ this.getImageTerrirtory(i)));
		}
	}

	public int getIndex(int color) {
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

	public int getX(int idx) {
		return (int) (0.75 * xTerritory[idx]);
	}

	public int getY(int idx) {
		return (int) (0.75 * yTerritory[idx]);
	}

	public String getImageTerrirtory(int idx) {
		return imageTerritory[idx];
	}

	public void setSelection(int numTerritory) {
		x = this.getX(numTerritory);
		y = this.getY(numTerritory);
		this.setPais(bufferTerritoryImage[numTerritory]);
	}

	public void setFondo() {
		fondo = bufferImageMap;
	}

	@Override
	public void paint(Graphics g) {
		if (gameSelected != -1) {
			this.getRowInfo(gameSelected);
			this.removeAll();
			this.setFondo();
			this.setSelection(gameSelected);
		} else {
			// pinchamos sobre agua
			this.removeAll();
			this.setFondo();
			this.getRowInfo(gameSelected);
		}
		g.drawImage(fondo, 0, 0, null);
		if (pais != null) {
			g.drawImage(pais, x, y, null);
		}
		// añadimos los nombre de los jugadores
		this.setPlayerName(g);
	}

	public void setPlayerName(Graphics g) {
		BufferedImage aux = null;
		for (int i = 0; i < 42; i++) {
			if (!dm.getValueAt(i, 1).equals("¿?")) {
				aux = bufferTerritoryImage[i];
				if (i <= 18 || (i > 24 && i <= 33)) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.GREEN);
				}

				final Font f = new Font((String) dm.getValueAt(i, 1),
					Font.BOLD, 8);
				g.setFont(f);
				final int mediaX = (aux.getWidth() / 2) + this.getX(i);
				final int mediaY = (aux.getHeight() / 2) + this.getY(i);
				g.drawString(f.getName(), mediaX, mediaY);
			}
		}
	}

	public void getRowInfo(int idx) {
		if (idx != -1) {
			final String ret = "<html>\n<P ALIGN=\"center\"><BIG>"
					+ TerritoryData.getName(idx)
					+ "</BIG><BR>\n<B> Controlado por: <EM>"
					+ dm.getValueAt(idx, 1)
					+ "</em></b></P>\n<HR>"
					+ "<TABLE BORDER=0>"
					+ "<TR><TD Align=\"right\">Soldados:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 2)
					+ "<TR><TD Align=\"right\">Cañones Tipo 1:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 3)
					+ "<TR><TD Align=\"right\">Cañones Tipo 2:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 4)
					+ "<TR><TD Align=\"right\">Cañones Tipo 3:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 5)
					+ "<TR><TD Align=\"right\">Misiles:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 6)
					+ "<TR><TD Align=\"right\">ICBMs:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 7)
					+ "<TR><TD Align=\"right\">Antimisiles:<TD Align=\"center\">"
					+ dm.getValueAt(idx, 8) + "</TABLE>\n</P>";
			this.getInfoPlayer().setContentType("text/html");
			this.getInfoPlayer().setText(ret);
		} else {
			this.setPais(null);
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

	public void setListPlayer(JEditorPane listPlayer) {
		listPlayer.setEditable(false);

		String list = "<html>\n<P ALIGN=\"center\"><BIG>" + "Jugadores"
				+ "</BIG><BR></P>\n<HR>" + "<TABLE BORDER=0>";
		final PlayerListModel plm = gm.getGameEngine().getPlayerListModel();

		for (int i = 0; i < plm.getRowCount(); i++) {
			list += "<TR><TD Align=\"left\">" + "<IMG SRC=\"";
			if ((Boolean) plm.getValueAt(i, 2)) {
				if ((Boolean) plm.getValueAt(i, 1)) {
					list += ClassLoader.getSystemResource("image/turn.png");
				} else {
					list += ClassLoader.getSystemResource("image/online.png");
				}
			} else {
				list += ClassLoader.getSystemResource("image/offline.png");
			}
			list += "\"><TD Align=\"left\">" + plm.getValueAt(i, 0);
		}

		list += "</TABLE>\n</P>";
		this.listPlayer = listPlayer;
		this.getListPlayer().setContentType("text/html");
		this.getListPlayer().setText(list);
	}

	public JEditorPane getListPlayer() {
		return listPlayer;
	}

	public JEditorPane getActionGame() {
		return actionGame;
	}

	public void setActionGame(JEditorPane actionGame) {

		actionGame.setEditable(false);
		String list = "<html>\n<P ALIGN=\"left\"><BIG>"
				+ "Información de la Partida" + "</BIG><BR></P><HR>";
		list += "iran las acciones que se generen en la partida\n";
		list += "</P>";
		this.actionGame = actionGame;
		this.getActionGame().setContentType("text/html");
		this.getActionGame().setText(list);
	}

	public int getSelectedRow(MouseEvent evt) {
		gameSelected = this.getIndex(bufferImageColorPixel.getRGB(evt.getX(),
			evt.getY()));
		return gameSelected;
	}
}
