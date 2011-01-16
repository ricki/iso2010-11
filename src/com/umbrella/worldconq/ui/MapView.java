package com.umbrella.worldconq.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.umbrella.worldconq.domain.TerritoryData;

public class MapView extends JComponent {

	private static final long serialVersionUID = 1L;

	private java.awt.Image fondo;
	private java.awt.Image pais;
	private int x;
	private int y;

	public MapView() {
		super();
	}

	public java.awt.Image getPais() {
		return pais;
	}

	public void setPais(java.awt.Image pais) {
		this.pais = pais;
	}

	public void ponerSel(int numTerritory) {
		final String dir = System.getProperty("user.dir") + "/src/image/";
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(dir
					+ TerritoryData.getImageTerrirtory(numTerritory)));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = TerritoryData.getX(numTerritory);
		y = TerritoryData.getY(numTerritory);
		this.setPais(bi);
	}

	public java.awt.Image getFondo() {
		return fondo;
	}

	public void setFondo(java.awt.Image fondo) {
		this.fondo = fondo;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(fondo, 0, 0, null);
		g.drawImage(pais, x, y, null);
	}
}
