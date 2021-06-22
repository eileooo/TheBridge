package com.leo.thebridge.game;

import java.io.File;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;


import com.leo.thebridge.utils.Cuboid;
import com.leo.thebridge.utils.Utils;

import net.elicodes.vw.developers.WorldAPI;

public class VirtualArena {

	private String name;	
	private World world;
	private String id;
	
	private Cuboid redPortal;
	private Cuboid bluePortal; 
		
	public VirtualArena(String arenaName, File schematicFile, String id) {
		this.name = arenaName;
		this.id = id;

		loadWorld(schematicFile);
		
		this.redPortal = new Cuboid(new Location(world, -13, 48, -14), new Location(world, -9, 48, -10));
		this.bluePortal = new Cuboid(new Location(world, -77, 48, -10), new Location(world, -81, 48, -14));
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
		Location location = new Location(world, -16, 60, -11);
		location.setPitch(3.30f);
		location.setYaw(-270.0f);
		return location;
	}
	
	public Location getLocationTwo() {
		Location location = new Location(world, -72, 60, -11);
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
		this.world = WorldAPI.createVirtualWorldWithSchematic(id, schematicFile);
	}
		
	
	public void unload() {
		WorldAPI.deleteVirtualWorld(world);

	}

}

