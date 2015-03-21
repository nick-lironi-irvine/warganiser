package org.warganiser.server.core;

import java.util.List;

public interface TournamentService {

	public Tournament createTournament(String name) throws TournamentException;

	public Tournament getTournament(Long id) throws TournamentException;

	public List<Tournament> listTournaments();

}
