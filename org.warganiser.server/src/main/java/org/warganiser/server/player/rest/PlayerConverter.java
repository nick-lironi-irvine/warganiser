package org.warganiser.server.player.rest;

import org.warganiser.server.player.Player;
import org.warganiser.server.resources.AbstractResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;

public final class PlayerConverter {

	public static SingleResourceWrapper<PlayerDto> createAndPopulateResponseWrapperWithLinks(Player player) {
		SingleResourceWrapper<PlayerDto> response = new SingleResourceWrapper<>(new PlayerDto(player));
		response.addLink(AbstractResourceWrapper.SELF, "%s/%s", PlayerResource.ROOT_PATH, player.getId());
		response.addLink(AbstractResourceWrapper.PARENT, "%s", PlayerResource.ROOT_PATH);
		return response;
	}
}