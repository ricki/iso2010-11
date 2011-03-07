package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.domain.GameEngine;
import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.TerritoryDecorator;
import com.umbrella.worldconq.exceptions.NotEnoughMoneyException;
import com.umbrella.worldconq.exceptions.NotEnoughUnitsException;
import com.umbrella.worldconq.exceptions.OcupiedTerritoryException;
import com.umbrella.worldconq.exceptions.OutOfTurnException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
import com.umbrella.worldconq.exceptions.UnocupiedTerritoryException;

import domain.Arsenal;
import domain.Player;
import exceptions.AlreadyInGameException;
import exceptions.FullGameException;
import exceptions.GameNotFoundException;
import exceptions.InvalidGameInfoException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTerritoryException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;

public class MainWindow extends JFrame implements GameEventListener {

	private static final long serialVersionUID = -5107198177153703399L;

	private final GameManager gameMgr;
	private final WorldConqApp app;

	private JToolBar mGameListToolBar = null;
	private JPanel mGameListPanel = null;
	private JTable mOpenList = null;
	private JTable mCurrentList = null;

	private JToolBar mPlayToolBar = null;
	private JPanel mGamePanel = null;
	private JPanel mGameInfoPanel = null;
	private JTextArea actionGame = null;

	//Botones de la barra mPlayToolbar
	private JButton moveUnitsButton; //Botón para mover unidades de un territorio a otro
	private JButton attackButton; //Botón para atacar un territorio
	private JButton buyUnitsButton; //Botón para comprar refuerzos
	private JButton sendSpyButton; //Botón para enviar un espía a un territorio
	private JButton buyTerritoryButton; //Botón para comprar territorios
	private JButton exitGameButton; //Botón para desconectarse de una partida
	private JButton logoutButton; //Botón para quitar y salir del juego (cerrar sesión)
	private JButton connectGameButton; //Botón para conectarse a una partida
	private JButton joinGameButton; //Botón para unirse a una partida
	private JButton updateListButton;
	private JButton createGameButton;
	private MapView mv; //MapView
	private TerritoryInfoView tinfov; //MapView

	public MainWindow(GameManager gameMgr, WorldConqApp app) {
		super();
		this.gameMgr = gameMgr;
		this.app = app;
		try {
			this.setIconImage(new ImageIcon(
				ClassLoader.getSystemResource("image/logo.png")).getImage());
		} catch (final Exception e) {
			System.out.println("Imagen no encontrada");
		}
		this.setResizable(false);
		this.setTitle("La Conquista del Mundo");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setupListGUI();

		try {
			gameMgr.updateGameList();
		} catch (final RemoteException e) {
			this.showErrorAndExit(e);
		} catch (final InvalidSessionException e) {
			this.showErrorAndExit(e);
		}
	}

	public void setupListGUI() {
		if (mPlayToolBar != null) {
			mPlayToolBar.setVisible(false);
			mPlayToolBar = null;
		}
		if (mGamePanel != null) {
			mGamePanel.setVisible(false);
			mGamePanel = null;
		}
		if (mGameInfoPanel != null) {
			mGameInfoPanel.setVisible(false);
			mGameInfoPanel = null;
		}

		this.getContentPane().add(this.getGameListToolBar(), BorderLayout.NORTH);
		this.getContentPane().add(this.getGameListPanel(), BorderLayout.CENTER);
		mGameListToolBar.setVisible(true);
		this.getGameListPanel().setVisible(true);
	}

	private JToolBar getGameListToolBar() {
		if (mGameListPanel == null) {
			mGameListToolBar = new JToolBar();

			updateListButton = new JButton("Actualizar lista");
			updateListButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/refresh.png")));
			updateListButton.addMouseListener(new UpdateListMouseAdapter());
			mGameListToolBar.add(updateListButton);

			createGameButton = new JButton("Crear partida");
			createGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/addgame.png")));
			createGameButton.addMouseListener(new CreateGameMouseAdapter());
			mGameListToolBar.add(createGameButton);

			joinGameButton = new JButton("Unirse a la partida");
			joinGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/join.png")));
			joinGameButton.addMouseListener(new JoinGameMouseAdapter());
			joinGameButton.setEnabled(false);
			mGameListToolBar.add(joinGameButton);

			connectGameButton = new JButton("Conectarse a partida");
			connectGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/connect.png")));
			connectGameButton.addMouseListener(new ConnectGameMouseAdapter(this));
			connectGameButton.setEnabled(false);
			mGameListToolBar.add(connectGameButton);

			logoutButton = new JButton("Cerrar sesión");
			logoutButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/logout.png")));
			logoutButton.addMouseListener(new LogoutMouseAdapter(this));
			mGameListToolBar.add(logoutButton);

		}
		return mGameListToolBar;
	}

