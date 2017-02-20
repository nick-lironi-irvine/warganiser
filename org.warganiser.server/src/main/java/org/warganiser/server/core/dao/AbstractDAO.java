package org.warganiser.server.core.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jvnet.hk2.annotations.Service;

@Service
public abstract class AbstractDAO<T extends Object> {

	EntityManager entityManager;

	private Class<T> clazz;

	public AbstractDAO() {
		this.clazz = getClazz();
	}

	@Inject
	public void setEntityManager(EntityManager em){
		this.entityManager = em;
	}

	protected abstract Class<T> getClazz();

	public T get(Long id) {
		return entityManager.find(clazz, id);
	}

	public List<T> list() {
		return entityManager.createQuery(String.format("from %s", clazz.getSimpleName()), clazz).getResultList();
	}

	public T update(T entity) {
		entityManager.persist(entity);
		return entity;
	}
}