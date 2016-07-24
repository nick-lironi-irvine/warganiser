package org.warganiser.server.tournament.rest;

import org.joda.time.DateTime;
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

	//TODO set participants?
	public ListResourceWrapper<ParticipantDto> getParticipants(){
		return ParticipantConverter.convert(tournament.getParticipants());
	}

	public DateTime getStartDateTime() {
		return tournament.getStartDateTime();
	}

	public void setStartDateTime(DateTime startDateTime) {
		tournament.setStartDateTime(startDateTime);
	}

	public DateTime getEndDateTime() {
		return tournament.getEndDateTime();
	}

	public void setEndDateTime(DateTime endDateTime) {
		tournament.setEndDateTime(endDateTime);
	}

}
