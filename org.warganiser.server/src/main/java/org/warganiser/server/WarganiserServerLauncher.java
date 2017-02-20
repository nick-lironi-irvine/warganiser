package org.warganiser.server;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.warganiser.server.resources.WarganiserApplicationConfig;

import gov.va.oia.HK2Utilities.HK2RuntimeInitializer;

public class WarganiserServerLauncher {

	public static void main(String[] args) throws Exception {
		ServiceLocator serviceLocator = HK2RuntimeInitializer.init("warganiserServiceLocator", false, "org.warganiser");

		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8180).build();
		Server server = JettyHttpContainerFactory.createServer(baseUri, new WarganiserApplicationConfig(), serviceLocator);

		// Wait for server to shutdown
		server.join();
	}

}
