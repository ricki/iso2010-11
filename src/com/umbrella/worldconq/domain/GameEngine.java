package com.umbrella.worldconq.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.NegativeValueException;
import com.umbrella.worldconq.exceptions.NotEnoughMoneyException;
import com.umbrella.worldconq.exceptions.NotEnoughUnitsException;
import com.umbrella.worldconq.exceptions.OcupiedTerritoryException;
import com.umbrella.worldconq.exceptions.OutOfTurnException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
import com.umbrella.worldconq.exceptions.UnocupiedTerritoryException;
import com.umbrella.worldconq.ui.GameEventListener;
import communications.IClient.TimeType;

import domain.Arsenal;
import domain.EventType;
import domain.Game;
import domain.Player;
import domain.Spy;
import domain.Territory;
import exceptions.GameNotFoundException;
import exceptions.InvalidSessionException;
import exceptions.InvalidTerritoryException;
import exceptions.InvalidTimeException;
import exceptions.NotCurrentPlayerGameException;

public class GameEngine implements ClientCallback {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;
	private final Game mGame;
	private final Session session;
	private final ServerAdapter adapter;
	private final GameEventListener gameListener;
	private Attack mCurrentAttack;

	public GameEngine(Game game, Session session, ServerAdapter adapter, GameEventListener gameListener) throws NotCurrentPlayerGameException {
		if (game == null)
			throw new NullPointerException();
		if (session == null)
			throw new NullPointerException();
		if (adapter == null)
			throw new NullPointerException();
		if (gameListener == null)
			throw new NullPointerException();

		mCurrentAttack = null;
		this.gameListener = gameListener;
		mGame = game;
		this.session = session;
		this.adapter = adapter;

		final Player self = game.strToPlayer(session.getUser(), game);
		if (self == null)
			throw new NotCurrentPlayerGameException(null);

		if (game.getPlayers() == null)
			throw new NullPointerException();

		mPlayerListModel = new PlayerListModel(self, game.getPlayers());

		mMapListModel = new MapModel(self, mPlayerListModel);

		final ArrayList<TerritoryDecorator> mMapList = new ArrayList<TerritoryDecorator>();
		final ArrayList<Territory> map = game.getMap();

		for (final Territory t : map) {
			mMapList.add(new TerritoryDecorator(t, mMapListModel,
				mPlayerListModel));
		}

		mMapListModel.setData(mMapList);

	}

	public UUID getId() {
		return mGame.getGameInfo().getId();
	}

	public String getName() {
		return mGame.getGameInfo().getName();
	}

	public MapModel getMapListModel() {
		return mMapListModel;
	}

	public String getDescription() {
		return mGame.getGameInfo().getDescription();
	}

	public PlayerListModel getPlayerListModel() {
		return mPlayerListModel;

	}

	public Game getGame() {
		return mGame;
	}

	public void setMapListModel(MapModel mMapListModel) {
		this.mMapListModel = mMapListModel;
	}

	public void setPlayerListModel(PlayerListModel mPlayerListModel) {
		this.mPlayerListModel = mPlayerListModel;
	}

