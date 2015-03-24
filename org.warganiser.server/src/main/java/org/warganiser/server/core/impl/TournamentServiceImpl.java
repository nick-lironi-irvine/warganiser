package org.warganiser.server.core.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentException;
import org.warganiser.server.core.TournamentService;
import org.warganiser.server.core.dao.TournamentDAO;

import com.google.inject.Inject;

public class TournamentServiceImpl implements TournamentService {

	private final TournamentDAO dao;

	@Inject
	public TournamentServiceImpl(TournamentDAO dao) {
		this.dao = dao;
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

}
