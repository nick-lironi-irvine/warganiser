package org.warganiser.server.core.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.warganiser.server.core.Player;

public class PlayerDAOIntegrationTest extends AbstractDAOIntegration {

	PlayerDAO daoUnderTest;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		daoUnderTest = injector.getInstance(PlayerDAO.class);
	}

	@After
	public void tearDown() {
		/*
		 * Drop the contents even after transaction rollback, as tests may have
		 * committed additional transactions.
		 */
		rollbackTransactionIfActive();
		EntityManager entityManager = injector.getInstance(EntityManager.class);
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE FROM Player").executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test
	public void testPersistPlayerWithName() {
		String playerName = "John Doe";
		Player createdPlayer = daoUnderTest.update(new Player(playerName));
		assertThat(createdPlayer, is(notNullValue()));
		// assume that a non-null generated id indicates that it was actually
		// persisted
		assertThat(createdPlayer.getId(), is(notNullValue()));
		assertThat(createdPlayer.getName(), is(equalTo(playerName)));

		Player retrievedPlayer = daoUnderTest.get(createdPlayer.getId());
		assertThat(retrievedPlayer.getName(), equalTo(playerName));
	}

	@Test
	public void testListPlayersReturnsAllPlayers() {
		String playerName = "John Doe";
		String playerName2 = playerName + "2";
		Player createdPlayer = daoUnderTest.update(new Player(playerName));
		assertThat(createdPlayer, is(notNullValue()));
		Player createdPlayer2 = daoUnderTest.update(new Player(playerName2));
		assertThat(createdPlayer2, is(notNullValue()));

		List<Player> players = daoUnderTest.list();
		assertThat(players, is(notNullValue()));
		assertThat(players.size(), equalTo(2));
		assertThat(players, contains(createdPlayer, createdPlayer2));
	}

}
