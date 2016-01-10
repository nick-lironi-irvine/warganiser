package org.warganiser.server.tournament;

import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.warganiser.server.player.Player;
import org.warganiser.server.player.PlayerException;
import org.warganiser.server.player.PlayerService;
import org.warganiser.server.tournament.persistence.TournamentDAO;

import com.google.inject.Inject;

public class TournamentServiceImpl implements TournamentService {

	private final TournamentDAO dao;
	private final PlayerService playerService;

	@Inject
	public TournamentServiceImpl(TournamentDAO dao, PlayerService playerService) {
		this.dao = dao;
		this.playerService = playerService;
	}

	@Override
	public Tournament createTournament(String name) throws TournamentException {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		Tournament tournament = new Tournament(name);
		try {
			return dao.update(tournament);
		} catch (PersistenceException e) {
			throw new TournamentException(e, "Unable to create tournament with name '%s'", name);
		}
	}

	@Override
	public Tournament getTournament(Long id) throws TournamentException {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		try {
			return dao.get(id);
		} catch (PersistenceException e) {
			throw new TournamentException(e, "Unable to load tournament with id '%s'", id);
		}
	}

	@Override
	public Tournament updateTournament(Tournament tournament) throws TournamentException {
		if (tournament == null) {
			throw new IllegalArgumentException("tournament cannot be null");
		}
		try {
			return dao.update(tournament);
		} catch (PersistenceException e) {
			throw new TournamentException(e, "Unable to update tournament with id '%s'", tournament.getId());
		}
	}

	@Override
	public List<Tournament> listTournaments() {
		return dao.list();
	}

	@Override
	public Tournament addPlayer(Long tournamentId, Long playerId) throws TournamentException {
		if (playerId == null) {
			throw new IllegalArgumentException("'playerId' must not be null");
		}
		Player player;
		try {
			player = this.playerService.getPlayer(playerId);
		} catch (PlayerException e) {
			throw new TournamentException(e, "Unable to add Player '%s' to Tournament '%s' due to an error loading the player", playerId, tournamentId);
		}
		if (player == null) {
			throw new IllegalArgumentException(String.format("No such Player with Id '%s'", playerId));
		}
		return addPlayer(tournamentId, player);

	}

	@Override
	public Tournament addPlayer(Long tournamentId, Player player) throws TournamentException {
		if (tournamentId == null) {
			throw new IllegalArgumentException("'tournamentId' must not be null");
		}
		Tournament updatedTournament;
		try {
			Tournament tournament = this.dao.get(tournamentId);
			if (tournament == null) {
				throw new IllegalArgumentException(String.format("No such Tournament with Id '%s'", tournamentId));
			}
			Player updatedPlayer = playerService.updatePlayer(player);
			tournament.addParticipant(updatedPlayer);
			updatedTournament = dao.update(tournament);
		} catch (PlayerException e) {
			throw new TournamentException(e, "Error when creating Player '%s' to add to Tournament '%s'", player.getName(), tournamentId);
		}
		return updatedTournament;
	}


	@Override
	public Set<Player> listPotentialPlayers(Long tournamentId) throws TournamentException {
		//TODO could probably implement this at the DB level, rather than here in memory
		if (tournamentId == null) {
			throw new IllegalArgumentException("'tournamentId' must not be null");
		}
		Tournament tournament = this.dao.get(tournamentId);
		if (tournament == null) {
			throw new IllegalArgumentException(String.format("No such Tournament with Id '%s'", tournamentId));
		}
		Set<Player> candidatePlayers = this.playerService.getPlayers();

		tournament.getParticipants().stream().forEach(p -> candidatePlayers.remove(p.getPlayer()));

		return candidatePlayers;
	}

}