	public void attackTerritory(int src, int dst, int soldiers, int cannons, int missiles, int icbm) throws RemoteException, InvalidTerritoryException, GameNotFoundException, InvalidSessionException, InvalidTimeException, OutOfTurnException, UnocupiedTerritoryException, NotEnoughUnitsException, PendingAttackException {
		this.checkInTurn();

		final TerritoryDecorator srcTerritory = mMapListModel.getTerritoryAt(src);
		final TerritoryDecorator dstTerritory = mMapListModel.getTerritoryAt(dst);

		if (srcTerritory.getPlayer() == null)
			throw new UnocupiedTerritoryException(src);

		if (!srcTerritory.getPlayer().equals(mPlayerListModel.getSelfPlayer()))
			throw new InvalidTerritoryException();

		if (dstTerritory.getPlayer() == null)
			throw new UnocupiedTerritoryException(dst);

		if (dstTerritory.getPlayer().equals(mPlayerListModel.getSelfPlayer()))
			throw new InvalidTerritoryException();

		if (!srcTerritory.getAdjacentTerritories().contains(dstTerritory))
			throw new InvalidTerritoryException();

		if (soldiers < 0)
			throw new NegativeValueException();
		if (soldiers > srcTerritory.getNumSoldiers())
			throw new NotEnoughUnitsException(soldiers,
			srcTerritory.getNumSoldiers());

		if (cannons < 0)
			throw new NegativeValueException();
		if (cannons > srcTerritory.getNumTotalCannons())
			throw new NotEnoughUnitsException(cannons,
			srcTerritory.getNumTotalCannons());

		if (missiles < 0)
			throw new NegativeValueException();
		if (missiles > srcTerritory.getNumMissiles())
			throw new NotEnoughUnitsException(missiles,
			srcTerritory.getNumMissiles());

		if (icbm < 0)
			throw new NegativeValueException();
		if (icbm > srcTerritory.getNumICBMs())
			throw new NotEnoughUnitsException(icbm,
			srcTerritory.getNumICBMs());

		if (mCurrentAttack != null)
			throw new PendingAttackException();

		final Arsenal arsenal = new Arsenal(soldiers,
			cannons, missiles, icbm);

		final Attack att = new Attack(arsenal,
			(TerritoryDecorator) srcTerritory.clone(),
			(TerritoryDecorator) dstTerritory.clone());
		adapter.attackTerritory(session, mGame, att);

		mCurrentAttack = att;
	}

	public void acceptAttack() throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, OutOfTurnException {
		this.checkNotInTurn();

		if (mCurrentAttack == null)
			throw new NullPointerException();

		adapter.acceptAttack(session, mGame);
		mCurrentAttack = null;
	}

	public void requestNegotiation(int money, int soldiers) throws RemoteException, GameNotFoundException, InvalidSessionException, InvalidTimeException, OutOfTurnException, NotEnoughMoneyException, NotEnoughUnitsException {
		this.checkNotInTurn();

		if (mCurrentAttack == null)
			throw new NullPointerException();

		if (money < 0)
			throw new NegativeValueException();

		if (money > mPlayerListModel.getSelfPlayer().getMoney())
			throw new NotEnoughMoneyException();

		if (soldiers < 0)
			throw new NegativeValueException();

		if (soldiers > mCurrentAttack.getDestination().getNumSoldiers())
			throw new NotEnoughUnitsException(soldiers,
			mCurrentAttack.getDestination().getNumSoldiers());

		adapter.requestNegotiation(session, mGame, money, soldiers);
		mCurrentAttack = null;
	}

	public void buyUnits(int index, int soldiers, int cannons, int missiles, int icbm, int antimissiles) throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException, InvalidTerritoryException, UnocupiedTerritoryException, OutOfTurnException, NotEnoughMoneyException, PendingAttackException {
		this.checkInTurn();
		if (mCurrentAttack != null)
			throw new PendingAttackException();

		final Player self = mPlayerListModel.getSelfPlayer();

		final TerritoryDecorator t = mMapListModel.getTerritoryAt(index);

		if (t.getPlayer() == null)
			throw new UnocupiedTerritoryException(index);

		if (!t.getPlayer().equals(self))
			throw new InvalidTerritoryException();

		int totalCost = 0;

		if (soldiers < 0)
			throw new NegativeValueException();
		totalCost += UnitInfo.getSoldierCost() * soldiers;

		if (cannons < 0)
			throw new NegativeValueException();
		totalCost += UnitInfo.getCannonCost() * cannons;

		if (missiles < 0)
			throw new NegativeValueException();
		totalCost += UnitInfo.getMissileCost() * missiles;

		if (icbm < 0)
			throw new NegativeValueException();
		totalCost += UnitInfo.getICBMCost() * icbm;

		if (self.getMoney() < totalCost)
			throw new NotEnoughMoneyException();

		final Player playerUpdate = new Player(
			self.getName(),
			self.getMoney() - totalCost,
			self.isOnline(),
			self.isHasTurn(),
			self.getSpies());

		final ArrayList<Player> playerUpdates = new ArrayList<Player>();
		playerUpdates.add(playerUpdate);

		final TerritoryDecorator territoryUpdate = (TerritoryDecorator) t.clone();

		territoryUpdate.setNumAntiMissiles(t.getNumAntiMissiles()
				+ antimissiles);
		territoryUpdate.setNumICBMs(t.getNumICBMs() + icbm);
		territoryUpdate.setNumMissiles(t.getNumMissiles() + missiles);
		territoryUpdate.setNumSoldiers(t.getNumSoldiers()
				+ soldiers);

		final int numCannons[] = new int[3];
		numCannons[0] = t.getNumCannons()[0];
		numCannons[1] = t.getNumCannons()[1];
		numCannons[2] = t.getNumCannons()[2] + cannons;
		territoryUpdate.setNumCannons(numCannons);

		final ArrayList<TerritoryDecorator> territoriesUpdate = new ArrayList<TerritoryDecorator>();
		territoriesUpdate.add(territoryUpdate);

		adapter.updateGame(session, mGame, playerUpdates,
			territoriesUpdate, EventType.BuyArsenalEvent);

		mPlayerListModel.updatePlayer(playerUpdate);
		mMapListModel.updateTerritory(territoryUpdate);
	}

