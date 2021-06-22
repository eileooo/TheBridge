package com.leo.thebridge;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.leo.thebridge.commands.BasicCommands;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.listeners.ChatListener;
import com.leo.thebridge.listeners.DeathListener;
import com.leo.thebridge.listeners.PortalJoinListener;
import com.leo.thebridge.listeners.QuitListeners;
import com.leo.thebridge.listeners.SimpleListeners;
import com.leo.thebridge.utils.Utils;

public class Main extends JavaPlugin{
	
	private GameManager gameManager;
	
	private File configFile;
	private YamlConfiguration configYaml;
	
	private File schematicFile;
	
	public void onEnable() {
		
		this.schematicFile = new File(getDataFolder(), "boo.schematic");
		
		loadConfiguration();
		
		this.gameManager = new GameManager(this);
		
		BasicCommands basicCommands = new BasicCommands(gameManager);
		
		this.getCommand("entrar").setExecutor(basicCommands);
		this.getCommand("forcestart").setExecutor(basicCommands);
		this.getCommand("arena").setExecutor(basicCommands);
		this.getCommand("points").setExecutor(basicCommands);
		this.getCommand("state").setExecutor(basicCommands);
		this.getCommand("location").setExecutor(basicCommands);
		
		Bukkit.getPluginManager().registerEvents(new SimpleListeners(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new QuitListeners(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new PortalJoinListener(gameManager), this);
		Bukkit.getPluginManager().registerEvents(new DeathListener(gameManager), this);
		
	}
	
	private void loadConfiguration() {
		File file = new File(this.getDataFolder(), "config.yml");
		
		if (!file.exists( )) {
			file.getParentFile().mkdirs();
			saveResource("config.yml", false);
		}
		
		this.configFile = file;
		
		this.configYaml = new YamlConfiguration();
		
		try {
			this.configYaml.load(file);
			Utils.log("Config carregada");

			
		} catch (IOException | InvalidConfigurationException ex) {
			ex.printStackTrace();
			Utils.log("Erro ao carregar a config");

		}
		
	}

	
	public File getConfigFile() {
		return configFile;
	}
	
	public YamlConfiguration getConfigYaml() {
		return configYaml;
	}
	
	public File getSchematicFile() {
		return schematicFile;
	}
	
	public void saveConfiguration() {
		try {
			this.configYaml.save(this.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
