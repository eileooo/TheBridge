package com.leo.thebridge.tasks;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.utils.Colorize;

public class StartingGameTask extends BukkitRunnable{

	int timer = 11;
	private Game game;
	private GameManager gameManager;
	
	public StartingGameTask(GameManager gameManager, Game game) {
		this.gameManager = gameManager;
		this.game = game;
		
	}
	
	@Override
	public void run() {
		
		if (timer == 10) {
			game.broadcast(Colorize.colorize("§eO jogo iniciará em §7" + timer + " §esegundos."));
			game.playSound(Sound.NOTE_PLING);
		}
		
		timer--;
		
		if (timer <= 3 && timer > 0) {
			game.broadcast(Colorize.colorize("§eO jogo iniciará em §7" + timer + " §esegundos."));
			game.playSound(Sound.NOTE_PLING);	
		}
		
		if (timer <= 0) {
			this.cancel();
			this.gameManager.setGameState(game, GameState.ACTIVE);
		}
	}
	
	

}
