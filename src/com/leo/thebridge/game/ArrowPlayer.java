package com.leo.thebridge.game;

public class ArrowPlayer {

	private ActivePlayer player;
	private boolean waitingArrow = false;
	private int seconds = 3;
	
	public ArrowPlayer(ActivePlayer player) {
		this.player = player;
	}
	
	public void decreaseSeconds() {
		this.seconds--;
	}
	
	public void setWaitingArrow(boolean waiting) {
		if (waiting) {
			this.waitingArrow = true;
			this.seconds = 3;	
		} else {
			this.waitingArrow = false;
		}
	}
	
	public boolean isWaitingArrow() {
		return this.waitingArrow;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public ActivePlayer getPlayer() {
		return player;
	}
	
}
