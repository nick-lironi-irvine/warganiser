package org.warganiser.server.tournament.rest;

import org.warganiser.server.tournament.Tournament;

public class TournamentConverter {

	public static Tournament updateFromDto(Tournament tournament, TournamentDto tournamentDto) {
		tournament.setName(tournamentDto.getName());
		tournament.setPoints(tournamentDto.getPoints());
		tournament.setStartDateTime(tournamentDto.getStartDateTime());
		tournament.setEndDateTime(tournamentDto.getEndDateTime());
		//TODO bind participants too
		return tournament;
	}

}
