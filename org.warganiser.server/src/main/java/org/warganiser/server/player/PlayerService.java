package org.warganiser.server.player;

import java.util.Set;

public interface PlayerService {

	Player createPlayer(String name) throws PlayerException;

	Player getPlayer(Long id) throws PlayerException;

	Player updatePlayer(Player player) throws PlayerException;

	Set<Player> getPlayers();

}
