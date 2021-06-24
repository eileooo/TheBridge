package com.leo.thebridge;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.leo.thebridge.commands.BasicCommands;
import com.leo.thebridge.game.Game;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.listeners.ChatListener;
import com.leo.thebridge.listeners.DeathListener;
import com.leo.thebridge.listeners.PortalJoinListener;
import com.leo.thebridge.listeners.QuitListeners;
import com.leo.thebridge.listeners.SimpleListeners;
import com.leo.thebridge.scoreboard.FastBoard;

public class Main extends JavaPlugin{
	
	private GameManager gameManager;
	
	private File schematicFile;
	
	public void onEnable() {
		
		this.schematicFile = new File(getDataFolder(), "boo.schematic");
		
		this.gameManager = new GameManager(this);
		
		BasicCommands basicCommands = new BasicCommands(gameManager);
		
		this.getCommand("entrar").setExecutor(basicCommands);
		this.getCommand("forcestart").setExecutor(basicCommands);
		this.getCommand("forcestop").setExecutor(basicCommands);
		
		Bukkit.getPluginManager().registerEvents(new SimpleListeners(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new QuitListeners(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new PortalJoinListener(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new DeathListener(gameManager), this);
		
	}
	
	public void onDisable() {
		for (Game game : gameManager.getGames()) {
			game.getVirtualArena().unload();
			for (FastBoard board : game.getBoards().values()) {
				board.delete();
			}
		}
	}
	
	public File getSchematicFile() {
		return schematicFile;
	}


}
