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
	
	private Cuboid redPortal;
	private Cuboid bluePortal; 
		
	public VirtualArena(String arenaName, File schematicFile) {
		this.name = arenaName;

		loadWorld(schematicFile);
		
		this.redPortal = new Cuboid(new Location(world, 32, 90, -2), new Location(world, 36, 90, 2));
		this.bluePortal = new Cuboid(new Location(world, -32, 90, 2), new Location(world, -36, 90, -2));
		Utils.log(bluePortal.blockList().size() + " blocks");
		
		spawnEndPortals();
		
	}
	
	private void spawnEndPortals() {
		getBluePortal().blockList().forEach(block -> {
			block.setType(Material.ENDER_PORTAL);
		});
		
		getRedPortal().blockList().forEach(block -> {
			block.setType(Material.ENDER_PORTAL);
		});
	}
	
	
	public Location getLocationOne() {
		Location location = new Location(world, 29, 102, 0);
		location.setPitch(3.30f);
		location.setYaw(-270.0f);
		return location;
	}
	
	public Location getLocationTwo() {
		Location location = new Location(world, -28, 102, 0);
		location.setPitch(4.49f);
		location.setYaw(-90.0f);
		return location;
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
	
	private void loadWorld(File schematicFile) {
		new LoadWorld(new WorldCreator(name).generator(new EmptyWorldGenerator()));
		World loadedWorld = Bukkit.getWorld(name);
		
		WorldEditPlugin worldEditPlugin = JavaPlugin.getPlugin(WorldEditPlugin.class);
		EditSession editSession = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loadedWorld), 9999999);
		
		try {	
			CuboidClipboard clipboard = CuboidClipboard.loadSchematic(schematicFile);
			clipboard.paste(editSession,  new Vector(0, 15 ,0), false);
		} catch (DataException | IOException | MaxChangedBlocksException e) {
			 e.printStackTrace();
			}			
		loadedWorld.setDifficulty(Difficulty.PEACEFUL);
		loadedWorld.setTime(1000);
		loadedWorld.setStorm(false);
			
		this.world = loadedWorld;
	}
	
	public void unload() {
		try {
			Bukkit.unloadWorld(world, false);	
		} catch (ClassCastException e) {
			
		}

	}

}
