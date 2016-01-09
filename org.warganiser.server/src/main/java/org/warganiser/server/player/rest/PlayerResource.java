package org.warganiser.server.player.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.warganiser.server.player.Player;
import org.warganiser.server.player.PlayerException;
import org.warganiser.server.player.PlayerService;
import org.warganiser.server.resources.WarganiserWebException;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Path("/players")
public class PlayerResource {

	private final PlayerService playerService;

	@Inject
	public PlayerResource(PlayerService service) {
		playerService = service;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{name}")
	@Transactional(rollbackOn = { WarganiserWebException.class })
	public PlayerDto create(@PathParam("name") String name) throws WarganiserWebException {
		try {
			return new PlayerDto(playerService.createPlayer(name));
		} catch (PlayerException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	public PlayerDto get(@PathParam("id") Long id) throws WarganiserWebException {
		try {
			return new PlayerDto(playerService.getPlayer(id));
		} catch (PlayerException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Produces("application/json")
	public List<PlayerDto> list() {
		List<Player> playerss = playerService.listPlayers();
		List<PlayerDto> result = new ArrayList<>(4 / 3 * playerss.size());
		for (Player tournament : playerss) {
			result.add(new PlayerDto(tournament));
		}
		return result;
	}

}
