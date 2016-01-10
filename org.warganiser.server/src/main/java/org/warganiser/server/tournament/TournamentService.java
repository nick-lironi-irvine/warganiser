package org.warganiser.server.tournament;

import java.util.List;

public interface TournamentService {

	public Tournament createTournament(String name) throws TournamentException;

	public Tournament getTournament(Long id) throws TournamentException;

	public Tournament updateTournament(Tournament tournament) throws TournamentException;

	public List<Tournament> listTournaments();
	
	public Tournament addPlayer(Long tournamentId, Long playerId) throws TournamentException;

}