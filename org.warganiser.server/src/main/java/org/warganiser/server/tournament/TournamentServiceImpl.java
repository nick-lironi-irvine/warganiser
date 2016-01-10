package org.warganiser.server.tournament;

import java.util.List;

import javax.persistence.PersistenceException;

import org.warganiser.server.participant.Participant;
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
		if (tournamentId == null) {
			throw new IllegalArgumentException("'tournamentId' must not be null");
		}
		if (playerId == null) {
			throw new IllegalArgumentException("'playerId' must not be null");
		}
		Tournament tournament = this.dao.get(tournamentId);
		if (tournament == null) {
			throw new IllegalArgumentException(String.format("No such Tournament with Id '%s'", tournamentId));
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
		tournament.addParticipant(player);
		return dao.update(tournament);
	}

}