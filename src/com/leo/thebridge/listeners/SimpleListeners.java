package com.leo.thebridge.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.VirtualArena;

public class SimpleListeners implements Listener {

	private GameManager gameManager;

	public SimpleListeners(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@EventHandler
	public void onBreakBlockEvent(BlockBreakEvent event) {
		if (gameManager.isPlayerPlaying(event.getPlayer())) {
			if (event.getBlock().getType() != Material.STAINED_CLAY) {
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void onPutBlockEvent(BlockPlaceEvent event) {
		if (gameManager.isPlayerPlaying(event.getPlayer())) {
			Player player = event.getPlayer();
			VirtualArena arena = gameManager.getGameFromPlayer(event.getPlayer()).getVirtualArena();
			if (arena.getRedProtectedArea().isIn(player) || arena.getBlueProtectedArea().isIn(player)) {
				event.setCancelled(true);
				return;
			}

			if (event.getBlock().getY() > 60) {
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (gameManager.isPlayerPlaying(player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		ActivePlayer activePlayer = gameManager.getActivePlayer(player);

		if (gameManager.isPlayerPlaying(player)) {
			activePlayer.getArrowPlayer().setWaitingArrow(true);
		}
	}

}
