package org.warganiser.server.resources;

import org.warganiser.server.core.TournamentService;
import org.warganiser.server.core.impl.TournamentServiceImpl;

import com.google.inject.AbstractModule;

public class WarganiserModule extends AbstractModule {
	@Override
	public void configure() {
		bind(TournamentService.class).to(TournamentServiceImpl.class);
	}
}