package org.warganiser.server.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

@Service
public class WarganiserEntityManager implements Factory<EntityManager> {

	private WarganiserEntityManagerFactory factory;

	@Inject
	public WarganiserEntityManager(WarganiserEntityManagerFactory factory) {
		this.factory = factory;
	}

	@Override
	public EntityManager provide() {
		return factory.provide().createEntityManager();
	}

	@Override
	public void dispose(EntityManager entityManager) {
		if(entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
