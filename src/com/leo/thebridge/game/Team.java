package com.leo.thebridge.game;

import org.bukkit.ChatColor;

public enum Team {

	BLUE, RED;
	
	public ChatColor getColor(Team team) {
		switch (team) {
		case BLUE:
			return ChatColor.BLUE;
		case RED:
			return ChatColor.RED;
		default:
			return ChatColor.WHITE;
		}
	}
	
	public String getTag(Team team) {
		switch(team) {
		case BLUE:
			return getColor(team) + "[Azul]";
		case RED:
			return getColor(team) + "[Vermelho]";
		default:
			return "";
		}
	}
	
}
