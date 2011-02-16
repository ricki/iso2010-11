package com.umbrella.worldconq.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import com.umbrella.worldconq.comm.ServerAdapter;
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

	}

	public void acceptAttack() {

	}

	public void requestNegotiation(int money, int soldiers) throws Exception {

	}

	public void buyUnits(int erritory, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {

	}

	public void moveUnits(int src, int dst, int soldiers, int cannons, int missiles, int ICMB, int antimissiles) throws Exception {

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
