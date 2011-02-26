package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;
import com.umbrella.worldconq.exceptions.NotEnoughMoneyException;
import com.umbrella.worldconq.exceptions.OutOfTurnException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
import com.umbrella.worldconq.ui.GameEventListener;
import communications.IClient.TimeType;

import domain.Arsenal;
import domain.EventType;
import domain.Game;
import domain.Player;
import domain.Spy;
import domain.Territory;
import exceptions.InvalidTerritoryException;

public class GameEngine implements ClientCallback {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;
	private final Game mGame;
	private final Session session;
	private final ServerAdapter adapter;
	private final GameEventListener gameListener;
	private Attack mCurrentAttack;

	public GameEngine(Game game, Session session, ServerAdapter adapter, GameEventListener gameListener) throws InvalidArgumentException {
		mCurrentAttack = null;
		this.gameListener = gameListener;
		mGame = game;
		this.session = session;
		this.adapter = adapter;

		mPlayerListModel = new PlayerListModel(game.strToPlayer(
			this.session.getUser(),
			game), game.getPlayers());

		mMapListModel = new MapModel(game.strToPlayer(this.session.getUser(),
			game), mPlayerListModel);

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

	public void attackTerritory(int src, int dst, int soldiers, int cannons, int missiles, int icbm) throws Exception {
		this.checkInTurn();

		if (src < 0 || src > 41)
			throw new InvalidArgumentException();
		if (dst < 0 || dst > 41)
			throw new InvalidArgumentException();

		if (mCurrentAttack != null)
			throw new PendingAttackException();
		else {
			final TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
				src);
			final TerritoryDecorator dstTerritory = this.getMapListModel().getTerritoryAt(
				dst);

			if (srcTerritory.getPlayer() != null
					&& srcTerritory.getOwner().equals(session.getUser())
					&& dstTerritory.getPlayer() != null
					&& !dstTerritory.getOwner().equals(session.getUser())
					&& srcTerritory.getAdjacentTerritories().contains(
						dstTerritory)
					&& soldiers >= 0 && cannons >= 0
					&& missiles >= 0 && icbm >= 0
					&& soldiers <= srcTerritory.getNumSoldiers()
					&& cannons <= srcTerritory.getNumTotalCannons()
					&& missiles <= srcTerritory.getNumMissiles()
					&& icbm <= srcTerritory.getNumICBMs()) {

				final Arsenal arsenal = new Arsenal(soldiers,
					cannons, missiles, icbm);

				final Attack att = new Attack(arsenal,
					(TerritoryDecorator) srcTerritory.clone(),
					(TerritoryDecorator) dstTerritory.clone());
				adapter.attackTerritory(session, mGame, att);

				mCurrentAttack = att;
			} else
				throw new InvalidArgumentException();
		}
	}

	public void acceptAttack() throws Exception {
		if (mPlayerListModel.getSelfPlayer().equals(
			mPlayerListModel.getActivePlayer())) throw new OutOfTurnException();
		if (mCurrentAttack == null)
			throw new Exception();
		adapter.acceptAttack(session, mGame);
		mCurrentAttack = null;
	}

	public void requestNegotiation(int money, int soldiers) throws Exception {
		if (mPlayerListModel.getSelfPlayer().equals(
			mPlayerListModel.getActivePlayer())) throw new OutOfTurnException();
		if (mCurrentAttack == null)
			throw new Exception();
		if (money >= 0 && soldiers >= 0
				&& mPlayerListModel.getSelfPlayer().getMoney() >= money
				&& mCurrentAttack.getDestination().getNumSoldiers() >= soldiers) {
			adapter.requestNegotiation(session, mGame, money, soldiers);
			mCurrentAttack = null;
		} else
			throw new InvalidArgumentException();
	}

