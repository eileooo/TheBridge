package com.leo.thebridge.game;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.leo.thebridge.utils.Colorize;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Game {

	private List<Player> players;
	private Arena arena;
	private String id;
	private GameState gameState;
	
	public Game(String id, Arena arena) {
		this.id = id;
		this.gameState = setBlank();
		this.players = new ArrayList<Player>();
		this.arena = arena;
		
		Bukkit.getServer().broadcastMessage("A new game is being crated §7" + id);
	}
	
	public GameState setBlank() {
		Bukkit.getServer().broadcastMessage("DAMN IT");
		
		return GameState.BLANK;

	}
	
	public String getId() {
		return id;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public void addPlayer(Player player) {
		player.teleport(this.getArena().getLocationOne());
		this.players.add(player);

		player.sendMessage(Colorize.colorize("§8Enviando para " + this.getId() + " [" + this.getArena().getName() + "]"));
		broadcast(Colorize.colorize("§7" + player.getName() + " §eentrou na partida. §7(" + getPlayersCount() + "/2)"));
		
	}
	
	public void removePlayer(Player player) {
		broadcast(Colorize.colorize("§7" + player.getName() + " §cabandonou a partida."));
		this.players.remove(player);

	}
	
	public int getPlayersCount() {
		return this.players.size();
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public void broadcast(String message) {
		players.forEach((player) -> player.sendMessage(message));
	}
	
	public void sendActionBar(String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        players.forEach(player -> {
        	((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
	}
	
	@SuppressWarnings("deprecation")
	public void sendTitle(String title, String subTitle) {
		players.forEach(player -> player.sendTitle(title, subTitle));
	}
	
	public void playSound(Sound sound) {
		players.forEach(player -> {
			player.playSound(player.getLocation(), sound, 1, 1);
		});
	}
		
}
