package org.warganiser.server.resources.dto;

import org.warganiser.server.core.Tournament;

public class TournamentDto {

	private Tournament tournament;

	public TournamentDto(Tournament tournament){
		this.tournament = tournament;
		
	}
	
	public String getName() {
		return tournament.getName();
	}

}
