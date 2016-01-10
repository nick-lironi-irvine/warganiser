package org.warganiser.server.tournament.rest;

import org.warganiser.server.participant.rest.ParticipantConverter;
import org.warganiser.server.participant.rest.ParticipantDto;
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.tournament.Tournament;

public class TournamentDto extends TournamentSummaryDto {

	/* For Jackson deserialisation */
	public TournamentDto() {
		super();
	}

	public TournamentDto(Tournament tournament) {
		super(tournament);
	}

	public Integer getPoints() {
		return tournament.getPoints();
	}

	public void setPoints(Integer points) {
		tournament.setPoints(points);
	}
	
	public ListResourceWrapper<ParticipantDto> getParticipants(){
		return ParticipantConverter.convert(tournament.getParticipants());
	}

}
