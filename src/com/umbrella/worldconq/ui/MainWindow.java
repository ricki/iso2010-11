package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.umbrella.worldconq.WorldConqApp;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -5107198177153703399L;

	private final WorldConqApp app;

	private JToolBar mGameListToolBar = null;
	private JPanel mGameListPanel = null;
	private JPanel mGamePanel = null;
	private JPanel mGameInfoPanel = null;
	private final JPanel mGameInfoPanel2 = null;
	private JToolBar mPlayToolBar = null;
	private JTable mOpenList = null;
	private JTable mCurrentList = null;
	private JTable mMap = null;
	private BufferedImage bufferImageColorPixel = null;
	private BufferedImage bufferImageMap = null;

	public MainWindow() {
		super();
		app = WorldConqApp.getWorldConqApp();
		this.initGUI();
	}

	void initGUI() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		try {
			this.setIconImage(new ImageIcon(
				ClassLoader.getSystemResource("image/logo.png")).getImage());
		} catch (final Exception e) {
			System.out.println("Imagen no encontrada");
		}
		this.setResizable(false);
		this.setTitle("La Conquista del Mundo");
		this.setSize(800, 500);

		mGameListToolBar = new JToolBar();
		mPlayToolBar = new JToolBar();

		final JButton updateListButton = new JButton("Actualizar lista");
		updateListButton.addMouseListener(new UpdateListMouseAdapter());
		mGameListToolBar.add(updateListButton);

		final JButton createGameButton = new JButton("Crear partida");
		createGameButton.addMouseListener(new CreateGameMouseAdapter());
		mGameListToolBar.add(createGameButton);

		final JButton joinGameButton = new JButton("Unirse a la partida");
		joinGameButton.addMouseListener(new JoinGameMouseAdapter());

		final JButton connectGameButton = new JButton("Conectarse a partida");
		connectGameButton.addMouseListener(new ConnectGameMouseAdapter());
		mGameListToolBar.add(connectGameButton);

		mGameListToolBar.add(joinGameButton);
	}

	public void setupListGUI() {
		this.getContentPane().add(mGameListToolBar, BorderLayout.NORTH);
		// FIXME Esto no funciona.
		// getContentPane().add(mPlayToolBar, BorderLayout.NORTH);
		this.getContentPane().add(this.getGameListPanel(), BorderLayout.CENTER);
		mGameListToolBar.setVisible(true);
		mPlayToolBar.setVisible(false);
		this.getGameListPanel().setVisible(true);
	}

	public void setupGameGUI() {
		// hacemos invisible lo anterior
		this.getGameListPanel().setVisible(false);
		mGameListToolBar.setVisible(false);
		// mostramos el mapa y lo demas
		mMap = new JTable(
			app.getGameManager().getGameEngine().getMapListModel());
		final MapView mv = new MapView(mMap.getModel());
		this.getContentPane().add(mPlayToolBar, BorderLayout.NORTH);
		this.getContentPane().add(this.getGamePanel(mv), BorderLayout.CENTER);
		this.getContentPane().add(this.getGameInfoPanel(mv), BorderLayout.EAST);
		mPlayToolBar.setVisible(true);
		this.getGamePanel(mv).setVisible(true);
		//this.pack();
		this.setSize(1227, 628);
		this.setLocationRelativeTo(null);

	}

	private JPanel getGameListPanel() {
		if (mGameListPanel == null) {
			mGameListPanel = new JPanel();
			mGameListPanel.setLayout(new BoxLayout(mGameListPanel,
				BoxLayout.Y_AXIS));
			mCurrentList = new JTable(
				app.getGameManager().getCurrentGameListModel());
			final JScrollPane currentListPanel = new JScrollPane(mCurrentList);
			mOpenList = new JTable(
				app.getGameManager().getOpenGameListModel());
			final JScrollPane openListPanel = new JScrollPane(mOpenList);
			mCurrentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mOpenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			mGameListPanel.add(new JLabel("Mis partidas actuales"));
			mGameListPanel.add(currentListPanel);
			mGameListPanel.add(new JLabel("Partidas disponibles"));
			mGameListPanel.add(openListPanel);
		}
		return mGameListPanel;
	}

	private JPanel getGamePanel(MapView mv) {
		if (mGamePanel == null) {
			mGamePanel = new JPanel();
			mGamePanel.setLayout(new BoxLayout(mGamePanel,
				BoxLayout.Y_AXIS));
			try {
				bufferImageColorPixel = ImageIO.read(ClassLoader.getSystemResource("image/Map_risk_buffer.png"));
				bufferImageMap = ImageIO.read(ClassLoader.getSystemResource("image/Map_risk.png"));
			} catch (final IOException e) {
				e.printStackTrace();
			}
			mv.setFondo(bufferImageMap);
			mGamePanel.addMouseListener(new MapMouseAdapter(mv));
			mGamePanel.add(mv);
		}
		return mGamePanel;
	}

	private JPanel getGameInfoPanel(MapView mv) {
		if (mGameInfoPanel == null) {
			mGameInfoPanel = new JPanel();
			mGameInfoPanel.setLayout(new BoxLayout(mGameInfoPanel,
				BoxLayout.Y_AXIS));

			mv.setInfoPlayer(new JEditorPane());
			mv.setListPlayer(new JEditorPane());

			mGameInfoPanel.add(new JScrollPane(mv.getListPlayer()));
			mGameInfoPanel.add(new JScrollPane(mv.getInfoPlayer()));
		}
		return mGameInfoPanel;
	}

	private class CreateGameMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			final JFrame f = new JFrame();
			final CreateGameDialog dlg = new CreateGameDialog(f,
				"La Conquista del Mundo - Nueva partida", true);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);

			if (dlg.getSelection() == true) {
				try {
					app.getGameManager().createGame(dlg.getGameName(),
						dlg.getDescription(), dlg.getCalendarList());
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			f.dispose();
		}
	}

	private class UpdateListMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			try {
				app.getGameManager().updateGameList();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class JoinGameMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			final int gameSelected = mOpenList.getSelectedRow();
			System.out.println(gameSelected);
			if (mOpenList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(mGameListPanel,
					"No ha seleccionado ninguna partida");
			} else {
				try {
					app.getGameManager().joinGame(gameSelected);
					final ArrayList<Calendar> session = app.getGameManager().getOpenGameListModel().getGameAt(
						gameSelected).getGameSessions();

					boolean onLine = false;

					//Creo un long que contine la fecha actual en milisegundos
					final long date = Calendar.getInstance().getTimeInMillis();

					//Recorro las sessiones de la partida seleccionada, si la resta es menos a dos horas se puede jugar
					for (int i = 0; i < session.size() && onLine == false; i++) {
						if ((date - session.get(i).getTimeInMillis() < 7200000)
								&& (date - session.get(i).getTimeInMillis() > 0)) {
							onLine = true;
						}
					}

					if (onLine) {
						//Existe una sesion activa, se pregunta si se desea jugar
						final int confirm = JOptionPane.showConfirmDialog(
							mGameListPanel,
							"La partida esta en juego, ¿desea jugar ahora?",
							"confirmación", JOptionPane.YES_NO_OPTION);
						if (confirm == 0) {
							//Se ha seleccionado que se desea jugar, se llama a connectToGame.
							//app.getGameManager().connectToGame();
						} else {
							//No se desea jugar, se actualiza la lista de partidas.
							app.getGameManager().updateGameList();
						}
					} else {
						//No hay ninguna sesión activa, se actuliza la lista de partidas.
						app.getGameManager().updateGameList();
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class ConnectGameMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			final int gameSelected = mCurrentList.getSelectedRow();
			System.out.println(gameSelected);
			if (mCurrentList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(mGamePanel,
					"No ha seleccionado ninguna partida");
			} else {
				try {
					app.getGameManager().connectToGame(gameSelected);
					MainWindow.this.setupGameGUI();

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class MapMouseAdapter extends MouseAdapter {
		private MapView mv = null;

		public MapMouseAdapter(MapView mv) {
			super();
			this.mv = mv;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			final int gameSelected = MapView.getIndex(bufferImageColorPixel.getRGB(
				evt.getX(), evt.getY()));
			//System.out.print(bufferImageColorPixel.getRGB(evt.getX(), evt.getY()));
			if (gameSelected != -1) {
				mv.getRowInfo(gameSelected);
				mv.removeAll();
				mv.setFondo(bufferImageMap);
				mv.ponerSel(gameSelected);
				mv.repaint();

				//info.setToolTipText(infoText);
				//System.out.println(infoText);
			}

		}

	}

}
