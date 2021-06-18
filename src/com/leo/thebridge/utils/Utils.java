package com.leo.thebridge.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
}
