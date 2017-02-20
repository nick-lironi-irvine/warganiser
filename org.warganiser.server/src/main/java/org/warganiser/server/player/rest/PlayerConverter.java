package org.warganiser.server.player.rest;

import org.jvnet.hk2.annotations.Service;
import org.warganiser.server.player.Player;
import org.warganiser.server.resources.AbstractResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;

@Service
public class PlayerConverter {

	public PlayerConverter(){

	}

	public static SingleResourceWrapper<PlayerDto> createAndPopulateResponseWrapperWithLinks(Player player) {
		SingleResourceWrapper<PlayerDto> response = new SingleResourceWrapper<>(new PlayerDto(player));
		response.addLink(AbstractResourceWrapper.SELF, "%s/%s", PlayerResource.ROOT_PATH, player.getId());
		response.addLink(AbstractResourceWrapper.PARENT, "%s", PlayerResource.ROOT_PATH);
		return response;
	}

	public Player toPlayer(PlayerDto playerDto) {
		if (playerDto == null) {
			throw new IllegalArgumentException("'playerDto' must not be null");
		}
		Player player = new Player();
		player.setId(playerDto.getId());
		player.setName(playerDto.getName());
		return player;
	}
}