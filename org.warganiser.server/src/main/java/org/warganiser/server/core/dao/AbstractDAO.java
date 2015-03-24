package org.warganiser.server.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;

public abstract class AbstractDAO<T extends Object> {

	@Inject
	EntityManager em;

	private Class<T> clazz;

	// Default constructor required for Guice
	public AbstractDAO() {
		this.clazz = getClazz();
	}

	protected abstract Class<T> getClazz();

	public T get(Long id) {
		return em.find(clazz, id);
	}

	public List<T> list() {
		return em.createQuery(String.format("from %s", clazz.getSimpleName()), clazz).getResultList();
	}

	public T update(T entity) {
		em.persist(entity);
		return entity;
	}
}