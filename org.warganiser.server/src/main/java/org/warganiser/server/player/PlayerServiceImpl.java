package org.warganiser.server.player;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.jvnet.hk2.annotations.Service;
import org.warganiser.server.player.persistence.PlayerDAO;

@Service
public class PlayerServiceImpl implements PlayerService {

	private final PlayerDAO dao;

	@Inject
	public PlayerServiceImpl(PlayerDAO dao) {
		this.dao = dao;
	}

	@Override
	public Player createPlayer(String name) throws PlayerException {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		Player player = new Player(name);
		try {
			return dao.update(player);
		} catch (PersistenceException e) {
			throw new PlayerException(e, "Unable to create player with name '%s'", name);
		}
	}

	@Override
	public Player getPlayer(Long id) throws PlayerException {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		try {
			return dao.get(id);
		} catch (PersistenceException e) {
			throw new PlayerException(e, "Unable to load player with id '%s'", id);
		}
	}

	@Override
	public Set<Player> getPlayers() {
		return new HashSet<>(dao.list());
	}

	@Override
	public Player updatePlayer(Player player) throws PlayerException {
		return dao.update(player);
	}

}