	public void buyUnits(int Territory, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {
		this.checkInTurn();

		if (Territory < 0 || Territory > 41)
			throw new InvalidArgumentException();

		final TerritoryDecorator t = this.getMapListModel().getTerritoryAt(
			Territory);

		if (t.getPlayer() != null
				&& t.getOwner().equals(session.getUser())
				&& soldiers >= 0 && cannons >= 0
				&& missiles >= 0 && ICMB >= 0 && antimissiles >= 0) {

			final int spendingMoney = soldiers
					* UnitInfo.getPriceSoldier() + cannons
					* UnitInfo.getPriceCannon() + missiles
					* UnitInfo.getPriceMissil() + ICMB
					* UnitInfo.getPriceICBM() + antimissiles
					* UnitInfo.getPriceAntiMissile();

			if (mPlayerListModel.getSelfPlayer().getMoney() >= spendingMoney) {
				final Player playerUpdate = new Player(
					mPlayerListModel.getSelfPlayer().getName(),
					mPlayerListModel.getSelfPlayer().getMoney() - spendingMoney,
					mPlayerListModel.getSelfPlayer().isOnline(),
					mPlayerListModel.getSelfPlayer().isHasTurn(),
					mPlayerListModel.getSelfPlayer().getSpies());

				final ArrayList<Player> playerUpdates = new ArrayList<Player>();
				playerUpdates.add(playerUpdate);

				final TerritoryDecorator territoryUpdate = (TerritoryDecorator) t.clone();

				territoryUpdate.setNumAntiMissiles(t.getNumAntiMissiles()
						+ antimissiles);
				territoryUpdate.setNumICBMs(t.getNumICBMs() + ICMB);
				territoryUpdate.setNumMissiles(t.getNumMissiles() + missiles);
				territoryUpdate.setNumSoldiers(t.getNumSoldiers()
						+ soldiers);

				final int numCannons[] = new int[3];
				numCannons[0] = t.getNumCannons()[0];
				numCannons[1] = t.getNumCannons()[1];
				numCannons[2] = t.getNumCannons()[2] + cannons;
				territoryUpdate.setNumCannons(numCannons);

				final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
				territoriesUpdate.add(territoryUpdate);

				adapter.updateGame(session, mGame, playerUpdates,
					territoriesUpdate, EventType.BuyArsenalEvent);

				mPlayerListModel.updatePlayer(playerUpdate);
				mMapListModel.updateTerritory(territoryUpdate);
			} else
				throw new NotEnoughMoneyException();
		} else
			throw new InvalidArgumentException();
	}

	public void moveUnits(int src, int dst, int soldiers, int[] cannons, int missiles, int ICMB, int antimissiles) throws Exception {
		this.checkInTurn();

		if (src < 0 || src > 41)
			throw new InvalidArgumentException();
		if (dst < 0 || dst > 41)
			throw new InvalidArgumentException();

		TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
			src);
		TerritoryDecorator dstTerritory = this.getMapListModel().getTerritoryAt(
			dst);

		if (srcTerritory.getPlayer() != null
				&& srcTerritory.getOwner().equals(session.getUser())
				&& dstTerritory.getPlayer() != null
				&& dstTerritory.getOwner().equals(session.getUser())
				&& srcTerritory.getAdjacentTerritories().contains(dstTerritory)
				&& soldiers <= srcTerritory.getNumSoldiers() && soldiers >= 0
				&& cannons[0] <= srcTerritory.getNumCannons()[0]
				&& cannons[0] >= 0
				&& cannons[1] <= srcTerritory.getNumCannons()[1]
				&& cannons[1] >= 0
				&& cannons[2] <= srcTerritory.getNumCannons()[2]
				&& cannons[2] >= 0
				&& missiles <= srcTerritory.getNumMissiles() && missiles >= 0
				&& ICMB <= srcTerritory.getNumICBMs() && ICMB >= 0
				&& antimissiles <= srcTerritory.getNumAntiMissiles()
				&& antimissiles >= 0) {

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
					- ICMB);
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
					+ ICMB);
			dstTerritory.setNumAntiMissiles(dstTerritory.getNumAntiMissiles()
					+ antimissiles);

			final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
			territoriesUpdate.add(srcTerritory);
			territoriesUpdate.add(dstTerritory);
			adapter.updateGame(session, mGame,
				new ArrayList<Player>(), territoriesUpdate,
				EventType.UnknownEvent);

