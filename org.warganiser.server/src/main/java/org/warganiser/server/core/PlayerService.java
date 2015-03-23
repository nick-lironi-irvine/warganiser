package org.warganiser.server.core;

import java.util.List;

public interface PlayerService {

	Player createPlayer(String name) throws PlayerException;

	Player getPlayer(Long id) throws PlayerException;

	List<Player> listPlayers();

}
