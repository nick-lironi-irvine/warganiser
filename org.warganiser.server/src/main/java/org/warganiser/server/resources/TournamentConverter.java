package org.warganiser.server.resources;

import org.warganiser.server.core.Tournament;
import org.warganiser.server.resources.dto.TournamentDto;

public class TournamentConverter {

	public static Tournament updateFromDto(Tournament tournament, TournamentDto tournamentDto) {
		tournament.setName(tournamentDto.getName());
		tournament.setPoints(tournamentDto.getPoints());
		return tournament;
	}

}
