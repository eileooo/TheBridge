package com.leo.thebridge.game;

import java.util.UUID;

import com.leo.thebridge.utils.Utils;

public class ActivePlayer {
	
	private UUID uuid;
	
	private Team team;
	private int deaths = 0;
	private int kills = 0;
	private int points = 0;
	
	public ActivePlayer(UUID uuid) {
		this.uuid = uuid;
		
		Utils.log("A new active player is being created, §7" + uuid.toString());
	}
	
	public UUID getUUID() {
		return uuid;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getKills() {
		return kills;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void addKill() {
		kills++;
	}
	
	public void addDeath() {
		deaths++;
	}
	
	public void addPoint() {
		points++;
	}
	
	
	

}
