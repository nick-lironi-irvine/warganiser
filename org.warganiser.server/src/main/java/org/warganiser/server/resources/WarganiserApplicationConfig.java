package org.warganiser.server.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.warganiser.server.player.rest.PlayerResource;
import org.warganiser.server.tournament.rest.TournamentResource;

public class WarganiserApplicationConfig extends ResourceConfig {

	public WarganiserApplicationConfig() {
		property("com.sun.jersey.config.property.packages", "org.warganiser.server.resources, org.warganiser.server.tournament.rest, org.warganiser.server.player.rest");
		property(org.glassfish.jersey.CommonProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);

		register(TournamentResource.class);
		register(PlayerResource.class);

		register(JacksonFeature.class);
	}
}
