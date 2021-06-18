package com.leo.thebridge.game;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import net.elicodes.reset.reset.EmptyWorldGenerator;
import net.elicodes.reset.reset.LoadWorld;

@SuppressWarnings("deprecation")
public class VirtualArena {

	private String name;	
	private World world;
	// private Location locationOne;
	// private Location locationTwo;
		
	public VirtualArena(String name, File schematicFile) {
		this.name = name;
		
		new LoadWorld(new WorldCreator(name).generator(new EmptyWorldGenerator()));
		World loadedWorld = Bukkit.getWorld(name);
		
		loadedWorld.setGameRuleValue("doMobSpawning", "false");
		
		WorldEditPlugin worldEditPlugin = JavaPlugin.getPlugin(WorldEditPlugin.class);
		EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loadedWorld), -1);
		
		try {
			
			CuboidClipboard clipboard = MCEditSchematicFormat.MCEDIT.load(schematicFile);
			
			clipboard.paste(editSession,  new Vector(0,100,0), false);
			
			this.world = loadedWorld;
			
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//this.locationOne = world.getSpawnLocation();
		
	}
	
	
	public Location getLocationOne() {
		return new Location(world, 25, 160, -21);
	}
	
	public String getName() {
		return name;
	}
	

	
}
