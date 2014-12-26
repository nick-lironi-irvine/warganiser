package org.warganiser.server.core;

import java.util.List;

public interface TournamentService {

	public Tournament createTournament(String name);

	public List<Tournament> listTournaments();

}
