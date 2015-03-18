package org.warganiser.server.core.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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

	@Test
	public void testListTournamentsReturnsAllTournaments() {
		String tournamentName = "My Tournament Name";
		String tournamentName2 = tournamentName + "2";
		Tournament createdTournament = daoUnderTest.createTournament(tournamentName);
		assertThat(createdTournament, is(notNullValue()));
		Tournament createdTournament2 = daoUnderTest.createTournament(tournamentName2);
		assertThat(createdTournament2, is(notNullValue()));

		List<Tournament> tournaments = daoUnderTest.listTournaments();
		assertThat(tournaments, is(notNullValue()));
		assertThat(tournaments.size(), equalTo(2));
		assertThat(tournaments, contains(createdTournament, createdTournament2));
	}

	@Test
	public void testPersistenceOfTournamentsWithDuplicateNamesIsPrevented() {
		expectedException.expect(PersistenceException.class);
		// FIXME this is a terrible test
		String tournamentName = "My Tournament Name";
		daoUnderTest.createTournament(tournamentName);
		daoUnderTest.createTournament(tournamentName);
	}
}
