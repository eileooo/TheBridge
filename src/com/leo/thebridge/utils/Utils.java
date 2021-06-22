package com.leo.thebridge.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.leo.thebridge.game.Team;

public class Utils {

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('§', message);
	}
	
	public static String getRandomID() {
		Random random = new Random();
		int number = random.nextInt(999) + 100;
		return "b" + number;
	}
	
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(colorize("§e[TheBridge] " + message));
	}
	
	public static String getTag(Team team) {
		switch(team) {
		case BLUE:
			return getColor(team) + "[Azul]";
		case RED:
			return getColor(team) + "[Vermelho]";
		default:
			return "";
		}
	}
	
	public static ChatColor getColor(Team team) {
		switch (team) {
		case BLUE:
			return ChatColor.BLUE;
		case RED:
			return ChatColor.RED;
		default:
			return ChatColor.WHITE;
		}
	}
}
