package com.leo.thebridge.game;

import java.util.UUID;

public class PlayerManager {

	private UUID uuid;
	
	public PlayerManager(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
}
