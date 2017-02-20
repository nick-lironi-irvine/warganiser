package org.warganiser.server.tournament.persistence;

import org.jvnet.hk2.annotations.Service;
import org.warganiser.server.core.dao.AbstractDAO;
import org.warganiser.server.tournament.Tournament;

/**
 * Responsible for the interaction with the persistence store for
 * {@link Tournament} entities.
 */
@Service
public class TournamentDAO extends AbstractDAO<Tournament> {

	@Override
	protected Class<Tournament> getClazz() {
		return Tournament.class;
	}

}