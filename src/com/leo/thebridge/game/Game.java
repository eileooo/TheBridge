package com.leo.thebridge.game;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.leo.thebridge.utils.Colorize;

public class Game {

	private List<Player> players;
	private Arena arena;
	private String id;
	private GameState gameState;
	
	public Game(String id, Arena arena) {
		this.id = id;
		this.gameState = GameState.WAITING;
		this.players = new ArrayList<Player>();
		this.arena = arena;
	}
	
	public String getId() {
		return id;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);

		player.sendMessage(Colorize.colorize("§8Enviando para " + this.getId() + " [" + this.getArena().getName() + "]"));
		broadcast(Colorize.colorize("§7" + player.getName() + " §eentrou na partida. §7(" + getPlayersCount() + "/2)"));
		
		player.teleport(this.getArena().getLocationOne());
	}
	
	public int getPlayersCount() {
		return this.players.size();
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void broadcast(String message) {
		players.forEach((player) -> player.sendMessage(message));
	}
	
}