	public void moveUnits(int src, int dst, int soldiers, int[] cannons, int missiles, int icbm, int antimissiles) throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException, OutOfTurnException, UnocupiedTerritoryException, InvalidTerritoryException, NotEnoughUnitsException, PendingAttackException {
		this.checkInTurn();
		if (mCurrentAttack != null)
			throw new PendingAttackException();

		final Player self = mPlayerListModel.getSelfPlayer();

		TerritoryDecorator srcTerritory = mMapListModel.getTerritoryAt(src);
		TerritoryDecorator dstTerritory = mMapListModel.getTerritoryAt(dst);

		if (srcTerritory.getPlayer() == null)
			throw new UnocupiedTerritoryException(src);

		if (!srcTerritory.getPlayer().equals(self))
			throw new InvalidTerritoryException();

		if (dstTerritory.getPlayer() == null)
			throw new UnocupiedTerritoryException(dst);

		if (!dstTerritory.getPlayer().equals(self))
			throw new InvalidTerritoryException();

		if (!srcTerritory.getAdjacentTerritories().contains(dstTerritory))
			throw new InvalidTerritoryException();

		if (soldiers < 0)
			throw new NegativeValueException();
		if (soldiers > srcTerritory.getNumSoldiers())
			throw new NotEnoughUnitsException(soldiers,
			srcTerritory.getNumSoldiers());

		if (cannons[0] < 0)
			throw new NegativeValueException();
		if (cannons[1] < 0)
			throw new NegativeValueException();
		if (cannons[2] < 0)
			throw new NegativeValueException();
		if (cannons[0] > srcTerritory.getNumCannons()[0])
			throw new NotEnoughUnitsException(cannons[0],
			srcTerritory.getNumCannons()[0]);
		if (cannons[1] > srcTerritory.getNumCannons()[1])
			throw new NotEnoughUnitsException(cannons[1],
			srcTerritory.getNumCannons()[1]);
		if (cannons[2] > srcTerritory.getNumCannons()[2])
			throw new NotEnoughUnitsException(cannons[2],
			srcTerritory.getNumCannons()[2]);

		if (missiles < 0)
			throw new NegativeValueException();
		if (missiles > srcTerritory.getNumMissiles())
			throw new NotEnoughUnitsException(missiles,
			srcTerritory.getNumMissiles());

		if (icbm < 0)
			throw new NegativeValueException();
		if (icbm > srcTerritory.getNumICBMs())
			throw new NotEnoughUnitsException(icbm,
			srcTerritory.getNumICBMs());

		srcTerritory = (TerritoryDecorator) srcTerritory.clone();

		srcTerritory.setNumSoldiers(srcTerritory.getNumSoldiers()
				- soldiers);

		final int numCannons[] = new int[3];
		for (int i = 0; i < 3; i++)
			numCannons[i] = srcTerritory.getNumCannons()[i] - cannons[i];
		srcTerritory.setNumCannons(numCannons);

		srcTerritory.setNumMissiles(srcTerritory.getNumMissiles()
				- missiles);
		srcTerritory.setNumICBMs(srcTerritory.getNumICBMs()
				- icbm);
		srcTerritory.setNumAntiMissiles(srcTerritory.getNumAntiMissiles()
				- antimissiles);

		dstTerritory = (TerritoryDecorator) dstTerritory.clone();

		dstTerritory.setNumSoldiers(dstTerritory.getNumSoldiers()
				+ soldiers);

		for (int i = 0; i < 3; i++)
			numCannons[i] = srcTerritory.getNumCannons()[i] + cannons[i];
		dstTerritory.setNumCannons(numCannons);

		dstTerritory.setNumMissiles(dstTerritory.getNumMissiles()
				+ missiles);
		dstTerritory.setNumICBMs(dstTerritory.getNumICBMs()
				+ icbm);
		dstTerritory.setNumAntiMissiles(dstTerritory.getNumAntiMissiles()
				+ antimissiles);

		final ArrayList<TerritoryDecorator> territoriesUpdate = new ArrayList<TerritoryDecorator>();
		territoriesUpdate.add(srcTerritory);
		territoriesUpdate.add(dstTerritory);
		adapter.updateGame(session, mGame,
			new ArrayList<Player>(), territoriesUpdate,
			EventType.UnknownEvent);

		mMapListModel.updateTerritory(srcTerritory);
		mMapListModel.updateTerritory(dstTerritory);
	}

