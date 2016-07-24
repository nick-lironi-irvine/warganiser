package org.warganiser.server;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jetty.servlet.JettyWebContainerFactory;
import org.glassfish.jersey.servlet.ServletProperties;
import org.warganiser.server.resources.WarganiserModule;

import com.google.common.collect.ImmutableMap;

public class WarganiserServerLauncher {

	public static void main(String[] args) throws Exception {
		/*
		 * The port that we should run on can be set into an environment variable. Look for that variable and default to 8180 if it isn't there.
		 */
		String webPort = System.getenv("WARGANISER_SERVER_PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8180";
		}

		Server server = JettyWebContainerFactory.create(
				String.format("http://localhost:%s/", webPort),

				// Init params:
				ImmutableMap.of(
						// Set the ResourceConfig class:
						ServletProperties.JAXRS_APPLICATION_CLASS, WarganiserModule.class.getCanonicalName(),

						/* Don't autoload a wrongly configured Jackson provider! This isn't strictly necessary if you've already got the manual config right, but it's still more reassuring than having no idea what's loaded and what isn't.*/
						CommonProperties.METAINF_SERVICES_LOOKUP_DISABLE, "true"
						)
				);

		// Start the server
		server.start();

		// Wait for server to shutdown
		server.join();
	}

}
