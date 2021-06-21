package com.leo.thebridge.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.leo.thebridge.utils.Cuboid;
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
	
	private Cuboid redPortal;
	private Cuboid bluePortal; 
		
	public VirtualArena(String name, File schematicFile) {
		this.name = name;
		
		loadWorld(schematicFile);
		
		this.redPortal = new Cuboid(new Location(world, 20, 77, -20), new Location(world, 22, 77, -18));
		this.bluePortal = new Cuboid(new Location(world, 110, 77, -18), new Location(world, 108, 77, -20));
		Utils.log(bluePortal.blockList().size() + " blocks");
		
		spawnEndPortals();
		
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
			loadedWorld.setSpawnLocation(25, 100, -21);
			
			this.world = loadedWorld;
			world.getBlockAt(getLocationOne().add(2, 0, 0)).setType(Material.ENDER_PORTAL);
			
			world.getBlockAt(new Location(loadedWorld, 23, 77, -23)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 22, 77, -23)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 21, 77, -23)).setType(Material.ENDER_PORTAL);
			
			world.getBlockAt(new Location(loadedWorld, 23, 77, -22)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 22, 77, -22)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 21, 77, -22)).setType(Material.ENDER_PORTAL);
			
			world.getBlockAt(new Location(loadedWorld, 23, 77, -21)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 22, 77, -21)).setType(Material.ENDER_PORTAL);
			world.getBlockAt(new Location(loadedWorld, 21, 77, -21)).setType(Material.ENDER_PORTAL);
			
	}
	
	private void spawnEndPortals() {
		getBluePortal().blockList().forEach(block -> {
			block.setType(Material.ENDER_PORTAL);
		});
	}
	
	
	public Location getLocationOne() {
		return new Location(world, 25, 100, -21);
	}
	
	public Location getLocationTwo() {
		return new Location(world, 107, 100, -21);
	}
	
	public String getName() {
		return name;
	}
	
	public Cuboid getRedPortal() {
		return redPortal;
	}
	
	public Cuboid getBluePortal() {
		return bluePortal;
	}
	
	public void unload() {
		
		try {
			Bukkit.unloadWorld(world, false);	
		} catch (ClassCastException e) {
			Utils.log("Deu erro aqui ó");
		}

	}

	
}
