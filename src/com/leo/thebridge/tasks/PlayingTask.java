package com.leo.thebridge.tasks;

import com.leo.thebridge.utils.Utils;

import org.bukkit.scheduler.BukkitRunnable;

import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameState;

public class PlayingTask extends BukkitRunnable {

	private Game game;
	
	public PlayingTask(Game game) {
		this.game = game;
	}

	int seconds = 0;
	
	@Override
	public void run() {
		seconds++;
		game.sendActionBar(Utils.colorize("§eTimer: §7" + getFormattedTimer()));
		
		if (game.getGameState() != GameState.ACTIVE) this.cancel();
	}
	
	public String getFormattedTimer() {
		
		StringBuilder builder = new StringBuilder();
		
		if (seconds > 60) {
			int minutes = seconds / 60;
			int rest = seconds % 60; 
			
			builder.append(minutes + " minuto" + (minutes == 1 ? " " : "s "));
			builder.append("e " + rest + " segundo" + (rest == 1 ? "" : "s"));
		} else {
			return seconds + " segundo" + (seconds == 1 ? "" : "s");
		}
		
		return builder.toString();
	}
	
}
