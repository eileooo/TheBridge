package com.leo.thebridge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.leo.thebridge.game.GameManager;
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
	
}
