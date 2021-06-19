package com.leo.thebridge.tasks;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.utils.Utils;

public class StartingGameTask extends BukkitRunnable{

	
	private Game game;
	private GameManager gameManager;
	public StartingGameTask(GameManager gameManager, Game game) {
		this.gameManager = gameManager;
		this.game = game;
	
	}

	int seconds = 10;
	
	@Override
	public void run() {
		if (seconds == 10 || seconds <= 3 && seconds > 0) {
			game.broadcast(Utils.colorize("§eO jogo iniciará em §7" + seconds + "§e segundo" + (seconds == 1 ? "" : "s")));
			game.playSound(Sound.NOTE_STICKS);
		}
		
		if (seconds == 0) {
			gameManager.setGameState(game, GameState.ACTIVE);
			this.cancel();
			
		}
			//Utils.log("Task canceled");
		
		seconds--;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
}
