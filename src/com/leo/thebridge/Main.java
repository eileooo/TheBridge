package com.leo.thebridge;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.leo.thebridge.commands.BasicCommands;
import com.leo.thebridge.configuration.Configuration;
import com.leo.thebridge.game.GameManager;
import com.leo.thebridge.listeners.SimpleListeners;

public class Main extends JavaPlugin{
	
	private GameManager gameManager;
	private Configuration configuration;
	
	private File configFile;
	private YamlConfiguration configYaml;
	
	public void onEnable() {
		
		loadConfiguration();
		this.configuration = new Configuration(this);
		this.gameManager = new GameManager(this);
		
		this.getCommand("entrar").setExecutor(new BasicCommands(gameManager));
		this.getCommand("forcestart").setExecutor(new BasicCommands(gameManager));
		Bukkit.getPluginManager().registerEvents(new SimpleListeners(gameManager), this);
		
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
			
		} catch (IOException | InvalidConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	
	public File getConfigFile() {
		return configFile;
	}
	
	public YamlConfiguration getConfigYaml() {
		return configYaml;
	}
	
	public void saveConfiguration() {
		try {
			this.configYaml.save(this.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
