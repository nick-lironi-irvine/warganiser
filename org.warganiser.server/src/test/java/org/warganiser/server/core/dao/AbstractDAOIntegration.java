package org.warganiser.server.core.dao;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public abstract class AbstractDAOIntegration {

	protected static Injector injector;
	static PersistService persistService;

	@BeforeClass
	public static void setUpClass() throws Exception {
		/*
		 * Use the same properties as the real DB, but override the connection
		 * to use in memory for speed.
		 */
		Properties properties = new Properties();
		properties.put("hibernate.connection.url", "jdbc:derby:memory:warganiserTestDB;create=true");
		properties.put("hibernate.hbm2ddl.auto", "create");
		Module testPersistenceProperties = new JpaPersistModule("org.warganiser").properties(properties);
		injector = Guice.createInjector(testPersistenceProperties);
		persistService = injector.getInstance(PersistService.class);
		persistService.start();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (persistService != null) {
			persistService.stop();
		}
	}

	/**
	 * @Before method in sub classes must not shadow this name, otherwise this
	 *         will not be run
	 */
	@Before
	public void setUpBase() {
		EntityManager entityManager = injector.getInstance(EntityManager.class);
		entityManager.getTransaction().begin();
	}

	/**
	 * @After method in sub classes must not shadow this name, otherwise this
	 *        will not be run
	 */
	@After
	public void tearDownBase() {
		// Rollback transactions by default.
		rollbackTransactionIfActive();

		/*
		 * Drop the contents even after transaction rollback, as tests may have
		 * committed additional transactions.
		 */
		EntityManager entityManager = injector.getInstance(EntityManager.class);
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Player").executeUpdate();
		entityManager.getTransaction().commit();
	}

	protected EntityManager rollbackTransactionIfActive() {
		EntityManager entityManager = injector.getInstance(EntityManager.class);
		if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		return entityManager;
	}

}