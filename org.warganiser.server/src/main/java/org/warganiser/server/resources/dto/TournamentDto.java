package org.warganiser.server.resources.dto;

import org.warganiser.server.core.Tournament;

public class TournamentDto {

	private final Tournament tournament;

	public TournamentDto(Tournament tournament) {
		this.tournament = tournament;
	}

	public String getName() {
		return tournament.getName();
	}

	public Long getId() {
		return tournament.getId();
	}
}
