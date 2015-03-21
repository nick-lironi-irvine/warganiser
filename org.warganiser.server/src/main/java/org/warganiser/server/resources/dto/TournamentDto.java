package org.warganiser.server.resources.dto;

import org.warganiser.server.core.Tournament;

public class TournamentDto {

	private final Tournament tournament;

	/* For Jackson deserialisation */
	public TournamentDto() {
		this.tournament = new Tournament();
	}

	public TournamentDto(Tournament tournament) {
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

	public Integer getPoints() {
		return tournament.getPoints();
	}

	public void setPoints(Integer points) {
		tournament.setPoints(points);
	}

}
