package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;
import com.umbrella.worldconq.exceptions.PendingAttackException;
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
	private final Attack mCurrentAttack;

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

		for (final Iterator<Territory> iterator = map.iterator(); iterator.hasNext();)
			mMapList.add(new TerritoryDecorator(iterator.next(), mMapListModel,
				mPlayerListModel));

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
								&& cannons <= srcTerritory.getNumShots()
								&& missiles <= srcTerritory.getNumMissiles()
								&& icbm <= srcTerritory.getNumICBMs()) {

							final Arsenal arsenal = new Arsenal(soldiers,
								cannons, missiles, icbm);

							adapter.attackTerritory(session, mGame,
								srcTerritory.getDecoratedTerritory(),
								dstTerritory.getDecoratedTerritory(), arsenal);

							mCurrentAttack = new Attack(arsenal,
								(TerritoryDecorator) srcTerritory.clone(),
								(TerritoryDecorator) dstTerritory.clone());
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
		
		final TerritoryDecorator srcTerritory = this.getMapListModel().getTerritoryAt(
			src);
		final TerritoryDecorator dstTerritory =  this.getMapListModel().getTerritoryAt(
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
						
						srcTerritory.setNumSoldiers(srcTerritory.getNumSoldiers()-soldiers);
						//srcTerritory.setNumCannonss(srcTerritory.getNumCannons()-cannons);
						srcTerritory.setNumMissiles(srcTerritory.getNumMissiles()-missiles);
						srcTerritory.setNumICBMs(srcTerritory.getNumICBMs()-ICMB);
						srcTerritory.setNumAntiMissiles(srcTerritory.getNumAntiMissiles()-antimissiles);
						
						dstTerritory = (TerritoryDecorator) dstTerritory.clone();
						dstTerritory.setNumSoldiers(dstTerritory.getNumSoldiers()+soldiers);
						//dstTerritory.setNumCannonss(dstTerritory.getNumCannons()+cannons);
						dstTerritory.setNumMissiles(dstTerritory.getNumMissiles()+missiles);
						dstTerritory.setNumICBMs(dstTerritory.getNumICBMs()+ICMB);
						dstTerritory.setNumAntiMissiles(dstTerritory.getNumAntiMissiles()+antimissiles);
						
						final ArrayList<Territory> territoriesUpdate = new ArrayList<Territory>(); 
						territoriesUpdate.add(srcTerritory.getDecoratedTerritory());
						territoriesUpdate.add(dstTerritory.getDecoratedTerritory());
						updateGame(session, mGame, new ArrayList<Player>(), territoriesUpdate, EventType);
					
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
}

		
		
		
	}

	public void buyTerritory(int territory) {

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
	public void timeExpired(TimeType whatTime) {

	}
}
