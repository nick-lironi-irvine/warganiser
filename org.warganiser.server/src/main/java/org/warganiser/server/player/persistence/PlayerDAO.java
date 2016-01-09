package org.warganiser.server.player.persistence;

import org.warganiser.server.core.dao.AbstractDAO;
import org.warganiser.server.player.Player;

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