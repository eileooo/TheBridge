package com.leo.thebridge.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.utils.Utils;

public class DeathListener implements Listener{
	
	private GameManager gameManager;
	
	public DeathListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntityType() != EntityType.PLAYER) return;
		Player player = (Player) event.getEntity();
		if (!gameManager.isPlayerPlaying(player)) return;
		
		if (event.getCause() == EntityDamageEvent.DamageCause.VOID) { 
			Utils.log("Void damage.");
			event.setCancelled(true);	
			ActivePlayer activePlayer = gameManager.getActivePlayerFromPlayer(player);
			
			//player.setHealth(0f);
			player.teleport(activePlayer.getSpawnLocation());
			activePlayer.addDeath();
			
			activePlayer.getGame().broadcast(Utils.getColor(activePlayer.getTeam()) + player.getName() + "§7 caiu no void.");
		} else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
	    final Player player = e.getEntity();
	    ActivePlayer activePlayer = gameManager.getActivePlayerFromPlayer(player);
	    Bukkit.getScheduler().scheduleSyncDelayedTask(gameManager.getPlugin(), () -> {
	    	player.spigot().respawn();
	    	player.teleport(activePlayer.getSpawnLocation());
	    }, 2);
	}

}
