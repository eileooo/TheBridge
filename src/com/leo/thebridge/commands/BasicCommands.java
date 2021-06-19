package com.leo.thebridge.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.utils.Utils;

public class BasicCommands implements CommandExecutor {
	
	private GameManager gameManager;
	
	public BasicCommands(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("entrar")) {
			if (gameManager.isPlayerPlaying(player)) {
				player.sendMessage(Utils.colorize("§cVocê já está em uma partida."));
				return true;
			} else {
				gameManager.handleJoin(player);
				return true;
			}
			
		// temporary	
		} else if (command.getName().equals("forcestart")) {
			if (gameManager.isPlayerPlaying(player)) {
				Game game = gameManager.getGameFromPlayer(player);
				gameManager.setGameState(game, GameState.STARTING);
				return true;
			} else {
				player.sendMessage(Utils.colorize("§cVocê não está jogando!"));
				return true;
			}

		} else if (command.getName().equals("arena")) {
			World arena = Bukkit.getWorld("arena");
			
			player.teleport(new Location(arena, 1567, 73, 1261));
			return true;
		} else if (command.getName().equals("points")) {
			ActivePlayer activePlayer = gameManager.getActivePlayerFromUUID(player.getUniqueId());
			player.sendMessage(Utils.colorize("§eVocê tem §7" + activePlayer.getPoints() + "§e pontos"));
			return true;
		}
		
		
		return false;
	}

}
