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
		try {
			return dao.createTournament(name);
		} catch (PersistenceException e) {
			throw new TournamentException(e, "Unable to create tournament with name '%s'", name);
		}
	}

	@Override
	public List<Tournament> listTournaments() {
		return dao.listTournaments();
	}

}
