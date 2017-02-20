package org.warganiser.server.resources;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class WarganiserEntityManagerFactory implements Factory<EntityManagerFactory> {

	private EntityManagerFactory entityManagerFactory;

	public WarganiserEntityManagerFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("org.warganiser");
	}

	@Override
	public EntityManagerFactory provide() {
		return entityManagerFactory;
	}

	@Override
	public void dispose(EntityManagerFactory factory) {
		if(factory.isOpen()) {
			factory.close();
		}
	}
}
