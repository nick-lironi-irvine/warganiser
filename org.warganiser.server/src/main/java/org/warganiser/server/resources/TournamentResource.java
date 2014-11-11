package org.warganiser.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.warganiser.server.core.TournamentService;
import org.warganiser.server.resources.dto.TournamentDto;

import com.google.inject.Inject;

@Path("/tournament")
public class TournamentResource {

	private final TournamentService tournamentService;

	@Inject
	public TournamentResource(TournamentService service) {
		this.tournamentService = service;
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public TournamentDto create(@FormParam("name") String name) {
		return new TournamentDto(tournamentService.createTournament(name));
	}

}