	public void buyTerritory(int index) throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException, OcupiedTerritoryException, InvalidTerritoryException, OutOfTurnException, NotEnoughMoneyException, PendingAttackException {
		this.checkInTurn();
		if (mCurrentAttack != null)
			throw new PendingAttackException();

		final Player self = mPlayerListModel.getSelfPlayer();
		final TerritoryDecorator territory = mMapListModel.getTerritoryAt(index);

		if (territory.getPlayer() != null)
			throw new OcupiedTerritoryException(index);

		final ArrayList<TerritoryDecorator> adj = territory.getAdjacentTerritories();

		boolean validTerritory = false;
		for (final TerritoryDecorator t : adj) {
			if (self.equals(t.getPlayer())) {
				validTerritory = true;
				break;
			}
		}

		if (!validTerritory)
			throw new InvalidTerritoryException();

		if (self.getMoney() < territory.getPrice())
			throw new NotEnoughMoneyException();

		final Player playerUpdate = new Player(
			self.getName(),
			self.getMoney() - mMapListModel.getTerritoryAt(index).getPrice(),
			self.isOnline(),
			self.isHasTurn(),
			self.getSpies());

		final ArrayList<Player> playerUpdates = new ArrayList<Player>();
		playerUpdates.add(playerUpdate);

		final TerritoryDecorator territoryUpdate = (TerritoryDecorator) territory.clone();

		territoryUpdate.setPlayer(self);

		final ArrayList<TerritoryDecorator> territoriesUpdate = new ArrayList<TerritoryDecorator>();
		territoriesUpdate.add(territoryUpdate);

		adapter.updateGame(session, mGame, playerUpdates,
			territoriesUpdate, EventType.BuyTerritoryEvent);

		mPlayerListModel.updatePlayer(playerUpdate);
		mMapListModel.updateTerritory(territoryUpdate);
	}

	public void deploySpy(int index) throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException, OutOfTurnException, NotEnoughMoneyException, PendingAttackException {
		this.checkInTurn();
		if (mCurrentAttack != null)
			throw new PendingAttackException();

		final Player self = mPlayerListModel.getSelfPlayer();

		if (self.getMoney() < UnitInfo.getSpyCost())
			throw new NotEnoughMoneyException();

		final ArrayList<Spy> spyList = new ArrayList<Spy>();
		spyList.addAll(self.getSpies());
		spyList.add(new Spy(index, 0));

		final Player p = new Player(
			self.getName(), self.getMoney() - UnitInfo.getSpyCost(),
			self.isOnline(), self.isHasTurn(), spyList);

		final ArrayList<Player> playerUpdate = new ArrayList<Player>();
		playerUpdate.add(p);
		adapter.updateGame(session, mGame, playerUpdate,
			new ArrayList<TerritoryDecorator>(), EventType.BuyArsenalEvent);

		mPlayerListModel.updatePlayer(p);
	}

