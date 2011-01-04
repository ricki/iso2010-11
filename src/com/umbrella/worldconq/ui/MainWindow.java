package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	private JToolBar mPlayToolBar = null;

	private JTable openList = null;

	private JTable currentList = null;

	public MainWindow() {
		super();
		app = WorldConqApp.getWorldConqApp();
		this.initGUI();
	}

	private void initGUI() {
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
		// TODO : Creo ya la función. A completar en próximas iteraciones.
		mGameListToolBar.setVisible(false);
		mPlayToolBar.setVisible(true);
		this.getGameListPanel().setVisible(false);
	}

	private JPanel getGameListPanel() {
		if (mGameListPanel == null) {
			mGameListPanel = new JPanel();
			mGameListPanel.setLayout(new BoxLayout(mGameListPanel,
				BoxLayout.Y_AXIS));
			currentList = new JTable(
				app.getGameManager().getCurrentGameListModel());
			final JScrollPane currentListPanel = new JScrollPane(currentList);
			openList = new JTable(
				app.getGameManager().getOpenGameListModel());
			final JScrollPane openListPanel = new JScrollPane(openList);
			currentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			openList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			mGameListPanel.add(new JLabel("Mis partidas actuales"));
			mGameListPanel.add(currentListPanel);
			mGameListPanel.add(new JLabel("Partidas disponibles"));
			mGameListPanel.add(openListPanel);
		}
		return mGameListPanel;
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
			final int gameSelected = openList.getSelectedRow();
			System.out.println(gameSelected);
			if (openList.getSelectedRow() == -1) {
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
			final int gameSelected = currentList.getSelectedRow();
			System.out.println(gameSelected);
			if (currentList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(mGameListPanel,
					"No ha seleccionado ninguna partida");
			} else {
				try {
					app.getGameManager().connectToGame(gameSelected);

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
