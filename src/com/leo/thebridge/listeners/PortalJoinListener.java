package com.leo.thebridge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.Team;
import com.leo.thebridge.game.VirtualArena;
import com.leo.thebridge.utils.Utils;
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

	@EventHandler 
	public void onMove(PlayerMoveEvent event) {
		if (!gameManager.isPlayerPlaying(event.getPlayer())) return;
		ActivePlayer activePlayer = gameManager.getActivePlayerFromUUID(event.getPlayer().getUniqueId());
		Game game = gameManager.getGameFromPlayer(event.getPlayer());
		
		Player player = event.getPlayer();
		VirtualArena arena =game.getVirtualArena();
		Team team = activePlayer.getTeam();
		
		if (arena.getRedPortal().isIn(event.getPlayer())) {
			if (team == Team.RED) {
				game.teleportPlayerToRespectiveSpot(player, activePlayer);
				player.sendMessage(Utils.colorize("§cVocê não pode marcar pontos no seu portal!"));
				return;
			} else {
				gameManager.scorePoint(game, activePlayer);
				return;
			} 
		} 
		
		if (arena.getBluePortal().isIn(event.getPlayer())) {
			if (team == Team.BLUE) {
				game.teleportPlayerToRespectiveSpot(player, activePlayer);
				player.sendMessage(Utils.colorize("§cVocê não pode marcar pontos no seu portal!"));
				return;
			} else {
				gameManager.scorePoint(game, activePlayer);
				return;
			} 
		} 
		
	}
	
}
