package com.leo.thebridge.game;

import java.io.File;

import org.bukkit.Difficulty;
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

	private Cuboid redCage;
	private Cuboid blueCage;

	private Cuboid redProtectedArea;
	private Cuboid blueProtectedArea;

	public VirtualArena(String arenaName, File schematicFile, String id) {
		this.name = arenaName;
		this.id = id;

		loadWorld(schematicFile);

		this.redCage = new Cuboid(new Location(world, -12, 62, -9), new Location(world, -16, 66, -15));
		this.blueCage = new Cuboid(new Location(world, -74, 62, -9), new Location(world, -78, 66, -15));

		this.redPortal = new Cuboid(new Location(world, -13, 48, -14), new Location(world, -9, 48, -10));
		this.bluePortal = new Cuboid(new Location(world, -77, 48, -10), new Location(world, -81, 48, -14));

		this.redProtectedArea = new Cuboid(new Location(world, -24, 48, -1), new Location(world, -1, 68, -27));
		this.blueProtectedArea = new Cuboid(new Location(world, -66, 49, -24), new Location(world, -93, 68, 2));

		Utils.log(bluePortal.blockList().size() + " blocks");

		spawnEndPortals();

	}

	private void spawnEndPortals() {
		getRedPortal().fill(Material.ENDER_PORTAL);
		getBluePortal().fill(Material.ENDER_PORTAL);
	}

	public Location getLocationOne() {
		Location location = new Location(world, -14, 64, -11);
		location.setPitch(3.30f);
		location.setYaw(-270.0f);
		return location;
	}

	public Location getLocationTwo() {
		Location location = new Location(world, -74, 64, -11);

		location.setPitch(4.49f);
		location.setYaw(-90.0f);
		return location;
	}

	public String getName() {
		return name;
	}

	public Cuboid getRedCage() {
		return redCage;
	}

	public Cuboid getRedPortal() {
		return redPortal;
	}

	public Cuboid getBluePortal() {
		return bluePortal;
	}

	public Cuboid getRedProtectedArea() {
		return redProtectedArea;
	}

	public Cuboid getBlueProtectedArea() {
		return blueProtectedArea;
	}

	private void loadWorld(File schematicFile) {
		this.world = WorldAPI.createVirtualWorldWithSchematic(id, schematicFile);
		this.world.setDifficulty(Difficulty.PEACEFUL);
	}

	public void unload() {
		WorldAPI.deleteVirtualWorld(world);

	}

	public void fillCage(Cuboid cage, Material block, Material frame) {

		int maxX = cage.getxMax();
		int minX = cage.getxMin();

		int maxY = cage.getyMax();
		int minY = cage.getyMin();

		int maxZ = cage.getzMax();
		int minZ = cage.getzMin();

		cage.fillFloor(block);
		cage.fillRoof(block);

		for (int y = minY; y <= maxY; y++) {

			for (int x = minX; x <= maxX; x++) {
				if (y != minY && y != maxY) {
					if (x != maxX && x != minX) {
						world.getBlockAt(x, y, minZ).setType(block);
						world.getBlockAt(x, y, maxZ).setType(block);
					}
				} else {
					world.getBlockAt(x, y, minZ).setType(frame);
					world.getBlockAt(x, y, maxZ).setType(frame);
				}
			}

			for (int z = minZ; z <= maxZ; z++) {
				if (y != minY && y != maxY) {
					if (z != minZ && z != maxZ) {
						world.getBlockAt(maxX, y, z).setType(block);
						world.getBlockAt(minX, y, z).setType(block);
					}
				} else {
					world.getBlockAt(maxX, y, z).setType(frame);
					world.getBlockAt(minX, y, z).setType(frame);
				}
			}
		}

	}

	public void fillCages() {
		fillCage(redCage, Material.BARRIER, Material.QUARTZ_BLOCK);
		fillCage(blueCage, Material.BARRIER, Material.QUARTZ_BLOCK);
	}

	public void removeCages() {
		fillCage(redCage, Material.AIR, Material.AIR);
		fillCage(blueCage, Material.AIR, Material.AIR);
	}

}
