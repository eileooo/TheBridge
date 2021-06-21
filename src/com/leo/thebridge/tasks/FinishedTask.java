package com.leo.thebridge.tasks;

import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;

import org.bukkit.scheduler.BukkitRunnable;

import com.leo.thebridge.game.Game;

public class FinishedTask extends BukkitRunnable{

	private Game game;
	private GameManager gameManager;
	
	public FinishedTask(GameManager gameManager, Game game) {
		this.gameManager = gameManager;
		this.game = game;
	}

	
	int timer = 30;
	
	@Override
	public void run() {
		
		if (timer == 0) {
			gameManager.setGameState(game, GameState.RESETING);
			this.cancel();
			
		}
		
		game.sendActionBar("§aServidor será resetado em " + timer + " segundo" + (timer == 1 ? "" : "s"));
		
		timer--;
	}
	
}
