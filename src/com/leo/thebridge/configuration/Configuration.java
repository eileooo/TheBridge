package com.leo.thebridge.configuration;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.List;

import com.leo.thebridge.Main;
import com.leo.thebridge.game.Arena;
import com.leo.thebridge.utils.Colorize;

public class Configuration {

	private YamlConfiguration yaml;
	private Main plugin;
		
	public Configuration(Main main) {
		this.plugin = main;
		this.yaml = plugin.getConfigYaml();
	}
	
	public List<Arena> loadArenas() {
		ArrayList<Arena> arenas = new ArrayList<>();
		
		if (!yaml.isConfigurationSection("arenas")) {
			Bukkit.getConsoleSender().sendMessage(Colorize.colorize("§cNenhuma arena encontrada."));
			yaml.createSection("arenas");
			
			this.plugin.saveConfiguration();
			
		} else {
			for (String arena : yaml.getConfigurationSection("arenas").getKeys(false)) {
				
				Bukkit.getConsoleSender().sendMessage(Colorize.colorize("§aArena §c" + arena + "§a encontrada!"));
				
				String worldName = yaml.getString("arenas." + arena + ".worldname");
				World world = Bukkit.createWorld(new WorldCreator(worldName));
				
				int locOneX = yaml.getInt("arenas." + arena + ".locationOne.x");
				int locOneY = yaml.getInt("arenas." + arena + ".locationOne.y");
				int locOneZ = yaml.getInt("arenas." + arena + ".locationOne.z");
				
				int locTwoX = yaml.getInt("arenas." + arena + ".locationTwo.x");
				int locTwoY = yaml.getInt("arenas." + arena + ".locationTwo.y");
				int locTwoZ = yaml.getInt("arenas." + arena + ".locationTwo.Z");
				
				Location locationTwo = new Location(world, locTwoX, locTwoY, locTwoZ);
				Location locationOne = new Location(world, locOneX, locOneY, locOneZ);
				
				Arena loadedArena = new Arena(arena, world, locationOne, locationTwo);
				
				arenas.add(loadedArena);
				
				Bukkit.getConsoleSender().sendMessage(Colorize.colorize("§eArena carregada com sucesso!"));
				
			}
			
		}

		return arenas;

	}
	
	
	
		
}
