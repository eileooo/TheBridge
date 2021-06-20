package com.leo.thebridge.game;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.leo.thebridge.utils.Utils;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.DataException;

import net.elicodes.reset.reset.EmptyWorldGenerator;
import net.elicodes.reset.reset.LoadWorld;

@SuppressWarnings("deprecation")
public class VirtualArena {

	private String name;	
	private World world;
	/*private Location locationOne;
	private Location locationTwo; */
		
	public VirtualArena(String name, File schematicFile) {
		this.name = name;
		
		loadWorld(schematicFile);
		
	}
	
	private void loadWorld(File schematicFile) {
		new LoadWorld(new WorldCreator(name).generator(new EmptyWorldGenerator()));
		World loadedWorld = Bukkit.getWorld(name);
		
		loadedWorld.setGameRuleValue("doMobSpawning", "false");
		
		WorldEditPlugin worldEditPlugin = JavaPlugin.getPlugin(WorldEditPlugin.class);
		EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loadedWorld), 9999999);
		
			try {	
				CuboidClipboard clipboard = CuboidClipboard.loadSchematic(schematicFile);
				clipboard.paste(editSession,  new Vector(0, 25 ,0), false);
			} catch (DataException | IOException | MaxChangedBlocksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loadedWorld.setDifficulty(Difficulty.PEACEFUL);
			loadedWorld.setTime(0);
			loadedWorld.setStorm(false);
			loadedWorld.setSpawnLocation(25, 160, -21);
			
			this.world = loadedWorld;
			world.getBlockAt(getLocationOne().add(2, 0, 0)).setType(Material.ENDER_PORTAL);
	}
	
	
	public Location getLocationOne() {
		return new Location(world, 25, 160, -21);
	}
	
	public Location getLocationTwo() {
		return new Location(world, 107, 160, -21);
	}
	
	public String getName() {
		return name;
	}
	
	public void unload() {
		
		try {
			Bukkit.unloadWorld(world, false);	
		} catch (ClassCastException e) {
			Utils.log("Deu erro aqui ó");
		}

	}

	
}
