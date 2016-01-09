package org.warganiser.server.player;

import java.util.List;

public interface PlayerService {

	Player createPlayer(String name) throws PlayerException;

	Player getPlayer(Long id) throws PlayerException;

	List<Player> listPlayers();

}
