package com.leo.thebridge.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import com.leo.thebridge.Main;
import com.leo.thebridge.tasks.PlayingTask;
import com.leo.thebridge.tasks.StartingGameTask;
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
			Utils.log("§eNenhum jogo encontrado, criando");

			// by default the game state is waiting
			VirtualArena virtualArena = new VirtualArena("Fire", plugin.getSchematicFile());
			Game game = new Game(Utils.getRandomID(), virtualArena);
			
			game.addPlayer(player);
			setGameState(game, GameState.WAITING);
			this.addGame(game);
			
			player.sendMessage(Utils.colorize("§8Enviando para " + game.getId() + " [" + game.getVirtualArena().getName() + "]"));
			
			
		} else {
			
			Game game = joiningGame.get();
			
			this.games.remove(game);
			// debug
			Utils.log("§eUm jogo foi encontrado, enviando player §7[" + player.getName() + "}");
			
			game.addPlayer(player);
			player.sendMessage(Utils.colorize("§8§oEnviando para " + game.getId() + " [" + game.getVirtualArena().getName() + "]"));
			
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
		} else if (game.getPlayersCount() == 0) {
			setGameState(game, GameState.BLANK);
		}
	
		// todo: give win if there's another player
		// set state to finished
		// teleport resting player to the lobby
	}
	
	public void setGameState(Game game, GameState state) {
		
		Utils.log("§7" + game.getId() + " §ehave his state changed to §7" + state.toString());
		
		switch(state) {
		case BLANK:
			break;
		case WAITING:
			break;
		case STARTING:
			StartingGameTask startingTask = new StartingGameTask(this, game);
			startingTask.runTaskTimer(plugin, 0, 20);
			break;
		case ACTIVE:
			game.broadcast("");
			game.broadcast("§e§lThe Bridge");
			game.broadcast("§7* Entre 5 vezes no portal do seu adversário ");
			game.broadcast("§7para vencer o jogo.");
			game.broadcast("");
			
			game.giveTeams();
			game.teleportPlayersToSpot();
			
			PlayingTask gamingTask = new PlayingTask(game);
			gamingTask.runTaskTimer(plugin, 0, 20);
			
			
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
	
	public ActivePlayer getActivePlayerFromUUID(UUID uuid) {
		for (Game game : games) {
			for (ActivePlayer activePlayer : game.getActivePlayers()) {
				if (activePlayer.getUUID() == uuid) {
					return activePlayer;
				}
			}
		}
		
		return null;
	}
	
}
