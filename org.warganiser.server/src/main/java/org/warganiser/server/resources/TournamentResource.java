package org.warganiser.server.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.warganiser.server.core.TournamentManager;

import com.google.inject.Inject;

@Path("/tournament")
public class TournamentResource {
	
	private final TournamentManager manager;
	
	@Inject
	public TournamentResource(TournamentManager manager) {
		this.manager = manager;
	}
	
	@GET
	@Produces("application/json")
	public String retrieve() {
		return "foo";
	}
	
}

