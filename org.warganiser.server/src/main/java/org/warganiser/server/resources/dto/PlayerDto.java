package org.warganiser.server.resources.dto;

import org.warganiser.server.core.Player;

public class PlayerDto {

	private Player player;

	/* For Jackson deserialisation */
	public PlayerDto() {
		this.player = new Player();
	}

	public PlayerDto(Player player) {
		this.player = player;
	}

	public Long getId() {
		return player.getId();
	}

	public void setId(Long id) {
		player.setId(id);
	}

	public String getName() {
		return player.getName();
	}

	public void setName(String name) {
		player.setName(name);
	}

}
