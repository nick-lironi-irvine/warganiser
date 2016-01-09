package org.warganiser.server.tournament.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.warganiser.server.resources.WarganiserWebException;
import org.warganiser.server.tournament.Tournament;
import org.warganiser.server.tournament.TournamentException;
import org.warganiser.server.tournament.TournamentService;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

@Path("/tournament")
public class TournamentResource {

	private final TournamentService tournamentService;

	@Inject
	public TournamentResource(TournamentService service) {
		tournamentService = service;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{name}")
	@Transactional(rollbackOn = { WarganiserWebException.class })
	public TournamentDto create(@PathParam("name") String name) throws WarganiserWebException {
		try {
			return new TournamentDto(tournamentService.createTournament(name));
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	public TournamentDto get(@PathParam("id") Long id) throws WarganiserWebException {
		try {
			return new TournamentDto(tournamentService.getTournament(id));
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{id}")
	@Transactional(rollbackOn = { WarganiserWebException.class })
	public TournamentDto update(@PathParam("id") Long id, TournamentDto tournamentDto) throws WarganiserWebException {
		try {
			Tournament tournament = tournamentService.getTournament(id);
			tournament = TournamentConverter.updateFromDto(tournament, tournamentDto);
			Tournament updatedTournament = tournamentService.updateTournament(tournament);
			return new TournamentDto(updatedTournament);
		} catch (TournamentException e) {
			throw new WarganiserWebException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Produces("application/json")
	public List<TournamentDto> list() {
		List<Tournament> tournaments = tournamentService.listTournaments();
		List<TournamentDto> result = new ArrayList<>(4 / 3 * tournaments.size());
		for (Tournament tournament : tournaments) {
			result.add(new TournamentDto(tournament));
		}
		return result;
	}

}
