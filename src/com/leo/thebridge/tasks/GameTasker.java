package com.leo.thebridge.tasks;

import com.leo.thebridge.game.Game;
public class GameTasker {

	private Game game;
	
	private int startingTimer;
	private int gameDuration;
	
	
	public GameTasker(Game game) {
		this.game = game;
		this.startingTimer = 10;
	}
	
	public void decreaseStartingTimer() {
		this.startingTimer--;
	}
	
	/*public void run() {
		
		if (game.getPlayersCount() == 2) {
			
			if (game.getGameState() == GameState.WAITING) {
				if (startingTimer == 10 || startingTimer <= 3) {
					game.broadcast("§eO jogo iniciará em §7" + startingTimer + " segundo" + (startingTimer == 1 ? "s" : ""));
				}
				
				if (startingTimer == 0 ) {
					gameManager.setGameState(game, GameState.ACTIVE);
				}
				
				startingTimer--;	
			} else if (game.getGameState() == GameState.ACTIVE) {
				gameDuration++;
			}

		}

	} */
	
}
