package org.warganiser.server.core.dao;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractDAOIntegration {

	private static EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	@BeforeClass
	public static void setUpClass() throws Exception {
		/*
		 * Use the same properties as the real DB, but override the connection
		 * to use in memory for speed and isolation.
		 */
		Properties properties = new Properties();
		properties.put("hibernate.connection.url", "jdbc:derby:memory:warganiserTestDB;create=true");
		entityManagerFactory = Persistence.createEntityManagerFactory("org.warganiser", properties);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}


	/**
	 * @Before method in sub classes must not shadow this name, otherwise this
	 *         will not be run
	 */
	@Before
	public void setUpBase() {
		entityManager = entityManagerFactory.createEntityManager();
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
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Player").executeUpdate();
		entityManager.getTransaction().commit();
	}

	protected EntityManager rollbackTransactionIfActive() {
		if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		return entityManager;
	}

}