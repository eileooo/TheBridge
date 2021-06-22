package com.leo.thebridge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
public class QuitListeners implements Listener {

	private GameManager gameManager;
	
	public QuitListeners(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		
		if (gameManager.isPlayerPlaying(player)) {
			gameManager.handleQuit(player);	
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (gameManager.isPlayerPlaying(player)) {
			Game game = gameManager.getGameFromPlayer(player);
			if (game.getGameState() == GameState.ACTIVE || game.getGameState() == GameState.WAITING) {
				gameManager.handleQuit(player);	
			}
		}
	}
	
	
}
