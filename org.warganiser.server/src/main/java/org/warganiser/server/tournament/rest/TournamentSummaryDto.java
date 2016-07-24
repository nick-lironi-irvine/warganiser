package org.warganiser.server.tournament.rest;

import org.warganiser.server.tournament.Tournament;

public class TournamentSummaryDto {

	protected Tournament tournament;

	public TournamentSummaryDto() {
		super();
		this.tournament = new Tournament();
	}

	public TournamentSummaryDto(Tournament tournament) {
		this.tournament = tournament;
	}

	public Long getId() {
		return tournament.getId();
	}

	public void setId(Long id) {
		tournament.setId(id);
	}

	public String getName() {
		return tournament.getName();
	}

	public void setName(String name) {
		tournament.setName(name);
	}
}