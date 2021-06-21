package com.leo.thebridge.game;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.leo.thebridge.utils.Cuboid;
import com.leo.thebridge.utils.Utils;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Game {

	private List<Player> players;
	private List<ActivePlayer> activePlayers;
	private String id;
	public GameState gameState;
	
	private VirtualArena virtualArena;
	
	private int round = 1;
	private ActivePlayer winner;
	
	public Game(String id, VirtualArena arena) {
		this.id = id;
		this.gameState = GameState.BLANK;
		this.players = new ArrayList<Player>();
		this.activePlayers = new ArrayList<ActivePlayer>();
		this.virtualArena = arena;
		this.winner = null;
		
		
		
		Utils.log("A new game is being created §8[" + id + "]");
	}
	
	public String getId() {
		return id;
	}
	public List<Player> getPlayers() {
		return this.players;
	}
	public VirtualArena getVirtualArena() {
		return virtualArena;
	}
	public int getRound() {		
		return round;
	}
	

	
	
	public List<ActivePlayer> getActivePlayers() {
		return activePlayers;
	}
	
	public void addPlayer(Player player) {
		
		player.getInventory().clear();
		
		player.teleport(this.getVirtualArena().getLocationOne());
		
		this.players.add(player);
		this.activePlayers.add(new ActivePlayer(player));

		broadcast(Utils.colorize("§7" + player.getName() + " §eentrou na partida. §7(" + getPlayersCount() + "/2)"));
		
	}
	
	public void removePlayer(Player player) {
		broadcast(Utils.colorize("§7" + player.getName() + " §cabandonou a partida."));
		
		player.getInventory().clear();
		
		this.activePlayers.remove(getActivePlayerFromPlayer(player));
		this.players.remove(player);
		
	}
	
	public void reset() {
		if (!getPlayers().isEmpty() || getPlayers() != null) {
			for (Player player : getPlayers()) {
				player.performCommand("lobby");
			}
		}
		
		players.clear();
		activePlayers.clear();
		
		virtualArena.unload();

	}
	
	public int getPlayersCount() {
		return this.players.size();
	}	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public ActivePlayer getWinner() {
		return winner;
	}
	
	public void setWinner(ActivePlayer winner) {
		this.winner = winner;
	}
	
	public void broadcast(String message) {
		players.forEach((player) -> player.sendMessage(Utils.colorize(message)));
	}
	
	public void sendActionBar(String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        players.forEach(player -> {
        	((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
	}
	
	public void sendActionBar(Player player, String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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
	
	// index 0 will always be red team
	public void giveTeams() {
		activePlayers.get(0).setTeam(Team.RED);
		if (activePlayers.size() == 2) {
			activePlayers.get(1).setTeam(Team.BLUE);
		} 
		
	}
	
	public void teleportPlayersToSpot() {
		getPlayerFromActivePlayer(getRedTeamPlayer()).teleport(virtualArena.getLocationOne());
		if (getPlayersCount() == 2) {
			getPlayerFromActivePlayer(getBlueTeamPlayer()).teleport(virtualArena.getLocationTwo());
		}
	}
	
	public void teleportPlayerToRespectiveSpot(Player player, ActivePlayer activePlayer) {
		Team team = activePlayer.getTeam();
		if (team == Team.RED) {
			player.teleport(virtualArena.getLocationOne());
		} else if (team == Team.BLUE) {
			player.teleport(virtualArena.getLocationTwo());
		}
	}
	
	public void handlePoint(ActivePlayer player) {
		player.addPoint();
		teleportPlayersToSpot();
	}
	
	public ActivePlayer getRedTeamPlayer() {
		return this.activePlayers 
				.stream()
				.filter(player -> {return player.getTeam() == Team.RED;})
				.findFirst()
				.get();
	}	
	
	
	public ActivePlayer getBlueTeamPlayer() {
		return this.activePlayers
				.stream()
				.filter(player -> {return player.getTeam() == Team.BLUE;})
				.findFirst()
				.get();
	}
	
	public Player getPlayerFromActivePlayer(ActivePlayer activePlayer) {
		return this.players
				.stream()
				.filter(player -> {
					return activePlayer.getUUID() == player.getUniqueId();
				}).findFirst().get();
	}
	
	
	public ActivePlayer getActivePlayerFromPlayer(Player player) {
		return this.activePlayers
				.stream()
				.filter(active -> {
					return active.getUUID() == player.getUniqueId(); 
					})
				.findFirst()
				.get();
	}
	
		
}
