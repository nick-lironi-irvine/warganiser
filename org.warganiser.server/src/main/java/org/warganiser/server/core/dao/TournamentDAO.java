package org.warganiser.server.core.dao;

import javax.persistence.EntityManager;

import org.warganiser.server.core.Tournament;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * Responsible for the interaction with the persistence store for {@link Tournament} entities. 
 */
public class TournamentDAO {

	EntityManager em;

	@Inject
	public TournamentDAO(EntityManager em) {
		this.em = em;
	}

	@Transactional
	public Tournament createTournament(String name) {
		Tournament tournament = new Tournament(name);
		em.persist(tournament);
		return tournament;
	}

}
