package org.warganiser.server.tournament;

import java.util.List;
import java.util.Set;

import org.warganiser.server.player.Player;

public interface TournamentService {

	public Tournament createTournament(String name) throws TournamentException;

	public Tournament getTournament(Long id) throws TournamentException;

	public Tournament updateTournament(Tournament tournament) throws TournamentException;

	public List<Tournament> listTournaments();
	
	public Tournament addPlayer(Long tournamentId, Long playerId) throws TournamentException;
	
	public Set<Player> listPotentialPlayers(Long tournamentId) throws TournamentException;

}
