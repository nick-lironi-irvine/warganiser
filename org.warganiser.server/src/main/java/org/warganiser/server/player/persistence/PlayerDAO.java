package org.warganiser.server.player.persistence;

import org.jvnet.hk2.annotations.Service;
import org.warganiser.server.core.dao.AbstractDAO;
import org.warganiser.server.player.Player;

/**
 * Responsible for the interaction with the persistence store for {@link Player}
 * entities.
 */
@Service
public class PlayerDAO extends AbstractDAO<Player> {

	@Override
	protected Class<Player> getClazz() {
		return Player.class;
	}

}