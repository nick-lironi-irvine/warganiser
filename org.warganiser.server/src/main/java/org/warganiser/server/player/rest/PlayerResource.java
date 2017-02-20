package org.warganiser.server.player.rest;

import static org.warganiser.server.player.rest.PlayerConverter.createAndPopulateResponseWrapperWithLinks;
import static org.warganiser.server.resources.AbstractResourceWrapper.CREATE;

import java.util.Set;

import javax.inject.Inject;
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
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;
import org.warganiser.server.resources.WarganiserWebException;


@Path("/players")
public class PlayerResource {

	static final String ROOT_PATH = "/players";
	private final PlayerService playerService;

	@Inject
	public PlayerResource(PlayerService service) {
		playerService = service;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{name}")
	//@Transactional(rollbackOn = { WarganiserWebException.class, RuntimeException.class})
	public SingleResourceWrapper<PlayerDto> create(@PathParam("name") String name) throws WarganiserWebException {
		try {
			Player createdPlayer = playerService.createPlayer(name);
			return createAndPopulateResponseWrapperWithLinks(createdPlayer);
		} catch (PlayerException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	public SingleResourceWrapper<PlayerDto> get(@PathParam("id") Long id) throws WarganiserWebException {
		try {
			return createAndPopulateResponseWrapperWithLinks(playerService.getPlayer(id));
		} catch (PlayerException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Produces("application/json")
	public ListResourceWrapper<PlayerDto> list() {
		Set<Player> players = playerService.getPlayers();
		ListResourceWrapper<PlayerDto> result = new ListResourceWrapper<>(ROOT_PATH);
		result.addLink(CREATE, ROOT_PATH);
		for (Player player : players) {
			result.addData(createAndPopulateResponseWrapperWithLinks(player));
		}
		return result;
	}

}