	@Override
	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws InvalidTerritoryException {

		if (src == null)
			throw new NullPointerException();

		if (dst == null)
			throw new NullPointerException();

		if (arsenal == null)
			throw new NullPointerException();

		final TerritoryDecorator territoryOrigin = new TerritoryDecorator(
			src, mMapListModel, mPlayerListModel);
		final TerritoryDecorator territoryDestination = new TerritoryDecorator(
			dst, mMapListModel, mPlayerListModel);
		final Attack att = new Attack(arsenal, territoryOrigin,
			territoryDestination);

		gameListener.territoryUnderAttack(territoryOrigin,
			territoryDestination, arsenal);

		mCurrentAttack = att;
	}

	@Override
	public void negotiationRequested(int money, int soldiers) {
		if (money < 0)
			money = 0;
		if (soldiers < 0)
			soldiers = 0;
		mCurrentAttack.setOfferedMoney(money);
		mCurrentAttack.setOfferedSoldiers(soldiers);
		gameListener.negotiationRequested(money, soldiers);
	}

	@Override
	public void resolveAttack() {
		mCurrentAttack.resolve();

		final ArrayList<TerritoryDecorator> territoriesUpdate = new ArrayList<TerritoryDecorator>();
		territoriesUpdate.add(mCurrentAttack.getOrigin());
		territoriesUpdate.add(mCurrentAttack.getDestination());

		final ArrayList<Player> playersUpdate = new ArrayList<Player>();

		try {
			adapter.updateGame(session, mGame, playersUpdate,
				territoriesUpdate,
				EventType.AttackEvent);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mMapListModel.updateTerritory(mCurrentAttack.getOrigin());
		mMapListModel.updateTerritory(mCurrentAttack.getDestination());
		mCurrentAttack = null;

	}

	public void resolveNegotiation() throws RemoteException, GameNotFoundException, InvalidSessionException, NotCurrentPlayerGameException {

		//Modificamos el usuario y territorio propio, creando un clon por si falla el update mantener los datos
		final Player playerUpdateOrigin = new Player(
			mPlayerListModel.getSelfPlayer().getName(),
			mPlayerListModel.getSelfPlayer().getMoney()
				+ mCurrentAttack.getOfferedMoney(),
			mPlayerListModel.getSelfPlayer().isOnline(),
			mPlayerListModel.getSelfPlayer().isHasTurn(),
			mPlayerListModel.getSelfPlayer().getSpies());

		final TerritoryDecorator territoryUpdateOrigin = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();
		territoryUpdateOrigin.setNumSoldiers(mCurrentAttack.getOrigin().getNumSoldiers()
				+ mCurrentAttack.getOfferedSoldiers());

		//Modificamos el usuario y territorio contrario, creando un clon por si falla el update mantener los datos
		final Player playerUpdateDestination = new Player(
			mCurrentAttack.getDestination().getPlayer().getName(),
			mCurrentAttack.getDestination().getPlayer().getMoney()
				- mCurrentAttack.getOfferedMoney(),
			mCurrentAttack.getDestination().getPlayer().isOnline(),
			mCurrentAttack.getDestination().getPlayer().isHasTurn(),
			mCurrentAttack.getDestination().getPlayer().getSpies());

		final TerritoryDecorator territoryUpdateDestination = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();
		territoryUpdateDestination.setNumSoldiers(mCurrentAttack.getDestination().getNumSoldiers()
				- mCurrentAttack.getOfferedSoldiers());

		final ArrayList<Player> playerUpdates = new ArrayList<Player>();
		playerUpdates.add(playerUpdateOrigin);
		playerUpdates.add(playerUpdateDestination);

		final ArrayList<TerritoryDecorator> territoriesUpdate = new ArrayList<TerritoryDecorator>();
		territoriesUpdate.add(territoryUpdateOrigin);
		territoriesUpdate.add(territoryUpdateDestination);

		adapter.updateGame(session, mGame, playerUpdates,
			territoriesUpdate,
			EventType.NegotiationEvent);

		mPlayerListModel.updatePlayer(playerUpdateOrigin);
		mPlayerListModel.updatePlayer(playerUpdateDestination);
		mMapListModel.updateTerritory(territoryUpdateOrigin);
		mMapListModel.updateTerritory(territoryUpdateDestination);

		//Elimino el ataque actual.
		mCurrentAttack = null;

	}

	@Override
	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) {
		switch (event) {
		case AttackEvent:
			System.out.println("AttackEvent");
			break;
		case BuyArsenalEvent:
			System.out.println("BuyArsenalEvent");
			break;
		case BuyTerritoryEvent:
			System.out.println("BuyTerritoryEvent");
			break;
		case NegotiationEvent:
			System.out.println("NegotiationEvent");
			break;
		case TurnChanged:
			System.out.println("TurnChanged");
			break;
		case UnknownEvent:
			System.out.println("UnknownEvent");
			break;
		}
		final Player curPlayer = mPlayerListModel.getActivePlayer();
		ArrayList<TerritoryDecorator> terrList = null;

		if (territoryUpdate != null) {
			terrList = new ArrayList<TerritoryDecorator>();
			for (final Territory t : territoryUpdate) {
				terrList.add(new TerritoryDecorator(t, mMapListModel,
					mPlayerListModel));
			}
		}
		System.out.println("territoryUpdate is " + (territoryUpdate != null));

		if (event == EventType.AttackEvent && terrList != null) {
			if (terrList.size() == 2) {
				if (terrList.get(0).getPlayer().equals(curPlayer))
					gameListener.attackEvent(terrList.get(0), terrList.get(1));
				else
					gameListener.attackEvent(terrList.get(1), terrList.get(0));
			}
		} else if (event == EventType.NegotiationEvent && terrList != null) {
			if (terrList.size() == 2) {
				if (terrList.get(0).getPlayer().equals(curPlayer))
					gameListener.negotiationEvent(terrList.get(0),
						terrList.get(1));
				else
					gameListener.negotiationEvent(terrList.get(1),
						terrList.get(0));
			}
		} else if (event == EventType.BuyArsenalEvent && terrList != null) {
			if (terrList.size() == 1) {
				gameListener.buyUnitsEvent(terrList.get(0));
			}
		} else if (event == EventType.BuyTerritoryEvent && terrList != null) {
			if (terrList.size() == 1) {
				gameListener.buyTerritoryEvent(terrList.get(0));
			}
		} else if (event == EventType.TurnChanged) {
			Player next = null;
			for (final Player p : playerUpdate) {
				if (p.isHasTurn()) {
					next = p;
					break;
				}
			}
			gameListener.turnChangedEvent(next);
		}

		for (final Player p : playerUpdate) {
			mPlayerListModel.updatePlayer(p);
		}
		if (terrList != null) {
			for (final TerritoryDecorator t : terrList) {
				mMapListModel.updateTerritory(t);
			}
		}

		if (event == EventType.TurnChanged) {
			final Thread th = new TurnUpdateThread();
			th.run();
		}

	}

