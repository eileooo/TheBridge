package com.leo.thebridge.game;

import org.bukkit.World;
import org.bukkit.Location;

public class Arena {
	
	//private Location location;
	private String name;
	private World world;
	private Location locationOne;
	private Location locationTwo;
	
	public Arena(String name, 
			World world, 
			Location locationOne, 
			Location locationTwo) {
		//this.location = location;
		this.name = name;
		this.world = world;
		this.locationOne = locationOne;
		this.locationTwo = locationTwo;
		
	}
	
	/* public Location getLocation() {
		return location;
	} */ 

	public String getName() {
		return name;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Location getLocationOne() {
		return locationOne;
	}
	
	public Location getLocationTwo() {
		return locationTwo;
	}
	

	
}
