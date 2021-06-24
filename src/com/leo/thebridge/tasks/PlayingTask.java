package com.leo.thebridge.tasks;

import com.leo.thebridge.utils.Utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.ArrowPlayer;
import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.scoreboard.FastBoard;

public class PlayingTask extends BukkitRunnable {

	private Game game;

	public PlayingTask(Game game) {
		this.game = game;
	}

	int seconds = 900;

	@Override
	public void run() {
		seconds--;

		if (game.isWaitingNextRound()) {
			if (game.getNextRoundTimer() == 0) {
				game.sendTitle("§aGO!", "");
				game.setWaitingNextRound(false);
			} else {
				game.sendTitle("§c" + game.getNextRoundTimer(), "");
				game.decreaseNextRoundTimer();
			}
		}

		StringBuilder builder = new StringBuilder();
		builder.append(Utils.colorize("§eTimer: §7" + getFormattedTimer()));

		for (Player player : game.getPlayers()) {

			ActivePlayer activePlayer = game.getActivePlayerFromPlayer(player);
			if (activePlayer.getArrowPlayer().isWaitingArrow()) {
				ArrowPlayer arrowPlayer = activePlayer.getArrowPlayer();
				int arrowSeconds = arrowPlayer.getSeconds();
				player.setLevel(seconds);
				player.setExp(seconds / 10);

				if (arrowSeconds == 0) {
					player.getInventory().setItem(8, new ItemStack(Material.ARROW));
				} else {
					arrowPlayer.decreaseSeconds();
				}
			}

		}

		for (FastBoard board : game.getBoards().values()) {

			ActivePlayer player = game.getActivePlayerFromPlayer(board.getPlayer());
			String points = "§7* Você§7: §a(" + player.getPoints() + "§7/§a5)";

			ArrayList<String> lines = new ArrayList<>();
			lines.add("");
			lines.add("§fTempo restante: §a" + getFormattedTimer());
			lines.add(Utils.colorize("§fTime: " + Utils.getTag(player.getTeam())));
			lines.add("");
			lines.add("§fPontuação:");
			lines.add(Utils.colorize(points));

			ActivePlayer enemy = player.getEnemy();

			if (enemy != null) {
				String enemyPoints = "§7* " + enemy.getTeamColor() + enemy.getName() + "§7: §a(" + enemy.getPoints()
						+ "§7/§a5)";
				lines.add(Utils.colorize(enemyPoints));
			}

			lines.add("");
			lines.add(Utils.colorize("§fAbates: §7" + player.getKills()));
			lines.add(Utils.colorize("§fMortes: §7" + player.getDeaths()));
			lines.add("");

			board.updateLines(lines);

		}

		if (game.getGameState() != GameState.ACTIVE || seconds <= 0) {
			this.cancel();
			Utils.log("Task cancelled");
		}
	}

	public String getFormattedTimer() {

		StringBuilder builder = new StringBuilder();

		if (seconds > 60) {
			int minutes = seconds / 60;
			int rest = seconds % 60;

			builder.append(minutes + ":");
			if (rest < 10) {
				builder.append("0" + rest);
			} else {
				builder.append(rest);
			}

		} else {
			if (seconds < 10) {
				return "0" + seconds;
			} else {
				return seconds + "";
			}

		}

		return builder.toString();
	}

}
