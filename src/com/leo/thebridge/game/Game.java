package com.leo.thebridge.game;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leo.thebridge.scoreboard.FastBoard;
import com.leo.thebridge.utils.ItemBuilder;
import com.leo.thebridge.utils.Utils;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.inventory.PlayerInventory;

public class Game {

	private List<Player> players;
	private List<ActivePlayer> activePlayers;
	
	private String id;
	public GameState gameState;
	
	private VirtualArena virtualArena;
	
	private boolean waitingNextRound = false;
	private int nextRoundTimer = 3;
	private ActivePlayer winner;
	
	private HashMap<Player, FastBoard> boards;
	
	public Game(String id, File schematicFile) {
		this.id = id;
		this.gameState = GameState.BLANK;
		
		this.players = new ArrayList<Player>();
		this.activePlayers = new ArrayList<ActivePlayer>();
		this.boards = new HashMap<>();
		
		this.virtualArena = new VirtualArena("Boo", schematicFile, this.id);
		this.winner = null;
		
		Utils.log("A new game is being created §8[" + id + "]");
	}
	
	public void addPlayer(Player player) {
		
		player.getInventory().clear();
		
		this.players.add(player);		
				
		ActivePlayer activePlayer = new ActivePlayer(player, this);
		
		this.activePlayers.add(activePlayer);
		
		FastBoard board = new FastBoard(player);
		
		board.updateTitle("§e§lThe Bridge");
		board.updateLines(Arrays.asList("", "§cAguardando...", ""));
		
		this.boards.put(player,  board);
		
		player.getInventory().clear();
		player.teleport(this.getVirtualArena().getLocationOne());
		
		broadcast(Utils.colorize("§7" + player.getName() + " §eentrou na partida. §7(" + getPlayersCount() + "/2)"));
		
	}
	
	public void removePlayer(Player player) {
		broadcast(Utils.colorize("§7" + player.getName() + " §cabandonou a partida."));
		
		player.getInventory().clear();
		
		this.activePlayers.remove(getActivePlayerFromPlayer(player));
		this.players.remove(player);
		
		FastBoard board = this.boards.remove(player);
		
		if (board != null) {
			board.delete();
		}
		
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

	// index 0 will always be red team
	public void giveTeams() {
		ActivePlayer red = activePlayers.get(0);
		red.setTeam(Team.RED);
		red.setSpawnLocation(virtualArena.getLocationOne());
		red.setColor(ChatColor.RED);
		
		if (activePlayers.size() > 1) {
			ActivePlayer blue = activePlayers.get(1);
			blue.setTeam(Team.BLUE);
			blue.setSpawnLocation(virtualArena.getLocationTwo());
			red.setColor(ChatColor.BLUE);
			
			red.setEnemy(blue);
			blue.setEnemy(red);
		} 
		
	}
	
	@SuppressWarnings("deprecation")
	public void giveItems(ActivePlayer player) {
		Team team = player.getTeam();
		Player bukkitPlayer = player.getPlayer();
		
		PlayerInventory inventory = bukkitPlayer.getInventory();
		inventory.clear();
		
		Color color;
		byte clayColor;
		
		if (team == Team.RED) {
			color = Color.RED;
			clayColor = DyeColor.RED.getData();
		} else {
			color = Color.BLUE;
			clayColor = DyeColor.BLUE.getData();
		}
		
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemStack pickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 4).toItemStack();
		
		ItemStack clay = new ItemBuilder(Material.STAINED_CLAY, 64, clayColor).toItemStack();
		ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 8);
		ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(color).toItemStack();
		ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(color).toItemStack();
		ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(color).toItemStack();
		ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(color).toItemStack();
		
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		List<ItemStack> items = Arrays.asList(sword, bow, pickaxe, clay, clay, apple);
		
		items.forEach(inventory::addItem);
		inventory.setItem(8, arrow);
		
		inventory.setHelmet(helmet);
		inventory.setChestplate(chestplate);
		inventory.setLeggings(leggings);
		inventory.setBoots(boots);
	}
	
	public void giveItems() {
		giveItems(activePlayers.get(0));
		if (activePlayers.size() > 1) {
			giveItems(activePlayers.get(1));	
		}
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
	
	
	public void handlePoint(ActivePlayer player) {
		player.addPoint();
		//teleportPlayersToSpot();
		setWaitingNextRound(true);
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
		
	public String getId() {
		return id;
	}
	public List<Player> getPlayers() {
		return this.players;
	}
	public VirtualArena getVirtualArena() {
		return virtualArena;
	}
	public List<ActivePlayer> getActivePlayers() {
		return activePlayers;
	}
	public HashMap<Player, FastBoard> getBoards() {
		return boards;
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
					return activePlayer.getUuid() == player.getUniqueId();
				}).findFirst().get();
	}
	public ActivePlayer getActivePlayerFromPlayer(Player player) {
		return this.activePlayers
				.stream()
				.filter(active -> {
					return active.getUuid() == player.getUniqueId(); 
					})
				.findFirst()
				.get();
	}
	
	public boolean isWaitingNextRound() {
		return this.waitingNextRound;
	}
	
	public int getNextRoundTimer() {
		return this.nextRoundTimer;
	}
	
	public void decreaseNextRoundTimer() {
		this.nextRoundTimer--;
	}
	
	public void setNextRoundTimer(int seconds) {
		this.nextRoundTimer = seconds;
	}
	
	public void setWaitingNextRound(boolean waiting) {
		
		if (waiting) {
			this.getVirtualArena().fillCages();
			this.teleportPlayersToSpot();
			this.giveItems();
			this.setNextRoundTimer(3);
		} else {
			this.getVirtualArena().removeCages();
			this.setNextRoundTimer(3);
		}
		
		waitingNextRound = waiting;
	}
		
}
