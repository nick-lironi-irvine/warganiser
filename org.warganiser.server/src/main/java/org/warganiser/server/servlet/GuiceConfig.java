package org.warganiser.server.servlet;

import java.util.HashMap;
import java.util.Map;

import org.warganiser.server.core.impl.WarganiserModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class GuiceConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				install(new WarganiserModule());
				install(new JpaPersistModule("org.warganiser"));

				// Set init params for Jersey
				Map<String, String> params = new HashMap<>();
				params.put("com.sun.jersey.config.property.packages", "org.warganiser.server.resources");
				params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

				/*
				 * Ensure a session-per-http-request strategy to ensure
				 * EntityManager is available to inject. See
				 * https://github.com/google/guice/wiki/JPA
				 */
				filter("/*").through(PersistFilter.class);
				// Route all requests through GuiceContainer
				serve("/*").with(GuiceContainer.class, params);
			}
		});
	}

}
