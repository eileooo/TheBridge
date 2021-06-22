package com.leo.thebridge.tasks;

import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.GameState;
import com.leo.thebridge.scoreboard.FastBoard;
import com.leo.thebridge.utils.Utils;

import java.util.ArrayList;

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
		
for (FastBoard board : game.getBoards().values()) {
			
			ArrayList<String> lines = new ArrayList<>();
			lines.add("");
			lines.add(Utils.colorize("§eResetando mapa"));
			lines.add(Utils.colorize("§eem §7" + timer + "§e segundo" + (timer == 1 ? "" : "s")));
			lines.add("");
			
			board.updateLines(lines);
			
		}
		
		if (timer == 0) {
			gameManager.setGameState(game, GameState.RESETING);
			this.cancel();
			
		}		
		
		timer--;
	}
	
}
