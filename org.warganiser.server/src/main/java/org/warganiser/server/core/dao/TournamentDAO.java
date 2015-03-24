package org.warganiser.server.core.dao;

import org.warganiser.server.core.Tournament;

/**
 * Responsible for the interaction with the persistence store for
 * {@link Tournament} entities.
 */
public class TournamentDAO extends AbstractDAO<Tournament> {

	@Override
	protected Class<Tournament> getClazz() {
		return Tournament.class;
	}

}