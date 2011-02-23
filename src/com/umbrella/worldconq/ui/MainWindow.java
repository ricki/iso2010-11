package com.umbrella.worldconq.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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

import com.umbrella.worldconq.domain.GameManager;
import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Player;

public class MainWindow extends JFrame implements GameEventListener {

	private static final long serialVersionUID = -5107198177153703399L;

	private final GameManager gameMgr;

	private JToolBar mGameListToolBar = null;
	private JPanel mGameListPanel = null;
	private JPanel mGamePanel = null;
	private JPanel mGameInfoPanel = null;
	private JToolBar mPlayToolBar = null;
	private JTable mOpenList = null;
	private JTable mCurrentList = null;

	//Botones de la barra mPlayToolbar
	private JButton moveUnitsButton; //Botón para mover unidades de un territorio a otro
	private JButton attackButton; //Botón para atacar un territorio
	private JButton buyUnitsButton; //Botón para comprar refuerzos
	private JButton sendSpyButton; //Botón para enviar un espía a un territorio
	private JButton buyTerritoryButton; //Botón para comprar territorios
	private JButton exitGameButton; //Botón para desconectarse de una partida
	private JButton logoutButton; //Botón para quitar y salir del juego (cerrar sesión)
	private MapView mv; //MapView

	public MainWindow(GameManager gameMgr) {
		super();
		this.gameMgr = gameMgr;
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

		final JButton updateListButton = new JButton("Actualizar lista");
		updateListButton.addMouseListener(new UpdateListMouseAdapter());
		mGameListToolBar.add(updateListButton);

		final JButton createGameButton = new JButton("Crear partida");
		createGameButton.addMouseListener(new CreateGameMouseAdapter());
		mGameListToolBar.add(createGameButton);

		final JButton joinGameButton = new JButton("Unirse a la partida");
		joinGameButton.addMouseListener(new JoinGameMouseAdapter());
		mGameListToolBar.add(joinGameButton);

		final JButton connectGameButton = new JButton("Conectarse a partida");
		connectGameButton.addMouseListener(new ConnectGameMouseAdapter(this));
		mGameListToolBar.add(connectGameButton);

		logoutButton = new JButton("Cerrar sesión");
		logoutButton.addMouseListener(new LogoutMouseAdapter(this));
		mGameListToolBar.add(logoutButton);

		this.setupListGUI();
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
	public void generateButtons() {
		moveUnitsButton = new JButton("Mover tropas"); //Botón para mover unidades de un territorio a otro
		attackButton = new JButton("Atacar"); //Botón para atacar un territorio
		buyUnitsButton = new JButton("Comprar refuerzos"); //Botón para comprar refuerzos
		sendSpyButton = new JButton("Enviar espía"); //Botón para enviar un espía a un territorio
		buyTerritoryButton = new JButton(
			"Comprar territorio"); //Botón para comprar territorios
		exitGameButton = new JButton("Desconectarse"); //Botón para desconectarse de la partida

		//Añado un capturador de eventos a cada botón
		attackButton.addMouseListener(new AttackMouseAdapter(this));
		moveUnitsButton.addMouseListener(new MoveUnitsMouseAdapter(this));
		sendSpyButton.addMouseListener(new SendSpyMouseAdapter(this));
		buyUnitsButton.addMouseListener(new BuyUnitsMouseAdapter(this));
		buyTerritoryButton.addMouseListener(new BuyTerritoryMouseAdapter(this));
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

	public void setupListGUI() {
		this.getContentPane().add(mGameListToolBar, BorderLayout.NORTH);
		this.getContentPane().add(this.getGameListPanel(), BorderLayout.CENTER);
		mGameListToolBar.setVisible(true);
		this.getGameListPanel().setVisible(true);
	}

	public void setupGameGUI() throws IOException {
		// hacemos invisible lo anterior
		this.getGameListPanel().setVisible(false);
		mGameListToolBar.setVisible(false);
		// mostramos el mapa y lo demas
		mv = new MapView(
			gameMgr.getGameEngine().getMapListModel());

		this.generateButtons();
		this.getContentPane().add(mPlayToolBar, BorderLayout.NORTH);
		this.getContentPane().add(this.getGamePanel(mv), BorderLayout.CENTER);
		this.getContentPane().add(this.getGameInfoPanel(mv), BorderLayout.EAST);
		mPlayToolBar.setVisible(true);
		this.getGamePanel(mv).setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

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
			mGamePanel.setLayout(new BoxLayout(mGamePanel, BoxLayout.Y_AXIS));
			mv.setFondo();
			mGamePanel.add(mv);
			//añadimos el panel para la informacion de la partida
			mv.setActionGame(new JEditorPane());
			final JScrollPane ActionGameScroll = new JScrollPane(
				mv.getActionGame());
			ActionGameScroll.setPreferredSize(new Dimension(300, 125));
			mGamePanel.add(ActionGameScroll);
		}
		return mGamePanel;
	}

	private JPanel getGameInfoPanel(MapView mv) {
		if (mGameInfoPanel == null) {
			mGameInfoPanel = new JPanel();
			mGameInfoPanel.setLayout(new BoxLayout(mGameInfoPanel,
				BoxLayout.Y_AXIS));
			mv.setInfoPlayer(new JEditorPane());
			final JScrollPane listPlayerSroll = new JScrollPane(new PlayerView(
				gameMgr.getGameEngine().getPlayerListModel()));
			listPlayerSroll.setPreferredSize(new Dimension(150, 300));

			final JScrollPane listInfoSroll = new JScrollPane(
				mv.getInfoPlayer());
			listInfoSroll.setPreferredSize(new Dimension(150, 300));
			mGameInfoPanel.add(listPlayerSroll);
			mGameInfoPanel.add(listInfoSroll);
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
					gameMgr.createGame(dlg.getGameName(),
						dlg.getDescription(), dlg.getCalendarList(),
						dlg.getTurnTime(),
						dlg.getDefTime(), dlg.getNegTime());
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
				gameMgr.updateGameList();
			} catch (final Exception e) {
				e.printStackTrace();
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
					final ArrayList<Calendar> session = gameMgr.getOpenGameListModel().getGameAt(
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
							gameMgr.updateGameList();
						}
					} else {
						//No hay ninguna sesión activa, se actuliza la lista de partidas.
						gameMgr.updateGameList();
					}
				} catch (final Exception e) {
					e.printStackTrace();
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
				} catch (final Exception e) {
					e.printStackTrace();
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
				win.dispose();
				win.getGameManager().getUserManager().closeSession();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			final ArrayList<String> adjListNames = new ArrayList<String>();
			final TerritoryDecorator srcT;
			System.out.println("Atacando...");
			if (attackButton.isEnabled()) {
				final int selectedT = win.mv.getSelectedRow();
				srcT = win.gameMgr.getGameEngine().getMapListModel().getTerritoryAt(
					selectedT);
				adjList = srcT.getAdjacentTerritories();
				for (int i = 0; i < adjList.size(); i++) {
					if ((adjList.get(i).getPlayer() != null)
							&& (!(((adjList.get(i)).getOwner()).equals(win.gameMgr.getGameEngine().getPlayerListModel().getSelfPlayer().getName())))) {
						adjListNames.add(adjList.get(i).getName());
					}
				}
				lad = new LaunchAttackDialog(
					win,
					win.gameMgr.getGameEngine().getMapListModel().getTerritoryAt(
						selectedT), adjListNames);
				lad.setVisible(true);
				if (lad.getSelection() == true) {
					try {
						win.getGameManager().getGameEngine().attackTerritory(
							selectedT, lad.getTerritoryIndex(),
							lad.getSoldierCount(), lad.getCannonCount(),
							lad.getMissileCount(), lad.getICBMCount());
						lad.setVisible(false);
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					lad.setVisible(false);
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
			TerritoryDecorator srcT;
			final ArrayList<String> adjListNames = new ArrayList<String>();
			int selectedT;
			System.out.println("Moviendo unidades...");
			if (moveUnitsButton.isEnabled()) {
				selectedT = win.getMapView().getSelectedRow();
				srcT = win.getGameManager().getGameEngine().getMapListModel().getTerritoryAt(
					selectedT);
				adjList = srcT.getAdjacentTerritories();
				for (int i = 0; i < adjList.size(); i++) {
					if ((adjList.get(i).getPlayer() != null)
							&& ((((adjList.get(i)).getOwner()).equals(win.gameMgr.getGameEngine().getPlayerListModel().getSelfPlayer().getName())))) {
						adjListNames.add(adjList.get(i).getName());
					}
				}
				mud = new MoveUnitsDialog(win, srcT, adjListNames);
				mud.setVisible(true);
				if (mud.getSelection() == true) {
					try {
						mud.setVisible(false);
						win.getGameManager().getGameEngine().moveUnits(
							selectedT, mud.getDestiny(), mud.getSoldierCount(),
							mud.getCannonCount(), mud.getMissileCount(),
							mud.getICBMCount(), mud.getAntiMissileCount());
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					mud.setVisible(false);
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
			System.out.println("Enviando un espía...");
			if (sendSpyButton.isEnabled()) {
				//Completar
				//SendSpyDialog sed = new SendSpyDialog(this, TerritoryDecorator src,	ArrayList < String > adjacentList);			
				//sed.setVisible(true);		
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
			int selectedT;
			System.out.println("Comprando refuerzos...");
			if (buyUnitsButton.isEnabled()) {
				selectedT = win.getMapView().getSelectedRow();
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
				bud.setVisible(true);
				if (bud.getSelection() == true) {
					try {
						bud.setVisible(false);
						win.getGameManager().getGameEngine().buyUnits(
							selectedT, bud.getSoldierCount(),
							bud.getCannonCount(), bud.getMissileCount(),
							bud.getICBMCount(), bud.getAntiMissileCount());
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
			TerritoryDecorator srcT;
			Player p;
			final ArrayList<String> adjListNames = new ArrayList<String>();
			int selectedT;
			System.out.println("Comprando un territorio...");
			if (buyTerritoryButton.isEnabled()) {
				selectedT = win.getMapView().getSelectedRow();
				srcT = win.getGameManager().getGameEngine().getMapListModel().getTerritoryAt(
					selectedT);
				p = win.getGameManager().getGameEngine().getPlayerListModel().getSelfPlayer();
				if (srcT.getPlayer() == null) {
					if (p.getMoney() >= srcT.getPrice()) {
						try {
							win.getGameManager().getGameEngine().buyTerritory(
								selectedT);
						} catch (final Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(win,
							"No dispone de suficiente dinero",
							"Dinero insuficiente",
							JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(win,
						"Este territorio no está libre",
						"Territorio ocupado",
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
			final Object[] options = {
					"Sí", "No"
			};
			final String question = "¿Está seguro de que desea abandonar la partida?";
			final String confirm = "";
			final Object questionDialog = JOptionPane.showInputDialog(
				win,
				(question),
				"Abandonar la partida",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options, options[0]);
			if (questionDialog.toString().equals("Sí")) {
				win.mPlayToolBar.setVisible(false);
				win.mGamePanel.setVisible(false);
				win.mGameInfoPanel.setVisible(false);
				win.setupListGUI();
			} else {
				//No pasa nada
			}

		}
	}

	@Override
	public void negotiationRequested(int money, int soldiers) {
		final Object[] options = {
				"Sí", "No"
		};
		final String question = "Quieren nogociar con usted\nofreciéndole "
				+ soldiers + "soldados y " + money + " gallifantes.\n"
				+ "¿Acepta la negociación?";
		final String confirm = "";
		final Object questionDialog = JOptionPane.showInputDialog(
			this,
			(question),
			"Abandonar la partida",
			JOptionPane.QUESTION_MESSAGE,
			null,
			options, options[0]);
		if (questionDialog.toString().equals("Sí")) {
			this.getGameManager().getGameEngine().negotiationRequested(money,
				soldiers);
		} else {
			//??
		}

	}

	@Override
	public void territoryUnderAttack(TerritoryDecorator src, TerritoryDecorator dst) {
		// TODO Auto-generated method stub

	}
}