	@Override
	public void timeExpired(UUID game, TimeType whatTime) {
		// TODO Auto-generated method stub

	}

	private void checkInTurn() throws OutOfTurnException {
		if (!mPlayerListModel.getSelfPlayer().equals(
			mPlayerListModel.getActivePlayer()))
			throw new OutOfTurnException();
	}

	private void checkNotInTurn() throws OutOfTurnException {
		if (mPlayerListModel.getSelfPlayer().equals(
			mPlayerListModel.getActivePlayer()))
			throw new OutOfTurnException();
	}

	private class TurnUpdateThread extends Thread {
		@Override
		public void run() {
			mCurrentAttack = null;
			final Player self = mPlayerListModel.getSelfPlayer();

			if (!self.equals(mPlayerListModel.getActivePlayer())) return;

			final ArrayList<Spy> spyList = self.getSpies();
			final ArrayList<Spy> newSpyList = new ArrayList<Spy>();
			for (final Spy spy : spyList) {
				spy.setUses(spy.getUses() + 1);
				newSpyList.add(spy);
			}
			self.setSpies(newSpyList);

			try {
				final ArrayList<Player> playerList = new ArrayList<Player>();
				playerList.add(self);
				adapter.updateGame(session, GameEngine.this.getGame(),
					playerList, new ArrayList<TerritoryDecorator>(),
					EventType.UnknownEvent);
				mPlayerListModel.updatePlayer(self);
			} catch (final Exception e) {
				self.setSpies(spyList);
			}
		}
	}

}