	private JPanel getGameListPanel() {
		if (mGameListPanel == null) {
			mGameListPanel = new JPanel();
			mGameListPanel.setLayout(new BoxLayout(mGameListPanel,
				BoxLayout.Y_AXIS));
			mCurrentList = new JTable(
				gameMgr.getCurrentGameListModel());
			final JScrollPane currentListPanel = new JScrollPane(mCurrentList);
			mOpenList = new JTable(
				gameMgr.getOpenGameListModel());
			final JScrollPane openListPanel = new JScrollPane(mOpenList);

			mCurrentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mOpenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			final GameListObserver listObserver = new GameListObserver(this);
			mCurrentList.getSelectionModel().addListSelectionListener(
				listObserver);
			mOpenList.getSelectionModel().addListSelectionListener(listObserver);

			mGameListPanel.add(new JLabel("Mis partidas actuales"));
			mGameListPanel.add(currentListPanel);
			mGameListPanel.add(new JLabel("Partidas disponibles"));
			mGameListPanel.add(openListPanel);
		}
		return mGameListPanel;
	}

	public void setupGameGUI() throws IOException {
		// hacemos invisible lo anterior
		this.getGameListPanel().setVisible(false);
		this.getGameListToolBar().setVisible(false);
		// mostramos el mapa y lo demas
		mv = new MapView(
			gameMgr.getGameEngine().getMapListModel());
		tinfov = new TerritoryInfoView(
			gameMgr.getGameEngine().getMapListModel());
		//Añado un observador al modelo de lista de selección del mapView
		mv.getListSelectionModel().addListSelectionListener(
			new MapViewObserver(this, mv));
		//tinfov.getListSelectionModel().addListSelectionListener(
		//	new TerritoryInfoViewObserver(this, tinfov));
		//this.getPlayToolbar();
		this.getContentPane().add(this.getPlayToolbar(), BorderLayout.NORTH);
		this.getContentPane().add(this.createGamePanel(), BorderLayout.CENTER);
		this.getContentPane().add(this.createGameInfoPanel(), BorderLayout.EAST);
		this.getPlayToolbar().setVisible(true);
		mGamePanel.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

	}

	private JPanel createGamePanel() {
		mGamePanel = new JPanel();
		mGamePanel.setLayout(new BoxLayout(mGamePanel, BoxLayout.Y_AXIS));
		mv.setFondo();
		mGamePanel.add(mv);
		//añadimos el panel para la informacion de la partida
		this.setActionGame(new JTextArea());
		final JScrollPane ActionGameScroll = new JScrollPane(
			this.getActionGame());
		ActionGameScroll.setPreferredSize(new Dimension(300, 125));
		mGamePanel.add(ActionGameScroll);
		return mGamePanel;
	}

	private JPanel createGameInfoPanel() {
		mGameInfoPanel = new JPanel();
		mGameInfoPanel.setLayout(new BoxLayout(mGameInfoPanel,
			BoxLayout.Y_AXIS));
		mv.setInfoPlayer(new JEditorPane());
		tinfov.setInfoPlayer(new JEditorPane());
		final JScrollPane listPlayerSroll = new JScrollPane(new PlayerView(
			gameMgr.getGameEngine().getPlayerListModel()));
		listPlayerSroll.setPreferredSize(new Dimension(150, 300));

		final JScrollPane listInfoSroll = new JScrollPane(mv.getInfoPlayer());
		//final JScrollPane listInfoSroll = new JScrollPane(tinfov.getInfoPlayer());
		listInfoSroll.setPreferredSize(new Dimension(150, 300));
		mGameInfoPanel.add(listPlayerSroll);
		mGameInfoPanel.add(listInfoSroll);
		return mGameInfoPanel;
	}

	//Método que devuelve el Game Manager
	public GameManager getGameManager() {
		return gameMgr;
	}

	//Método que devuelve el MapView
	public MapView getMapView() {
		return mv;
	}

