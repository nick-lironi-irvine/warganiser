package org.warganiser.server.core;

import com.google.inject.AbstractModule;

public class WarganiserModule extends AbstractModule {
	@Override
	public void configure() {
		bind(TournamentManager.class).to(TournamentManagerImpl.class).asEagerSingleton();
	}
}