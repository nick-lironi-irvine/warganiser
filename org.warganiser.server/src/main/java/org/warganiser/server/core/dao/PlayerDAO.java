package org.warganiser.server.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.warganiser.server.core.Player;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * Responsible for the interaction with the persistence store for {@link Player}
 * entities.
 */
public class PlayerDAO {

	EntityManager em;

	@Inject
	public PlayerDAO(EntityManager em) {
		this.em = em;
	}

	@Transactional
	public Player createPlayer(String name) {
		Player player = new Player(name);
		em.persist(player);
		return player;
	}

	public Player getPlayer(Long id) {
		return em.find(Player.class, id);
	}

	public List<Player> listPlayers() {
		return em.createQuery("from Player", Player.class).getResultList();
	}
}