	//Método que genera los botones que llevará la barra 
	//de herramientas mPlayToolbar cuando se está jugando
	public JToolBar getPlayToolbar() {
		if (mPlayToolBar == null) {
			moveUnitsButton = new JButton("Mover tropas"); //Botón para mover unidades de un territorio a otro
			moveUnitsButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/moveunits.png")));
			attackButton = new JButton("Atacar"); //Botón para atacar un territorio
			attackButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/attack.png")));
			buyUnitsButton = new JButton("Comprar refuerzos"); //Botón para comprar refuerzos
			buyUnitsButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/buy.png")));
			sendSpyButton = new JButton("Enviar espía"); //Botón para enviar un espía a un territorio
			sendSpyButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/spy.png")));
			buyTerritoryButton = new JButton(
				"Comprar territorio"); //Botón para comprar territorios
			buyTerritoryButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/buy.png")));
			exitGameButton = new JButton("Desconectarse"); //Botón para desconectarse de la partida
			exitGameButton.setIcon(new ImageIcon(
				this.getClass().getClassLoader().getResource(
					"image/exitb.png")));

			//Añado un capturador de eventos a cada botón
			attackButton.addMouseListener(new AttackMouseAdapter(this));
			moveUnitsButton.addMouseListener(new MoveUnitsMouseAdapter(this));
			sendSpyButton.addMouseListener(new SendSpyMouseAdapter(this));
			buyUnitsButton.addMouseListener(new BuyUnitsMouseAdapter(this));
			buyTerritoryButton.addMouseListener(new BuyTerritoryMouseAdapter(
				this));
			exitGameButton.addMouseListener(new ExitGameMouseAdapter(this));

			//Deshabilito los botones
			attackButton.setEnabled(false);
			moveUnitsButton.setEnabled(false);
			sendSpyButton.setEnabled(false);
			buyUnitsButton.setEnabled(false);
			buyTerritoryButton.setEnabled(false);

			//Creo la toolbar
			mPlayToolBar = new JToolBar();

			mPlayToolBar.add(attackButton);
			mPlayToolBar.add(moveUnitsButton);
			mPlayToolBar.add(sendSpyButton);
			mPlayToolBar.add(buyUnitsButton);
			mPlayToolBar.add(buyTerritoryButton);
			mPlayToolBar.add(exitGameButton);
		}
		return mPlayToolBar;
	}

