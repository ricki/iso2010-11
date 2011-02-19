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

			if (srcTerritory.getOwner().equals(session.getUser())
					&& dstTerritory.getPlayer() != null
					&& !dstTerritory.getOwner().equals(session.getUser())
					&& srcTerritory.getAdjacentTerritories().contains(
						dstTerritory)
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
		if (mCurrentAttack == null)
			throw new Exception();
		adapter.acceptAttack(session, mGame);
		mCurrentAttack = null;
	}

	public void requestNegotiation(int money, int soldiers) throws Exception {
		if (mCurrentAttack == null)
			throw new Exception();
		if (mPlayerListModel.getSelfPlayer().getMoney() >= money
				&& mCurrentAttack.getDestination().getNumSoldiers() >= soldiers) {
			adapter.requestNegotiation(session, mGame, money, soldiers);
			mCurrentAttack = null;
		} else
			throw new InvalidArgumentException();
	}

	public void buyUnits(int Territory, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {

	}

	public void moveUnits(int src, int dst, int soldiers, int[] cannons, int missiles, int ICMB, int antimissiles) throws Exception {

		TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
			src);
		TerritoryDecorator dstTerritory = this.getMapListModel().getTerritoryAt(
			dst);

		if (srcTerritory.getOwner().equals(session.getUser())
				&& dstTerritory.getPlayer() != null
				&& dstTerritory.getOwner().equals(session.getUser())
				&& srcTerritory.getAdjacentTerritories().contains(dstTerritory)
				&& soldiers <= srcTerritory.getNumSoldiers()
				&& cannons[0] <= srcTerritory.getNumCannons()[0]
				&& cannons[1] <= srcTerritory.getNumCannons()[1]
				&& cannons[2] <= srcTerritory.getNumCannons()[2]
				&& missiles <= srcTerritory.getNumMissiles()
				&& ICMB <= srcTerritory.getNumICBMs()
				&& antimissiles <= srcTerritory.getNumAntiMissiles()) {

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
			territoriesUpdate.add(srcTerritory.getDecoratedTerritory());
			territoriesUpdate.add(dstTerritory.getDecoratedTerritory());
			adapter.updateGame(session, mGame,
							new ArrayList<Player>(), territoriesUpdate,
							EventType.UnknownEvent);

			mMapListModel.updateTerritory(srcTerritory);
			mMapListModel.updateTerritory(dstTerritory);

		} else
			throw new InvalidArgumentException();
	}

	public void buyTerritory(int territory) throws Exception {

		final ArrayList<TerritoryDecorator> AdjacentTerritories = mMapListModel.getTerritoryAt(
			territory).getAdjacentTerritories();

		TerritoryDecorator myTerritory = null;

		for (int i = 0; i < AdjacentTerritories.size() && myTerritory != null; i++) {
			if (AdjacentTerritories.get(i).getOwner().equals(
				mPlayerListModel.getSelfPlayer().getName()))
					myTerritory = AdjacentTerritories.get(i);
		}
		if (myTerritory == null)
			throw new InvalidArgumentException();

		if (mPlayerListModel.getSelfPlayer().getMoney() >= mMapListModel.getTerritoryAt(
			territory).getPrice()
				&& mMapListModel.getTerritoryAt(territory).getPlayer() == null) {

			final Player playerUpdate = new Player(
				mPlayerListModel.getSelfPlayer().getName(),
				mPlayerListModel.getSelfPlayer().getMoney()
						- mMapListModel.getTerritoryAt(
							territory).getPrice(),
				mPlayerListModel.getSelfPlayer().isOnline(),
				mPlayerListModel.getSelfPlayer().isHasTurn(),
				mPlayerListModel.getSelfPlayer().getSpies());

			final ArrayList<Player> playerUpdates = new ArrayList<Player>();
			playerUpdates.add(playerUpdate);

			final TerritoryDecorator territoryUpdate = (TerritoryDecorator) mMapListModel.getTerritoryAt(
				territory).clone();

			final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>();
			territoriesUpdate.add(territoryUpdate.getDecoratedTerritory());

			adapter.updateGame(session, mGame, playerUpdates,
				territoriesUpdate, EventType.BuyTerritoryEvent);

			mPlayerListModel.updatePlayer(playerUpdate);
			mMapListModel.updateTerritory(territoryUpdate);
		} else
			throw new InvalidArgumentException();
	}

	@Override
	public void territoryUnderAttack(Territory src, Territory dst, Arsenal arsenal) {

	}

	@Override
	public void negotiationRequested(int money, int soldiers) {

	}

	@Override
	public void resolveAttack() {

	}

	@Override
	public void resolveNegotiation(int money, int sodiers) {

	}

	@Override
	public void updateClient(ArrayList<Player> playerUpdate, ArrayList<Territory> territoryUpdate, EventType event) {

	}

	@Override
	public void timeExpired(UUID game, TimeType whatTime) {
		// TODO Auto-generated method stub

	}
}
