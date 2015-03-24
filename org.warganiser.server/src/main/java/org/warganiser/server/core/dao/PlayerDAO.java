package org.warganiser.server.core.dao;

import org.warganiser.server.core.Player;

/**
 * Responsible for the interaction with the persistence store for {@link Player}
 * entities.
 */
public class PlayerDAO extends AbstractDAO<Player> {

	@Override
	protected Class<Player> getClazz() {
		return Player.class;
	}

}