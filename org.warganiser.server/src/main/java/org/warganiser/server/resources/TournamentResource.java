package org.warganiser.server.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentException;
import org.warganiser.server.core.TournamentService;
import org.warganiser.server.resources.dto.TournamentDto;

import com.google.inject.Inject;

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
