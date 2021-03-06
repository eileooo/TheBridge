package com.leo.thebridge.game;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.leo.thebridge.utils.Utils;

public class ActivePlayer {
	
	private UUID uuid;
	private String name;
	private Player player;
	private Game game;
	
	private Team team;
	private ActivePlayer enemy = null;
	
	private ArrowPlayer arrowPlayer;
	
	private int deaths = 0;
	private int kills = 0;
	private int points = 0;
	
	private Location spawnLocation;
	private ChatColor color = ChatColor.GRAY;
	
	
	public ActivePlayer(Player player, Game game) {
		this.uuid = player.getUniqueId();
		this.player = player;
		this.name = player.getName();
		this.game = game;
		
		this.arrowPlayer = new ArrowPlayer(this);
		
		Utils.log("A new active player is being created, §7" + player.getUniqueId().toString());
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
	
	public void setEnemy(ActivePlayer enemy) {
		this.enemy = enemy;
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
	
	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
	
	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getSpawnLocation() {
		return spawnLocation;
	}
	
	public Game getGame() {
		return game;
	}
	
	public ActivePlayer getEnemy() {
		return enemy;
	}
	
	public ChatColor getTeamColor() {
		return color;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public ArrowPlayer getArrowPlayer() {
		return arrowPlayer;
	}
	
	

}
