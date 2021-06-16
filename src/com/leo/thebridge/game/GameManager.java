package com.leo.thebridge.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.leo.thebridge.Main;
import com.leo.thebridge.utils.Colorize;
import com.leo.thebridge.utils.Randomize;

import java.util.ArrayList;	

public class GameManager {

	private List<Game> games;
	private List<Arena> arenas;
	private Main plugin;
	
	public GameManager(Main plugin) {
		this.plugin = plugin;
		this.games = new ArrayList<Game>();
		this.arenas = new ArrayList<Arena>();
		
		this.arenas = plugin.getConfiguration().loadArenas();
	}
	
	public void handleJoin(Player player) {
		Stream<Game> availableGames = games.stream().filter(game -> {
			return game.getPlayersCount() <= 1; 
		});
		
		Optional<Game> joiningGame = availableGames.findAny();
		if (joiningGame.isEmpty()) {
			
			//debug
			Bukkit.getServer().broadcastMessage("§eNenhum jogo encontrado, aguarde.");

			// by default the game state is waiting
			Game game = new Game(Randomize.getRandomID(), this.getRandomArena());
			game.addPlayer(player);
			this.addGame(game);

		} else {
			
			// debug
			Bukkit.getServer().broadcastMessage("§eUm jogo foi encontrado, aguarde.");
			
			joiningGame.get().addPlayer(player);
			
			if (joiningGame.get().getPlayersCount() == 2) {
				setGameState(joiningGame.get(), GameState.STARTING);
			}
		}
	}
	
	public void setGameState(Game game, GameState state) {
		switch(state) {
		case WAITING:
			game.broadcast(Colorize.colorize("§cAguardando um jogador..."));
			break;
		case STARTING:
			
			break;
		case ACTIVE:
			break;
		case FINISHED: 
			break;
		}
	}
	
	public boolean isPlayerPlaying(Player player) {
		for (Game game : games) {
			if (game.getPlayers().contains(player)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public void addGame(Game game) {
		this.games.add(game);
	}

	public List<Arena> getArenas() {
		return arenas;
	}
	
	public Arena getRandomArena() {
		return this.arenas.get(new Random().nextInt(this.arenas.size()));
	}
	
	public Main getPlugin() {
		return plugin;
	}
}
