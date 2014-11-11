package org.warganiser.server.core.impl;

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
		return dao.createTournament(name);
	}

}
