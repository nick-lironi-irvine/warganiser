package org.warganiser.server.tournament.rest;

import static org.warganiser.server.resources.AbstractResourceWrapper.PARENT;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.warganiser.server.player.Player;
import org.warganiser.server.player.rest.PlayerConverter;
import org.warganiser.server.player.rest.PlayerDto;
import org.warganiser.server.resources.AbstractResourceWrapper;
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;
import org.warganiser.server.resources.WarganiserWebException;
import org.warganiser.server.tournament.Tournament;
import org.warganiser.server.tournament.TournamentException;
import org.warganiser.server.tournament.TournamentService;

@Path("/tournament")
public class TournamentResource {

	private static final String ROOT_PATH = "/tournament";

	private final TournamentService tournamentService;
	private final PlayerConverter playerConverter;

	@Inject
	public TournamentResource(TournamentService service, PlayerConverter playerConverter) {
		this.tournamentService = service;
		this.playerConverter = playerConverter;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{name}")
	//@Transactional(rollbackOn = { WarganiserWebException.class, RuntimeException.class})
	public SingleResourceWrapper<TournamentDto> create(@PathParam("name") String name) throws WarganiserWebException {
		try {
			return createAndPopulateResponseWrapperWithLinks(tournamentService.createTournament(name));
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	public SingleResourceWrapper<TournamentDto> get(@PathParam("id") Long id) throws WarganiserWebException {
		try {
			return createAndPopulateResponseWrapperWithLinks(tournamentService.getTournament(id));
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	//@Transactional(rollbackOn = { WarganiserWebException.class, RuntimeException.class})
	public SingleResourceWrapper<TournamentDto> update(@PathParam("id") Long id, TournamentDto tournamentDto) throws WarganiserWebException {
		try {
			Tournament tournament = tournamentService.getTournament(id);
			tournament = TournamentConverter.updateFromDto(tournament, tournamentDto);
			Tournament updatedTournament = tournamentService.updateTournament(tournament);
			return createAndPopulateResponseWrapperWithLinks(updatedTournament);
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Produces("application/json")
	public ListResourceWrapper<TournamentSummaryDto> list() {
		List<Tournament> tournaments = tournamentService.listTournaments();
		ListResourceWrapper<TournamentSummaryDto> response = new ListResourceWrapper<>(ROOT_PATH);
		response.addLink(AbstractResourceWrapper.CREATE, ROOT_PATH);
		for (Tournament tournament : tournaments) {
			response.addData(createAndPopulateSummaryResponseWrapperWithLinks(tournament));
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Path("/{tournamentId}/players")
	public ListResourceWrapper<PlayerDto> listPotentialPlayers(@PathParam("tournamentId") Long tournamentId) throws WarganiserWebException {
		try{
			Set<Player> players = tournamentService.listPotentialPlayers(tournamentId);
			ListResourceWrapper<PlayerDto> response = new ListResourceWrapper<>("%s/%s/players", ROOT_PATH, tournamentId);
			response.addLink(PARENT, "%s/%s", ROOT_PATH, tournamentId);
			for (Player player : players) {
				SingleResourceWrapper<PlayerDto> playerDto = PlayerConverter.createAndPopulateResponseWrapperWithLinks(player);
				playerDto.addLink("participate", "%s/%s/participant/%s", ROOT_PATH, tournamentId, player.getId());
				response.addData(playerDto);
			}
			return response;
		} catch(TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{tournamentId}/participant/{playerId}")
	//@Transactional(rollbackOn = { WarganiserWebException.class, RuntimeException.class})
	public SingleResourceWrapper<TournamentDto> addParticpant(@PathParam("tournamentId") Long tournamentId, @PathParam("playerId") Long playerId) throws WarganiserWebException {
		try {
			Tournament updatedTournament = this.tournamentService.addPlayer(tournamentId, playerId);
			return createAndPopulateResponseWrapperWithLinks(updatedTournament);
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{tournamentId}/participant")
	//@Transactional(rollbackOn = { WarganiserWebException.class, RuntimeException.class})
	public SingleResourceWrapper<TournamentDto> addParticpant(@PathParam("tournamentId") Long tournamentId, PlayerDto playerDto) throws WarganiserWebException {
		try {
			if (playerDto.getId() != null) {
				throw new IllegalArgumentException("This method must not be used for persistent Players, only Transient ones.");
			}
			Player player = playerConverter.toPlayer(playerDto);
			Tournament updatedTournament = this.tournamentService.addPlayer(tournamentId, player);
			return createAndPopulateResponseWrapperWithLinks(updatedTournament);
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	private SingleResourceWrapper<TournamentDto> createAndPopulateResponseWrapperWithLinks(Tournament tournament){
		SingleResourceWrapper<TournamentDto> response = new SingleResourceWrapper<>(new TournamentDto(tournament));
		addBaseLinks(tournament, response);
		response.addLink("candidatePlayers", "%s/%s/players", ROOT_PATH, tournament.getId());
		response.addLink("addNewPlayerAsParticipant", "%s/%s/participant", ROOT_PATH, tournament.getId());
		return response;
	}

	private SingleResourceWrapper<TournamentSummaryDto> createAndPopulateSummaryResponseWrapperWithLinks(Tournament tournament){
		SingleResourceWrapper<TournamentSummaryDto> response = new SingleResourceWrapper<>(new TournamentSummaryDto(tournament));
		addBaseLinks(tournament, response);
		return response;
	}

	private void addBaseLinks(Tournament tournament, SingleResourceWrapper<? extends TournamentSummaryDto> response) {
		response.addLink(AbstractResourceWrapper.SELF, "%s/%s", ROOT_PATH, tournament.getId());
		response.addLink(AbstractResourceWrapper.PARENT, "%s", ROOT_PATH);
	}


}
