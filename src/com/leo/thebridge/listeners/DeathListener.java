package com.leo.thebridge.listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

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
		if (event.getCause() == EntityDamageEvent.DamageCause.VOID) { 
			Utils.log("Void damage.");
			event.setCancelled(true);
			 
			Player player = (Player) event.getEntity();
			
			player.teleport(new Location(player.getWorld(), 25, 160, -21));
			player.setHealth(20.0f);
			
			
		} else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
			
	}

}
