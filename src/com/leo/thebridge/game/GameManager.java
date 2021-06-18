package com.leo.thebridge.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.leo.thebridge.Main;
import com.leo.thebridge.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;	


public class GameManager {

	private List<Game> games;
	// private List<Arena> arenas;
	private Main plugin;
	
	private List<String> arenas;
	
	public GameManager(Main plugin) {
		this.plugin = plugin;
		this.games = new ArrayList<Game>();
		
		this.arenas = Arrays.asList("fire");
	}
	
	public List<VirtualArena> loadVirtualArenas() {
		ArrayList<VirtualArena> worlds = new ArrayList<>();
		return worlds;			
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
			VirtualArena virtualArena = new VirtualArena("Fire", plugin.getSchematicFile());
			Game game = new Game(Utils.getRandomID(), virtualArena);
			
			game.addPlayer(player);
			setGameState(game, GameState.WAITING);
			this.addGame(game);
			
		} else {
			
			Game game = joiningGame.get();
			
			this.games.remove(game);
			// debug
			Bukkit.getServer().broadcastMessage("§eUm jogo foi encontrado, aguarde.");
			
			game.addPlayer(player);
			
			if (game.getPlayersCount() == 2) {
				setGameState(game, GameState.STARTING);
			}
			
			this.games.add(game);
		}
	}
	
	public void handleQuit(Player player) {
		Game game = getGameFromPlayer(player);
		game.removePlayer(player);
		
		if (game.getPlayersCount() == 1) {
			setGameState(game, GameState.WAITING);
			Bukkit.getServer().broadcastMessage("y");
		} else if (game.getPlayersCount() == 0) {
			setGameState(game, GameState.BLANK);
			Bukkit.getServer().broadcastMessage("x");
		}
	
		
		// todo: give win if there's another player
		// set state to finished
		// teleport resting player to the lobby
	}
	
	public void setGameState(Game game, GameState state) {
		
		Bukkit.getServer().broadcastMessage(Utils.colorize("§7" + game.getId() + " §ehave his state changed to §7" + state.toString()));
		
		switch(state) {
		case BLANK:
			break;
		case WAITING:
			break;
		case STARTING:
			game.broadcast(" ");
			game.broadcast("§aGame starting!");
			game.broadcast(" ");
			
			game.getPlayers().forEach(player -> {
				player.teleport(game.getVirtualArena().getLocationOne());
			});
			break;
		case ACTIVE:
			game.broadcast("§cComeçou!");
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
	
	public Game getGameFromPlayer(Player player) {
		Stream<Game> possibleGame = this.games.stream().filter(game -> {
			return game.getPlayers().contains(player);
		});
		
		return possibleGame.findFirst().get();
	}
	
	public void addGame(Game game) {
		this.games.add(game);
	}

	public List<String> getArenas() {
		return arenas;
	}
	
		
	public String getRandomArena() {
		return this.arenas.get(new Random().nextInt(this.arenas.size()));
	}
	
	public Main getPlugin() {
		return plugin;
	}
}
