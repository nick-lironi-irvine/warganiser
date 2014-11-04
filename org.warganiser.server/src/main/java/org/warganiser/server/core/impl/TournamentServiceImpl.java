package org.warganiser.server.core.impl;

import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentService;

public class TournamentServiceImpl implements TournamentService {

	@Override
	public Tournament createTournament(String name) {
		return new Tournament(name);
	}

}
