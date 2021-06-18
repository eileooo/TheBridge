package com.leo.thebridge.game;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;

import com.leo.thebridge.utils.Randomize;

import net.elicodes.vw.core.SchematicUtil;
import net.elicodes.vw.developers.WorldAPI;

public class VirtualArena {

	private String name;
	private File schematicFile;
	
	private World world;
	private Location locationOne;
	// private Location locationTwo;
	
	
	public VirtualArena(String name, File schematicFile) {
		this.name = name;
		this.schematicFile = schematicFile;
		
		this.world = WorldAPI.createVirtualWorld(Randomize.getRandomID());
		SchematicUtil.loadSchematic(schematicFile, world, 0, 100, 0);
		
		
		//this.locationOne = world.getSpawnLocation();
		
	}
	
	public Location getLocationOne() {
		return world.getSpawnLocation();
	}
	
	public String getName() {
		return name;
	}
	
	private File getSchematicFile() {
		return this.schematicFile;
	}
	
	
	
	
	
	
}
