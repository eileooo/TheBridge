package com.leo.thebridge.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.leo.thebridge.game.GameManager;

public class SimpleListeners implements Listener{
	
	private GameManager gameManager;
	
	public SimpleListeners(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onBreakBlockEvent(BlockBreakEvent event) {
		if (gameManager.isPlayerPlaying(event.getPlayer())) {
			if (event.getBlock().getType() != Material.STAINED_CLAY ) {
				event.setCancelled(true);	
			}
			
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		if (gameManager.isPlayerPlaying(player)) {
			event.setCancelled(true);
		}
	}


}
