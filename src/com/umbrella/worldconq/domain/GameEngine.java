package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
import com.umbrella.worldconq.ui.GameEventListener;
import communications.IClient.TimeType;

import domain.Arsenal;
import domain.EventType;
import domain.Game;
import domain.Player;
import domain.Territory;

public class GameEngine implements ClientCallback {
	private MapModel mMapListModel;
	private PlayerListModel mPlayerListModel;
	private final Game mGame;
	private final Session session;
	private final ServerAdapter adapter;
	private final GameEventListener gameListener;
	private Attack mCurrentAttack;

	public GameEngine(Game game, Session session, ServerAdapter adapter, GameEventListener gameListener) {
		mCurrentAttack = null;
		this.gameListener = gameListener;
		mGame = game;
		this.session = session;
		this.adapter = adapter;

		mMapListModel = new MapModel(game.strToPlayer(this.session.getUser(),
			game));

		mPlayerListModel = new PlayerListModel(game.strToPlayer(
			this.session.getUser(),
			game), game.getPlayers());

		final ArrayList<TerritoryDecorator> mMapList = new ArrayList<TerritoryDecorator>();
		final ArrayList<Territory> map = game.getMap();

		for (final Territory t : map) {
			if (t == null) System.out.println("HOSTIA PUTA");
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
		if (mCurrentAttack != null)
			throw new PendingAttackException();
		else {
			final TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
				src);
			final TerritoryDecorator dstTerritory = this.getMapListModel().getTerritoryAt(
				dst);

			if (srcTerritory.getOwner().equals(session.getUser())) {
				if (dstTerritory.getPlayer() != null
						&& !dstTerritory.getOwner().equals(session.getUser())) {

					if (srcTerritory.getAdjacentTerritories().contains(
						dstTerritory)) {
						if (soldiers <= srcTerritory.getNumSoldiers()
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
					} else
						throw new InvalidArgumentException();
				} else
					throw new InvalidArgumentException();
			} else
				throw new InvalidArgumentException();
		}
	}

	public void acceptAttack() {

	}

	public void requestNegotiation(int money, int soldiers) throws Exception {

	}

	public void buyUnits(int Territory, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {

	}

	public void moveUnits(int src, int dst, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {

		TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
			src);
		TerritoryDecorator dstTerritory = this.getMapListModel().getTerritoryAt(
			dst);

		if (srcTerritory.getOwner().equals(session.getUser())) {
			if (dstTerritory.getPlayer() != null
					&& dstTerritory.getOwner().equals(session.getUser())) {

				if (srcTerritory.getAdjacentTerritories().contains(
					dstTerritory)) {
					if (soldiers <= srcTerritory.getNumSoldiers()
							&& cannons <= srcTerritory.getNumCannons().length
							&& missiles <= srcTerritory.getNumMissiles()
							&& ICMB <= srcTerritory.getNumICBMs()
							&& antimissiles <= srcTerritory.getNumAntiMissiles()) {

						srcTerritory = (TerritoryDecorator) srcTerritory.clone();

						srcTerritory.setNumSoldiers(srcTerritory.getNumSoldiers()
								- soldiers);
						//srcTerritory.setNumCannonss(srcTerritory.getNumCannons()-cannons);
						srcTerritory.setNumMissiles(srcTerritory.getNumMissiles()
								- missiles);
						srcTerritory.setNumICBMs(srcTerritory.getNumICBMs()
								- ICMB);
						srcTerritory.setNumAntiMissiles(srcTerritory.getNumAntiMissiles()
								- antimissiles);

						dstTerritory = (TerritoryDecorator) dstTerritory.clone();
						dstTerritory.setNumSoldiers(dstTerritory.getNumSoldiers()
								+ soldiers);
						//dstTerritory.setNumCannonss(dstTerritory.getNumCannons()+cannons);
						dstTerritory.setNumMissiles(dstTerritory.getNumMissiles()
								+ missiles);
						dstTerritory.setNumICBMs(dstTerritory.getNumICBMs()
								+ ICMB);
						dstTerritory.setNumAntiMissiles(dstTerritory.getNumAntiMissiles()
								+ antimissiles);

						final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
						territoriesUpdate.add(srcTerritory.getDecoratedTerritory());
						territoriesUpdate.add(dstTerritory.getDecoratedTerritory());
						adapter.updateGame(session, mGame,
							new ArrayList<Player>(), territoriesUpdate,
							EventType.UnknownEvent);

						mMapListModel.updateTerritory(srcTerritory);
						mMapListModel.updateTerritory(dstTerritory);

					} else
						throw new InvalidArgumentException();
				} else
					throw new InvalidArgumentException();
			} else
				throw new InvalidArgumentException();
		} else
			throw new InvalidArgumentException();
	}

	public void buyTerritory(int territory) {

	}

	@Override
	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) throws Exception {
		if (src != null && dst != null && arsenal != null) {
			final TerritoryDecorator territoryOrigin = new TerritoryDecorator(
				src, mMapListModel, mPlayerListModel);
			final TerritoryDecorator territoryDestination = new TerritoryDecorator(
				dst, mMapListModel, mPlayerListModel);
			final Attack att = new Attack(arsenal, territoryOrigin,
				territoryDestination);

			gameListener.territoryUnderAttack(src, dst);

			mCurrentAttack = att;
		} else {
			throw new InvalidArgumentException();
		}
	}

	@Override
	public void negotiationRequested(int money, int soldiers) throws Exception {
		gameListener.negotiationRequested(money, soldiers);
	}

	@Override
	public void resolveAttack() throws Exception {
		mCurrentAttack.resolve();

		//Modificamos el territorio propio, creando un clon por si falla el update mantener los datos
		final TerritoryDecorator territoryUpdateOrigin = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();

		//Modificamos el territorio contrario, creando un clon por si falla el update mantener los datos
		final TerritoryDecorator territoryUpdateDestination = (TerritoryDecorator) mCurrentAttack.getOrigin().clone();

		final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
		territoriesUpdate.add(territoryUpdateOrigin.getDecoratedTerritory());
		territoriesUpdate.add(territoryUpdateDestination.getDecoratedTerritory());

		adapter.updateGame(session, mGame, mGame.getPlayers(),
			territoriesUpdate,
			EventType.AttackEvent);

		mMapListModel.updateTerritory(territoryUpdateOrigin);
		mMapListModel.updateTerritory(territoryUpdateDestination);
		mCurrentAttack = null;

	}

	@Override
	public void resolveNegotiation(int money, int soldiers) throws Exception {

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
		territoriesUpdate.add(territoryUpdateOrigin.getDecoratedTerritory());
		territoriesUpdate.add(territoryUpdateDestination.getDecoratedTerritory());

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

	}

	@Override
	public void timeExpired(UUID game, TimeType whatTime) {
		// TODO Auto-generated method stub

	}
}
