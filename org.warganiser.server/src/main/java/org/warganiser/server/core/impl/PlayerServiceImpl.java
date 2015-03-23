package org.warganiser.server.core.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.warganiser.server.core.Player;
import org.warganiser.server.core.PlayerException;
import org.warganiser.server.core.PlayerService;
import org.warganiser.server.core.dao.PlayerDAO;

import com.google.inject.Inject;

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
		try {
			return dao.createPlayer(name);
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
			return dao.getPlayer(id);
		} catch (PersistenceException e) {
			throw new PlayerException(e, "Unable to load player with id '%s'", id);
		}
	}

	@Override
	public List<Player> listPlayers() {
		return dao.listPlayers();
	}

}
