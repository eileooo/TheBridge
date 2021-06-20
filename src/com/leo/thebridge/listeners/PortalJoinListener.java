package com.leo.thebridge.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.Game;

public class PortalJoinListener implements Listener{
	
	private GameManager gameManager;
	
	public PortalJoinListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent event) {
		if (!gameManager.isPlayerPlaying(event.getPlayer())) return;
		
		//todo: check if game is active
		if (event.getCause() == TeleportCause.END_PORTAL) {
			event.setCancelled(true);
			
			ActivePlayer activePlayer = gameManager.getActivePlayerFromUUID(event.getPlayer().getUniqueId());
			Game game = gameManager.getGameFromPlayer(event.getPlayer());
			gameManager.scorePoint(game, activePlayer);
			
		}
	}

}