	private void showErrorAndExit(Exception e) {
		JOptionPane.showMessageDialog(MainWindow.this, e.toString(),
			"Error inesperado", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		try {
			gameMgr.getUserManager().closeSession();
		} catch (final Exception e1) {
		}
		app.setStartupMode();
	}

	private void showErrorAndExitGame(Exception e) {
		JOptionPane.showMessageDialog(MainWindow.this, e.toString(),
			"Error en la partida", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
		try {
			gameMgr.disconnectFromGame();
		} catch (final Exception e1) {
		}
		this.setupListGUI();
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
					gameMgr.createGame(dlg.getGameName(),
						dlg.getDescription(), dlg.getCalendarList(),
						dlg.getTurnTime(),
						dlg.getDefTime(), dlg.getNegTime());
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidGameInfoException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"Algún parámetro de partida es inválido",
						"GameInfo inválido",
						JOptionPane.WARNING_MESSAGE);
				}
			}
			f.dispose();
		}
	}

	private class UpdateListMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			try {
				gameMgr.updateGameList();
			} catch (final RemoteException e) {
				MainWindow.this.showErrorAndExit(e);
			} catch (final InvalidSessionException e) {
				MainWindow.this.showErrorAndExit(e);
			}
		}
	}

	private class JoinGameMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			final int gameSelected = mOpenList.getSelectedRow();
			System.out.println("Seleccionado: " + gameSelected);
			System.out.println(mOpenList.getRowCount());
			if (mOpenList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(mGameListPanel,
					"No ha seleccionado ninguna partida");
			} else {
				try {
					gameMgr.joinGame(gameSelected);
					gameMgr.updateGameList();
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final FullGameException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"La partida a la que intentas unirte ya está llena.",
						"Partida llena",
						JOptionPane.WARNING_MESSAGE);
				} catch (final GameNotFoundException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"No se ha podido localizar la partida seleccionada.",
						"Partida no encontrada",
						JOptionPane.WARNING_MESSAGE);
				} catch (final AlreadyInGameException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"Ya estás unido a la partida seleccionada.",
						"Jugador en la partida",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	private class ConnectGameMouseAdapter extends MouseAdapter {
		MainWindow win;

		public ConnectGameMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			final int gameSelected = mCurrentList.getSelectedRow();
			System.out.println(gameSelected);
			if (mCurrentList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(mGamePanel,
					"No ha seleccionado ninguna partida");
			} else {
				try {
					gameMgr.connectToGame(gameSelected, win);
					MainWindow.this.setupGameGUI();
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"No se ha podido localizar la partida seleccionada.",
						"Partida no encontrada",
						JOptionPane.WARNING_MESSAGE);
				} catch (final InvalidTimeException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"No es buen momento para jugar. Tómate un café.",
						"Tiempo no válido",
						JOptionPane.WARNING_MESSAGE);
				} catch (final NotCurrentPlayerGameException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"No estás unido a la partida seleccionada.",
						"Jugador no en la partida",
						JOptionPane.WARNING_MESSAGE);
				} catch (final AlreadyInGameException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						"Ya estás conectado a la partida seleccionada.",
						"Jugador en la partida",
						JOptionPane.WARNING_MESSAGE);
				} catch (final IOException e) {
					JOptionPane.showMessageDialog(MainWindow.this,
						e.toString(),
						"Problema en MapView",
						JOptionPane.WARNING_MESSAGE);
					MainWindow.this.setupListGUI();
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón logoutButton
	private class LogoutMouseAdapter extends MouseAdapter {
		MainWindow win;

		public LogoutMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			System.out.println("Haciendo logout...");
			//Aquí todo lo de guardar los datos, las partidas y demás
			try {
				final int confirm = JOptionPane.showConfirmDialog(
					mGameListPanel,
					"¿Está seguro de que desea salir?",
					"Confirmación", JOptionPane.YES_NO_OPTION);
				if (confirm == 0) {
					win.getGameManager().getUserManager().closeSession();
					win.dispose();
					app.setStartupMode();
				}
			} catch (final Exception e) {
				MainWindow.this.showErrorAndExit(e);
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón attackButton
	private class AttackMouseAdapter extends MouseAdapter {
		MainWindow win;

		public AttackMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			LaunchAttackDialog lad;
			ArrayList<TerritoryDecorator> adjList;
			final ArrayList<TerritoryDecorator> filteredList = new ArrayList<TerritoryDecorator>();
			final ArrayList<String> adjListNames = new ArrayList<String>();

			final GameEngine engine = gameMgr.getGameEngine();
			final Player self = engine.getPlayerListModel().getSelfPlayer();

			TerritoryDecorator srcT = null;
			TerritoryDecorator dstT = null;

			final int srcId = win.mv.getSelectedRow();

			System.out.println("Atacando...");

			if (win.attackButton.isEnabled() && srcId >= 0) {
				srcT = engine.getMapListModel().getTerritoryAt(srcId);

				adjList = srcT.getAdjacentTerritories();
				for (final TerritoryDecorator t : adjList) {
					if (t.getPlayer() != null && !t.getPlayer().equals(self))
						filteredList.add(t);
				}

				for (final TerritoryDecorator t : filteredList)
					adjListNames.add(t.getName());

				lad = new LaunchAttackDialog(win, srcT, adjListNames);
				lad.setLocationRelativeTo(null);
				lad.setModal(true);
				lad.setVisible(true);

				if (lad.getSelection()) {
					dstT = filteredList.get(lad.getTerritoryIndex());
					try {
						engine.attackTerritory(srcId, dstT.getId(),
							lad.getSoldierCount(),
							lad.getCannonCount(),
							lad.getMissileCount(),
							lad.getICBMCount());
					} catch (final RemoteException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final InvalidSessionException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final GameNotFoundException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final InvalidTimeException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final PendingAttackException e) {
						JOptionPane.showMessageDialog(win,
							"Hay otro ataque en curso.",
							"Ataques simultáneos",
							JOptionPane.WARNING_MESSAGE);
					} catch (final InvalidTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no es válido.",
							"Territorio no válido",
							JOptionPane.WARNING_MESSAGE);
					} catch (final OutOfTurnException e) {
						JOptionPane.showMessageDialog(win,
							"Accion realizada fuera de turno.",
							"Fuera de turno",
							JOptionPane.WARNING_MESSAGE);
					} catch (final UnocupiedTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no está ocupado.",
							"Territorio no ocupado",
							JOptionPane.WARNING_MESSAGE);
					} catch (final NotEnoughUnitsException e) {
						JOptionPane.showMessageDialog(
							win,
							"No tienes unidades suficientes para la acción seleccionada.",
							"Unidades insuficientes",
							JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón moveUnitsButton
	private class MoveUnitsMouseAdapter extends MouseAdapter {
		MainWindow win;

		public MoveUnitsMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			MoveUnitsDialog mud;
			ArrayList<TerritoryDecorator> adjList;
			final ArrayList<TerritoryDecorator> filteredList = new ArrayList<TerritoryDecorator>();
			TerritoryDecorator srcT;
			TerritoryDecorator dstT = null;
			final ArrayList<String> adjListNames = new ArrayList<String>();
			final Player self = gameMgr.getGameEngine().getPlayerListModel().getSelfPlayer();
			int selectedT;
			if (win.moveUnitsButton.isEnabled()) {
				System.out.println("Moviendo unidades...");
				selectedT = win.getMapView().getSelectedRow();
				srcT = win.getGameManager().getGameEngine().getMapListModel().getTerritoryAt(
					selectedT);
				adjList = srcT.getAdjacentTerritories();
				for (final TerritoryDecorator t : adjList) {
					if (t.getPlayer() != null && t.getPlayer().equals(self))
						filteredList.add(t);
				}

				for (final TerritoryDecorator t : filteredList)
					adjListNames.add(t.getName());
				mud = new MoveUnitsDialog(win, srcT, adjListNames);
				mud.setLocationRelativeTo(null);
				mud.setModal(true);
				mud.setVisible(true);
				if (mud.getSelection() == true) {
					dstT = filteredList.get(mud.getDestiny());
					try {
						mud.setVisible(false);
						win.getGameManager().getGameEngine().moveUnits(
							selectedT, dstT.getId(), mud.getSoldierCount(),
							mud.getCannonCount(), mud.getMissileCount(),
							mud.getICBMCount(), mud.getAntiMissileCount());
					} catch (final RemoteException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final InvalidSessionException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final GameNotFoundException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final NotCurrentPlayerGameException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final OutOfTurnException e) {
						JOptionPane.showMessageDialog(win,
							"Accion realizada fuera de turno.",
							"Fuera de turno",
							JOptionPane.WARNING_MESSAGE);
					} catch (final UnocupiedTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no está ocupado.",
							"Territorio no ocupado",
							JOptionPane.WARNING_MESSAGE);
					} catch (final NotEnoughUnitsException e) {
						JOptionPane.showMessageDialog(
							win,
							"No tienes unidades suficientes para la acción seleccionada.",
							"Unidades insuficientes",
							JOptionPane.WARNING_MESSAGE);
					} catch (final InvalidTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no es válido.",
							"Territorio no válido",
							JOptionPane.WARNING_MESSAGE);
					} catch (final PendingAttackException e) {
						JOptionPane.showMessageDialog(win,
							"Hay otro ataque en curso.",
							"Ataques simultáneos",
							JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón sendSpyButton
	private class SendSpyMouseAdapter extends MouseAdapter {
		MainWindow win;

		public SendSpyMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			final int selT = win.getMapView().getSelectedRow();
			if (win.sendSpyButton.isEnabled() && selT >= 0) {
				System.out.println("Enviando un espía...");
				try {
					win.getGameManager().getGameEngine().deploySpy(selT);
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final NotCurrentPlayerGameException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final OutOfTurnException e) {
					JOptionPane.showMessageDialog(win,
						"Accion realizada fuera de turno.",
						"Fuera de turno",
						JOptionPane.WARNING_MESSAGE);
				} catch (final PendingAttackException e) {
					JOptionPane.showMessageDialog(win,
						"Hay otro ataque en curso.",
						"Ataques simultáneos",
						JOptionPane.WARNING_MESSAGE);
				} catch (final NotEnoughMoneyException e) {
					JOptionPane.showMessageDialog(
						win,
						"No tienes dinero suficiente para la acción seleccionada.",
						"Dinero insuficiente",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón buyUnitsButton
	private class BuyUnitsMouseAdapter extends MouseAdapter {
		MainWindow win;

		public BuyUnitsMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			BuyUnitsDialog bud;
			ArrayList<TerritoryDecorator> adjList;
			TerritoryDecorator srcT;
			final ArrayList<String> adjListNames = new ArrayList<String>();
			final int selectedT = win.getMapView().getSelectedRow();
			if (win.buyUnitsButton.isEnabled() && selectedT >= 0) {
				System.out.println("Comprando refuerzos...");
				srcT = win.getGameManager().getGameEngine().getMapListModel().getTerritoryAt(
					selectedT);
				adjList = srcT.getAdjacentTerritories();
				for (int i = 0; i < adjList.size(); i++) {
					if ((adjList.get(i).getPlayer() != null)
							&& ((((adjList.get(i)).getOwner()).equals(win.gameMgr.getGameEngine().getPlayerListModel().getSelfPlayer().getName())))) {
						adjListNames.add(adjList.get(i).getName());
					}
				}
				bud = new BuyUnitsDialog(win, srcT.getName(),
					srcT.getPlayer().getMoney());
				bud.setLocationRelativeTo(null);
				bud.setModal(true);
				bud.setVisible(true);
				if (bud.getSelection() == true) {
					try {
						bud.setVisible(false);
						win.getGameManager().getGameEngine().buyUnits(
							selectedT, bud.getSoldierCount(),
							bud.getCannonCount(), bud.getMissileCount(),
							bud.getICBMCount(), bud.getAntiMissileCount());
					} catch (final RemoteException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final InvalidSessionException e) {
						MainWindow.this.showErrorAndExit(e);
					} catch (final GameNotFoundException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final NotCurrentPlayerGameException e) {
						MainWindow.this.showErrorAndExitGame(e);
					} catch (final OutOfTurnException e) {
						JOptionPane.showMessageDialog(win,
							"Accion realizada fuera de turno.",
							"Fuera de turno",
							JOptionPane.WARNING_MESSAGE);
					} catch (final PendingAttackException e) {
						JOptionPane.showMessageDialog(win,
							"Hay otro ataque en curso.",
							"Ataques simultáneos",
							JOptionPane.WARNING_MESSAGE);
					} catch (final NotEnoughMoneyException e) {
						JOptionPane.showMessageDialog(
							win,
							"No tienes dinero suficiente para la acción seleccionada.",
							"Dinero insuficiente",
							JOptionPane.WARNING_MESSAGE);
					} catch (final UnocupiedTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no está ocupado.",
							"Territorio no ocupado",
							JOptionPane.WARNING_MESSAGE);
					} catch (final InvalidTerritoryException e) {
						JOptionPane.showMessageDialog(win,
							"El territorio seleccionado no es válido.",
							"Territorio no válido",
							JOptionPane.WARNING_MESSAGE);
					}
				} else {
					bud.setVisible(false);
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón buyTerritoryButton
	private class BuyTerritoryMouseAdapter extends MouseAdapter {
		MainWindow win;

		public BuyTerritoryMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			final int selectedT = win.getMapView().getSelectedRow();
			if (win.buyTerritoryButton.isEnabled() && selectedT >= 0) {
				System.out.println("Comprando un territorio...");
				try {
					win.gameMgr.getGameEngine().buyTerritory(selectedT);
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final NotCurrentPlayerGameException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final OutOfTurnException e) {
					JOptionPane.showMessageDialog(win,
						"Accion realizada fuera de turno.",
						"Fuera de turno",
						JOptionPane.WARNING_MESSAGE);
				} catch (final PendingAttackException e) {
					JOptionPane.showMessageDialog(win,
						"Hay otro ataque en curso.",
						"Ataques simultáneos",
						JOptionPane.WARNING_MESSAGE);
				} catch (final NotEnoughMoneyException e) {
					JOptionPane.showMessageDialog(
						win,
						"No tienes dinero suficiente para la acción seleccionada.",
						"Dinero insuficiente",
						JOptionPane.WARNING_MESSAGE);
				} catch (final OcupiedTerritoryException e) {
					JOptionPane.showMessageDialog(win,
						"El territorio seleccionado está ocupado.",
						"Territorio ocupado",
						JOptionPane.WARNING_MESSAGE);
				} catch (final InvalidTerritoryException e) {
					JOptionPane.showMessageDialog(win,
						"El territorio seleccionado no es válido.",
						"Territorio no válido",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	//Mini clase privada para dar funcionalidad al botón exitGameButton
	private class ExitGameMouseAdapter extends MouseAdapter {
		MainWindow win;

		public ExitGameMouseAdapter(MainWindow win) {
			this.win = win;
		}

		@Override
		public void mouseClicked(MouseEvent evt) {
			System.out.println("Desconectándose de la partida...");
			final String question = "¿Está seguro de que desea abandonar la partida?";
			final int confirm = JOptionPane.showConfirmDialog(
				mGameListPanel,
				question,
				"confirmación", JOptionPane.YES_NO_OPTION);
			if (confirm == 0) {
				try {
					win.gameMgr.disconnectFromGame();
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
				} catch (final InvalidTimeException e) {
				} catch (final NotCurrentPlayerGameException e) {
				}
				win.setupListGUI();
			}
		}
	}

	@Override
	public void negotiationRequested(int money, int soldiers) {
		final NegotiationDialogThread nrdt = new NegotiationDialogThread(
			"negotiationRequested");
		nrdt.setData(this, money, soldiers);
		nrdt.start();
	}

	@Override
	public void territoryUnderAttack(TerritoryDecorator src, TerritoryDecorator dst, Arsenal arsenal) {
		final DialogThread radt = new DialogThread("territoryUnderAttack");
		radt.setData(this, src, dst, arsenal);
		radt.start();
	}

	//mini clase privada que lanza como un hilo el dialogo de petición de negociación
	private class NegotiationDialogThread extends Thread {
		private MainWindow win;
		private int money, soldiers;

		public NegotiationDialogThread(String dialogName) {
			super(dialogName);
		}

		public void setData(MainWindow win, int money, int soldiers) {
			this.win = win;
			this.money = money;
			this.soldiers = soldiers;
		}

		@Override
		public void run() {
			System.out.println("negotiationRequested");
			final Object[] options = {
					"Sí", "No"
			};
			final String question = "Quieren nogociar con usted\nofreciéndole "
					+ soldiers + "soldados y " + money + " gallifantes.\n"
					+ "¿Acepta la negociación?";

			final int opt = JOptionPane.showOptionDialog(win, question,
				"Negociacion", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if (opt == JOptionPane.YES_OPTION) {
				try {
					win.getGameManager().getGameEngine().resolveNegotiation();
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final NotCurrentPlayerGameException e) {
					MainWindow.this.showErrorAndExitGame(e);
				}
			} else {
				win.getGameManager().getGameEngine().resolveAttack();
			}
		}
	}

	//Mini clase que lanza hilos
	private class DialogThread extends Thread {
		private MainWindow win;
		private TerritoryDecorator srcT;
		private TerritoryDecorator dstT;
		private Arsenal arsenal;

		public DialogThread(String dialogName) {
			super(dialogName);
		}

		public void setData(MainWindow win, TerritoryDecorator srcT, TerritoryDecorator dstT, Arsenal arsenal) {
			this.win = win;
			this.srcT = srcT;
			this.dstT = dstT;
			this.arsenal = arsenal;
		}

		@Override
		public void run() {
			System.out.println("territoryUnderAttack");
			final ReplyAttackDialog rad = new ReplyAttackDialog(win, srcT,
				dstT, arsenal);
			rad.setModal(true);
			rad.setVisible(true);
			System.out.println(rad.isAttackAccepted());
			if (rad.isAttackAccepted()) {
				try {
					win.getGameManager().getGameEngine().acceptAttack();
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final OutOfTurnException e) {
					JOptionPane.showMessageDialog(win,
									"Accion realizada fuera de turno.",
									"Fuera de turno",
									JOptionPane.WARNING_MESSAGE);
				} catch (final InvalidTimeException e) {
					MainWindow.this.showErrorAndExitGame(e);
				}
			} else {
				try {
					win.getGameManager().getGameEngine().requestNegotiation(
									rad.getMoney(), rad.getSoldierCount());
				} catch (final RemoteException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final InvalidSessionException e) {
					MainWindow.this.showErrorAndExit(e);
				} catch (final GameNotFoundException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final OutOfTurnException e) {
					JOptionPane.showMessageDialog(win,
						"Accion realizada fuera de turno.",
						"Fuera de turno",
						JOptionPane.WARNING_MESSAGE);
				} catch (final InvalidTimeException e) {
					MainWindow.this.showErrorAndExitGame(e);
				} catch (final NotEnoughMoneyException e) {
					JOptionPane.showMessageDialog(
						win,
						"No tienes dinero suficiente para la acción seleccionada.",
						"Dinero insuficiente",
						JOptionPane.WARNING_MESSAGE);
				} catch (final NotEnoughUnitsException e) {
					JOptionPane.showMessageDialog(
						win,
						"No tienes unidades suficientes para la acción seleccionada.",
						"Unidades insuficientes",
						JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	//Miniclase que captura los cambios en el MapView
	private class MapViewObserver implements ListSelectionListener {
		private final MainWindow win;
		private final MapView mv;

		public MapViewObserver(MainWindow win, MapView mv) {
			this.win = win;
			this.mv = mv;
		}

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			final int Tsel = mv.getSelectedRow();
			if (Tsel != -1) {
				final Player p = win.getGameManager().getGameEngine().getMapListModel().getTerritoryAt(
					Tsel).getPlayer();
				if (p != null) {
					if (p.getName().equals(
						win.getGameManager().getGameEngine().getPlayerListModel().getSelfPlayer().getName())) {
						System.out.println("Soy yo");
						win.attackButton.setEnabled(true);
						win.buyUnitsButton.setEnabled(true);
						win.moveUnitsButton.setEnabled(true);
						win.sendSpyButton.setEnabled(false);
						win.buyTerritoryButton.setEnabled(false);
					} else {
						System.out.println("Es otro");
						win.sendSpyButton.setEnabled(true);
						win.buyTerritoryButton.setEnabled(false);
						win.attackButton.setEnabled(false);
						win.buyUnitsButton.setEnabled(false);
						win.moveUnitsButton.setEnabled(false);
					}
				} else {
					System.out.println("No hay nadie");
					win.buyTerritoryButton.setEnabled(true);
					win.sendSpyButton.setEnabled(false);
					win.attackButton.setEnabled(false);
					win.buyUnitsButton.setEnabled(false);
					win.moveUnitsButton.setEnabled(false);
				}
			} else {
				System.out.println("Estoy en el mar");
				win.buyTerritoryButton.setEnabled(false);
				win.sendSpyButton.setEnabled(false);
				win.attackButton.setEnabled(false);
				win.buyUnitsButton.setEnabled(false);
				win.moveUnitsButton.setEnabled(false);
			}

		}
	}

	//Miniclase que captura los cambios en las listas de partidas
	private class GameListObserver implements ListSelectionListener {
		private final MainWindow win;

		public GameListObserver(MainWindow win) {
			this.win = win;
		}

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			win.connectGameButton.setEnabled(mCurrentList.getSelectedRow() != -1);
			win.joinGameButton.setEnabled(mOpenList.getSelectedRow() != -1);
		}
	}

	public JTextArea getActionGame() {
		return actionGame;
	}

	public void setActionGame(JTextArea actionGame) {

		actionGame.setEditable(false);
		final String list = "Información de la Partida\n\n";
		this.actionGame = actionGame;
		this.getActionGame().setText(list);
	}

	@Override
	public void attackEvent(TerritoryDecorator src, TerritoryDecorator dst) {
		final String list = "El territorio " + src.getName()
				+ " ataca al territorio " + dst.getName() + "\n";
		this.getActionGame().append(list);
	}

	@Override
	public void buyTerritoryEvent(TerritoryDecorator t) {
		final String list = "Has comprado el territorio " + t.getName() + "\n";
		this.getActionGame().append(list);
	}

	@Override
	public void buyUnitsEvent(TerritoryDecorator t) {
		final String list = "Has comprado unidades en " + t.getName() + "\n";
		this.getActionGame().append(list);
	}

	@Override
	public void negotiationEvent(TerritoryDecorator src, TerritoryDecorator dst) {
		final String list = "El territorio " + src.getName()
				+ " esta negociando con el territorio " + dst.getName() + "\n";
		this.getActionGame().append(list);
	}

	@Override
	public void turnChangedEvent(Player p) {
		final Player self = gameMgr.getGameEngine().getPlayerListModel().getSelfPlayer();
		if (p.equals(self))
			JOptionPane.showMessageDialog(null, "Es tu turno", "Turno",
				JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "Ya no es tu turno", "Turno",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void winnerEvent(Player p) {
		final String list = "El jugador " + p.getName() + "ha ganado\n";
		this.getActionGame().append(list);
		JOptionPane.showMessageDialog(null, "Ganador", list,
			JOptionPane.INFORMATION_MESSAGE);
	}
}
