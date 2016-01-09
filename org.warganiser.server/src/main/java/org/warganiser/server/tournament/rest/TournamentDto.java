package org.warganiser.server.tournament.rest;

import java.util.List;

import org.warganiser.server.participant.rest.ParticipantConverter;
import org.warganiser.server.participant.rest.ParticipantDto;
import org.warganiser.server.tournament.Tournament;

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
	
	public List<ParticipantDto> getParticipants(){
		return ParticipantConverter.convert(tournament.getParticipants());
	}

}
