package org.warganiser.server.core.impl;

import java.util.List;

import org.warganiser.server.core.Tournament;
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
	public Tournament createTournament(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		return dao.createTournament(name);
	}

	@Override
	public List<Tournament> listTournaments() {
		return dao.listTournaments();
	}

}