			mMapListModel.updateTerritory(srcTerritory);
			mMapListModel.updateTerritory(dstTerritory);

		} else
			throw new InvalidArgumentException();
	}

	public void buyTerritory(int territory) throws Exception {
		this.checkInTurn();

		if (territory < 0 || territory > 41)
			throw new InvalidArgumentException();

		final ArrayList<TerritoryDecorator> AdjacentTerritories = mMapListModel.getTerritoryAt(
			territory).getAdjacentTerritories();

		TerritoryDecorator myTerritory = null;

		for (int i = 0; i < AdjacentTerritories.size() && myTerritory == null; i++) {
			if (AdjacentTerritories.get(i).getOwner().equals(
				mPlayerListModel.getSelfPlayer().getName()))
				myTerritory = AdjacentTerritories.get(i);
		}
		if (myTerritory == null)
			throw new InvalidArgumentException();

		if (mPlayerListModel.getSelfPlayer().getMoney() < mMapListModel.getTerritoryAt(
			territory).getPrice())
			throw new NotEnoughMoneyException();

		if (mMapListModel.getTerritoryAt(territory).getPlayer() == null) {

			final Player playerUpdate = new Player(
				mPlayerListModel.getSelfPlayer().getName(),
				mPlayerListModel.getSelfPlayer().getMoney()
						- mMapListModel.getTerritoryAt(territory).getPrice(),
				mPlayerListModel.getSelfPlayer().isOnline(),
				mPlayerListModel.getSelfPlayer().isHasTurn(),
				mPlayerListModel.getSelfPlayer().getSpies());

			final ArrayList<Player> playerUpdates = new ArrayList<Player>();
			playerUpdates.add(playerUpdate);

			final TerritoryDecorator territoryUpdate = (TerritoryDecorator) mMapListModel.getTerritoryAt(
				territory).clone();

			territoryUpdate.setPlayer(mPlayerListModel.getSelfPlayer());

			final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
			territoriesUpdate.add(territoryUpdate);

			adapter.updateGame(session, mGame, playerUpdates,
				territoriesUpdate, EventType.BuyTerritoryEvent);

			mPlayerListModel.updatePlayer(playerUpdate);
			mMapListModel.updateTerritory(territoryUpdate);
		} else
			throw new InvalidArgumentException();
	}

	public void deploySpy(int territory) throws Exception {
		this.checkInTurn();

		if (territory < 0 || territory >= mMapListModel.getRowCount())
			throw new InvalidArgumentException();

		final Player self = mPlayerListModel.getSelfPlayer();

		if (self.getMoney() < UnitInfo.getPricSpy())
			throw new NotEnoughMoneyException();

		final ArrayList<Spy> spyList = new ArrayList<Spy>();
		spyList.addAll(self.getSpies());
		spyList.add(new Spy(territory, 2));

		final Player p = new Player(
			self.getName(), self.getMoney() - UnitInfo.getPricSpy(),
			self.isOnline(), self.isHasTurn(), spyList);

		final ArrayList<Player> playerUpdate = new ArrayList<Player>();
		playerUpdate.add(p);
		adapter.updateGame(session, mGame, playerUpdate,
			new ArrayList<Territory>(), EventType.BuyArsenalEvent);

		mPlayerListModel.updatePlayer(p);
	}

	@Override
	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws InvalidTerritoryException {
		if (src != null && dst != null && arsenal != null) {
			final TerritoryDecorator territoryOrigin = new TerritoryDecorator(
				src, mMapListModel, mPlayerListModel);
			final TerritoryDecorator territoryDestination = new TerritoryDecorator(
				dst, mMapListModel, mPlayerListModel);
			final Attack att = new Attack(arsenal, territoryOrigin,
				territoryDestination);

			gameListener.territoryUnderAttack(territoryOrigin,
				territoryDestination, arsenal);

			mCurrentAttack = att;
		} else {
			throw new InvalidTerritoryException();
		}
	}

	@Override
	public void negotiationRequested(int money, int soldiers) {
		gameListener.negotiationRequested(money, soldiers);
	}

	@Override
	public void resolveAttack() {
		mCurrentAttack.resolve();

		final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
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

	public void resolveNegotiation(int money, int soldiers) {

		//Modificamos el usuario y territorio propio, creando un clon por si falla el update mantener los datos
		final Player playerUpdateOrigin = new Player(
			mPlayerListModel.getSelfPlayer().getName(),
			mPlayerListModel.getSelfPlayer().getMoney() + money,
			mPlayerListModel.getSelfPlayer().isOnline(),
			mPlayerListModel.getSelfPlayer().isHasTurn(),
			mPlayerListModel.getSelfPlayer().getSpies());

		final TerritoryDecorator territoryUpdateOrigin = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();
		territoryUpdateOrigin.setNumSoldiers(mCurrentAttack.getOrigin().getNumSoldiers()
				+ soldiers);

		//Modificamos el usuario y territorio contrario, creando un clon por si falla el update mantener los datos
		final Player playerUpdateDestination = new Player(
			mCurrentAttack.getDestination().getPlayer().getName(),
			mCurrentAttack.getDestination().getPlayer().getMoney() - money,
			mCurrentAttack.getDestination().getPlayer().isOnline(),
			mCurrentAttack.getDestination().getPlayer().isHasTurn(),
			mCurrentAttack.getDestination().getPlayer().getSpies());

		final TerritoryDecorator territoryUpdateDestination = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();
		territoryUpdateDestination.setNumSoldiers(mCurrentAttack.getDestination().getNumSoldiers()
				- soldiers);

		final ArrayList<Player> playerUpdates = new ArrayList<Player>();
		playerUpdates.add(playerUpdateOrigin);
		playerUpdates.add(playerUpdateDestination);

		final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
		territoriesUpdate.add(territoryUpdateOrigin);
		territoriesUpdate.add(territoryUpdateDestination);

		try {
			adapter.updateGame(session, mGame, playerUpdates,
				territoriesUpdate,
				EventType.NegotiationEvent);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mPlayerListModel.updatePlayer(playerUpdateOrigin);
		mPlayerListModel.updatePlayer(playerUpdateDestination);
		mMapListModel.updateTerritory(territoryUpdateOrigin);
		mMapListModel.updateTerritory(territoryUpdateDestination);

		//Elimino el ataque actual.
		mCurrentAttack = null;

	}

	@Override
	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) {
		final Player curPlayer = mPlayerListModel.getActivePlayer();

		if (event == EventType.AttackEvent) {
			if (territoryUpdate.size() == 2) {
				final TerritoryDecorator t1 = new TerritoryDecorator(
					territoryUpdate.get(0), mMapListModel, mPlayerListModel);
				final TerritoryDecorator t2 = new TerritoryDecorator(
					territoryUpdate.get(1), mMapListModel, mPlayerListModel);

				if (t1.getPlayer().equals(curPlayer))
					gameListener.attackEvent(t1, t2);
				else
					gameListener.attackEvent(t2, t1);
			}
		} else if (event == EventType.NegotiationEvent) {
			if (territoryUpdate.size() == 2) {
				final TerritoryDecorator t1 = new TerritoryDecorator(
					territoryUpdate.get(0), mMapListModel, mPlayerListModel);
				final TerritoryDecorator t2 = new TerritoryDecorator(
					territoryUpdate.get(1), mMapListModel, mPlayerListModel);

				if (t1.getPlayer().equals(curPlayer))
					gameListener.negotiationEvent(t1, t2);
				else
					gameListener.negotiationEvent(t2, t1);
			}
		} else if (event == EventType.BuyArsenalEvent) {
			if (territoryUpdate.size() == 1) {
				gameListener.buyUnitsEvent(new TerritoryDecorator(
					territoryUpdate.get(0), mMapListModel, mPlayerListModel));
			}
		} else if (event == EventType.BuyTerritoryEvent) {
			if (territoryUpdate.size() == 1) {
				gameListener.buyTerritoryEvent(new TerritoryDecorator(
					territoryUpdate.get(0), mMapListModel, mPlayerListModel));
			}
		}

		for (final Player p : playerUpdate) {
			mPlayerListModel.updatePlayer(p);
		}
		for (final Territory t : territoryUpdate) {
			mMapListModel.updateTerritory(new TerritoryDecorator(t,
				mMapListModel, mPlayerListModel));
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

	private class TurnUpdateThread extends Thread {
		@Override
		public void run() {
			final Player self = mPlayerListModel.getSelfPlayer();

			if (!self.equals(mPlayerListModel.getActivePlayer())) return;

			final ArrayList<Spy> spyList = self.getSpies();
			final ArrayList<Spy> newSpyList = new ArrayList<Spy>();
			for (final Spy spy : spyList) {
				spy.setUses(spy.getUses() - 1);
				if (spy.getUses() >= 0) newSpyList.add(spy);
			}
			self.setSpies(newSpyList);

			try {
				final ArrayList<Player> playerList = new ArrayList<Player>();
				playerList.add(self);
				adapter.updateGame(session, GameEngine.this.getGame(),
					playerList, new ArrayList<Territory>(),
					EventType.UnknownEvent);
				mPlayerListModel.updatePlayer(self);
			} catch (final Exception e) {
				self.setSpies(spyList);
			}
		}
	}

}
