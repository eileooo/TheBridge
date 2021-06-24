package com.leo.thebridge.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.utils.Utils;

public class DeathListener implements Listener {

	private GameManager gameManager;

	public DeathListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntityType() != EntityType.PLAYER)
			return;
		Player player = (Player) event.getEntity();
		if (!gameManager.isPlayerPlaying(player))
			return;

		if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
			Utils.log("Void damage.");
			event.setCancelled(true);
			ActivePlayer activePlayer = gameManager.getActivePlayer(player);
			Game game = activePlayer.getGame();

			game.giveItems(activePlayer);
			player.teleport(activePlayer.getSpawnLocation());

			activePlayer.addDeath();

			activePlayer.getGame().broadcast(activePlayer.getTeamColor() + player.getName() + "§7 caiu no void.");
		} else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		if (gameManager.isPlayerPlaying(player)) {
			ActivePlayer activePlayer = gameManager.getActivePlayer(player);
			Game game = activePlayer.getGame();

			Bukkit.getScheduler().scheduleSyncDelayedTask(gameManager.getPlugin(), () -> {
				player.spigot().respawn();
				player.teleport(activePlayer.getSpawnLocation());
				game.giveItems(activePlayer);
			}, 2);

			String coloredPlayerName = activePlayer.getTeamColor() + player.getName();

			if (player.getKiller() != null) {
				if (player.getKiller() instanceof Player) {

					Player killer = (Player) player.getKiller();
					ActivePlayer activeKiller = gameManager.getActivePlayer(killer);

					activeKiller.addDeath();
					killer.playSound(killer.getLocation(), Sound.NOTE_PLING, 1, 1);

					String coloredKillerName = activeKiller.getTeamColor() + activeKiller.getName();

					game.broadcast(coloredKillerName + "§7 matou " + coloredPlayerName + "§7.");
					return;
				}
			}
			game.broadcast(coloredPlayerName + "§7 morreu.");
		}

	}

}
