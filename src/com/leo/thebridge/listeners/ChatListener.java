package com.leo.thebridge.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.leo.thebridge.game.ActivePlayer;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.game.Team;
import com.leo.thebridge.utils.Utils;

public class ChatListener implements Listener{
	
	private GameManager gameManager;
	
	public ChatListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (!gameManager.isPlayerPlaying(event.getPlayer())) return;
		ActivePlayer activePlayer = gameManager.getActivePlayer(event.getPlayer());
		
		StringBuilder builder = new StringBuilder();
		
		Team team = activePlayer.getTeam();
		if (team != null) {
			builder.append(Utils.getTag(team) + " §7%s: %s");
		} else {
			builder.append("§7%s: %s");
		}
		
		event.setFormat(builder.toString());
	
	}

}
