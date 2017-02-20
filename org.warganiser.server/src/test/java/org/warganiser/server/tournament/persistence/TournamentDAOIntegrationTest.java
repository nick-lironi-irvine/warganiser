package org.warganiser.server.tournament.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.warganiser.server.core.dao.AbstractDAOIntegration;
import org.warganiser.server.participant.Participant;
import org.warganiser.server.player.Player;
import org.warganiser.server.player.persistence.PlayerDAO;
import org.warganiser.server.tournament.Tournament;

public class TournamentDAOIntegrationTest extends AbstractDAOIntegration{

	private TournamentDAO daoUnderTest;
	private PlayerDAO playerDaoUnderTest;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		daoUnderTest = new TournamentDAO();
		daoUnderTest.setEntityManager(entityManager);

		playerDaoUnderTest = new PlayerDAO();
		playerDaoUnderTest.setEntityManager(entityManager);
	}

	@After
	public void tearDown() {
		// Rollback transactions by default.
		entityManager.getTransaction().rollback();

		/*
		 * Drop the contents even after transaction rollback, as tests may have
		 * committed additional transactions.
		 */
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Participant").executeUpdate();
		entityManager.createQuery("DELETE FROM Tournament").executeUpdate();
		entityManager.createQuery("DELETE FROM Player").executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test
	public void testCreateTournamentPersistsTournamentWithGivenName() {
		String tournamentName = "My Tournament Name";
		Tournament createdTournament = daoUnderTest.update(new Tournament(tournamentName));
		assertThat(createdTournament, is(notNullValue()));
		assertThat(createdTournament.getId(), is(notNullValue()));
		assertThat(createdTournament.getName(), is(equalTo(tournamentName)));
	}

	@Test
	public void testListTournamentsReturnsAllTournaments() {
		String tournamentName = "My Tournament Name";
		String tournamentName2 = tournamentName + "2";
		Tournament createdTournament = daoUnderTest.update(new Tournament(tournamentName));
		assertThat(createdTournament, is(notNullValue()));
		Tournament createdTournament2 = daoUnderTest.update(new Tournament(tournamentName2));
		assertThat(createdTournament2, is(notNullValue()));

		List<Tournament> tournaments = daoUnderTest.list();
		assertThat(tournaments, is(notNullValue()));
		assertThat(tournaments.size(), equalTo(2));
		assertThat(tournaments, contains(createdTournament, createdTournament2));
	}

	@Test
	public void testPersistenceOfTournamentsWithDuplicateNamesIsPrevented() {
		expectedException.expect(PersistenceException.class);
		// FIXME this is a terrible test
		String tournamentName = "My Tournament Name";
		Tournament t1 = new Tournament(tournamentName);
		Tournament t2 = new Tournament(tournamentName);
		daoUnderTest.update(t1);
		daoUnderTest.update(t2);
	}

	@Test
	public void testAddingAnExistingPlayerToATournamentPersistsANewParticipant() {
		//Given a Tournament exists with no participants, and a Player exists
		String playerName = "A Player";
		Player player = playerDaoUnderTest.update(new Player(playerName));
		assertThat(player, is(notNullValue()));

		String tournamentName = "My Tournament Name";
		Tournament tournament = daoUnderTest.update(new Tournament(tournamentName));
		assertThat(tournament, is(notNullValue()));
		assertThat(tournament.getParticipants(), is(emptyCollectionOf(Participant.class)));

		this.entityManager.getTransaction().commit();
		this.entityManager.getTransaction().begin();

		//When a participant is added
		tournament.addParticipant(player);
		daoUnderTest.update(tournament);

		this.entityManager.getTransaction().commit();

		//Then a Participant exists
		this.entityManager.getTransaction().begin();
		Tournament modifiedTournament = daoUnderTest.get(tournament.getId());
		assertThat(modifiedTournament, is(notNullValue()));
		assertThat(modifiedTournament.getParticipants(), contains(new Participant(tournament, player)));
	}
}
