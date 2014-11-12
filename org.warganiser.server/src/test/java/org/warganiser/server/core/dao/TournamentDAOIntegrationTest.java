package org.warganiser.server.core.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.warganiser.server.core.Tournament;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class TournamentDAOIntegrationTest {

	private static Injector injector;
	private static PersistService persistService;
	private TournamentDAO daoUnderTest;

	@BeforeClass
	public static void setUpClass() throws Exception {
		/*
		 * Use the same properties as the real DB, but override the connection
		 * to use in memory for speed.
		 */
		Properties properties = new Properties();
		properties.put("hibernate.connection.url", "jdbc:derby:memory:warganiserTestDB;create=true");
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

	@Before
	public void setUp() {
		daoUnderTest = injector.getInstance(TournamentDAO.class);
	}

	@After
	public void tearDown() {
		/*
		 * Would be best to ensure the transaction is rolled back after each
		 * test to keep clean state. There seem to be no hooks in guice-persist
		 * to support this, so for now just drop the contents
		 */
		EntityManager entityManager = injector.getInstance(EntityManager.class);
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Tournament").executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test
	public void testCreateTournamentPersistsTournamentWithGivenName() {
		String tournamentName = "My Tournament Name";
		Tournament createdTournament = daoUnderTest.createTournament(tournamentName);
		assertThat(createdTournament, is(notNullValue()));
		assertThat(createdTournament.getId(), is(notNullValue()));
		assertThat(createdTournament.getName(), is(equalTo(tournamentName)));
	}

	@Test(expected = PersistenceException.class)
	public void testTournamentNamesAreUnique() {
		String tournamentName = "My Tournament Name";
		Tournament firstTournament = daoUnderTest.createTournament(tournamentName);
		assertThat(firstTournament, is(notNullValue()));

		daoUnderTest.createTournament(tournamentName);
	}
}
