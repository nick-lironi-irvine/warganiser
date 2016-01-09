package org.warganiser.server.resources;

import org.warganiser.server.player.PlayerService;
import org.warganiser.server.player.PlayerServiceImpl;
import org.warganiser.server.tournament.TournamentService;
import org.warganiser.server.tournament.TournamentServiceImpl;

import com.google.inject.AbstractModule;

public class WarganiserModule extends AbstractModule {
	@Override
	public void configure() {
		bind(TournamentService.class).to(TournamentServiceImpl.class);
		bind(PlayerService.class).to(PlayerServiceImpl.class);
	}
}