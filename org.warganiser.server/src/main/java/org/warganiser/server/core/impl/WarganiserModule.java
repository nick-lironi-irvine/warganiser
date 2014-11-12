package org.warganiser.server.core.impl;

import org.warganiser.server.core.TournamentService;

import com.google.inject.AbstractModule;

public class WarganiserModule extends AbstractModule {
	@Override
	public void configure() {
		bind(TournamentService.class).to(TournamentServiceImpl.class);
	}
}