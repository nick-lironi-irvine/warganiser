package org.warganiser.server.core.dao;

import org.warganiser.server.core.Tournament;

public class TournamentDAO {

	public Tournament createTournament(String name) {
		return new Tournament(name);
	}

}
