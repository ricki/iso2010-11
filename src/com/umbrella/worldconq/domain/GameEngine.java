package com.umbrella.worldconq.domain;

import java.util.ArrayList;
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

	public GameEngine(Game game, Session session, ServerAdapter adapter) {
		mGame = game;
		this.session = session;
		this.adapter = adapter;

		this.setMapListModel(new MapModel(game.strToPlayer(
			this.session.getUser(),
			game)));
		//this.setMapListModel(new MapModel(getOwner())); algo asi (funcion de Laura)
		//---------------------------------------------------------------
		this.setPlayerListModel(new PlayerListModel());

		mMapListModel.setData(mGame.getMap());
		mPlayerListModel.setData(mGame.getPlayers());
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
