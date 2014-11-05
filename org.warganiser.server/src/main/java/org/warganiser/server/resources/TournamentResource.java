package org.warganiser.server.resources;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentService;

import com.google.inject.Inject;

@Path("/tournament")
public class TournamentResource {
	
	private final TournamentService tournamentService;
	
	@Inject
	public TournamentResource(TournamentService service) {
		this.tournamentService = service;
	}
	
	@POST
	@Produces("application/json")
	public Tournament create(@FormParam("name") String name) {
		return tournamentService.createTournament(name);
	}
	
}

