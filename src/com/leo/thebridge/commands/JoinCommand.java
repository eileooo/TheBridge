package com.leo.thebridge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.utils.Colorize;

public class JoinCommand implements CommandExecutor {
	
	private GameManager gameManager;
	
	public JoinCommand(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("entrar")) {
			if (gameManager.isPlayerPlaying(player)) {
				player.sendMessage(Colorize.colorize("§cVocê já está em uma partida."));
			} else gameManager.handleJoin(player); 
			
		} else if (command.getName().equals("forcestart")) {
			if (gameManager.isPlayerPlaying(player)) {
				Game game = gameManager.getGameFromPlayer(player);
				gameManager.setGameState(game,  GameState.STARTING);
			}
			
			
		}
		
		
		return false;
	}

}
