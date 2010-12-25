package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
			final JTable currentList = new JTable(
				app.getGameManager().getCurrentGameListModel());
			final JScrollPane currentListPanel = new JScrollPane(currentList);
			final JTable openList = new JTable(
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
}
