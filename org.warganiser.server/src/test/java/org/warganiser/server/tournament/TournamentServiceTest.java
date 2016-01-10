package org.warganiser.server.tournament;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.warganiser.server.participant.Participant;
import org.warganiser.server.player.Player;
import org.warganiser.server.player.PlayerException;
import org.warganiser.server.player.PlayerService;
import org.warganiser.server.tournament.persistence.TournamentDAO;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceTest {

	private TournamentService serviceUnderTest;
	@Mock
	private PlayerService mockPlayerService;
	@Mock
	private TournamentDAO mockTournamentDAO;
	@Mock
	private Tournament mockTournament;
	@Mock
	private Player mockPlayer;

	@Before
	public void beforeTest() {
		serviceUnderTest = new TournamentServiceImpl(mockTournamentDAO, mockPlayerService);
		when(mockTournamentDAO.update(any(Tournament.class))).thenAnswer(new Answer<Tournament>() {
			@Override
			public Tournament answer(InvocationOnMock invocation) throws Throwable {
				return (Tournament) invocation.getArguments()[0];
			}
		});
	}

	@Test
	public void testCreateTournamentReturnsTournamentWithGivenName() throws TournamentException {
		String name = "Test Tournament";
		Tournament createdTournament = serviceUnderTest.createTournament(name);
		assertThat(createdTournament, is(notNullValue()));
		assertThat(createdTournament.getName(), is(equalTo(name)));
	}

	@Test
	public void testlistTournament() {
		when(mockTournamentDAO.list()).thenReturn(Collections.singletonList(mockTournament));
		List<Tournament> createdTournaments = serviceUnderTest.listTournaments();
		assertThat(createdTournaments, is(notNullValue()));
		assertThat(createdTournaments.size(), is(equalTo(1)));
	}
	
	@Test
	public void testAddingAPlayerToATournamentCreatesANewParticpiant() throws TournamentException, PlayerException {
		//Given
		Long tournamentId = 1L; 
		Long playerId = 2L;
		
		Tournament tournament = new Tournament();
		
		given(mockTournamentDAO.get(tournamentId)).willReturn(tournament );
		given(mockPlayerService.getPlayer(playerId)).willReturn(mockPlayer);

		Participant expectedParticipant = new Participant(mockTournament, mockPlayer);
		
		//When
		Tournament updatedTournament = serviceUnderTest.addPlayer(tournamentId, playerId);

		//Then
		assertThat(updatedTournament.getParticipants(), is(notNullValue()));
		assertThat(updatedTournament.getParticipants(), contains(expectedParticipant));
	}
	
	@Test
	public void testListPotentialPlayersIncludesAllPlayersWhenTournamentHasNoParticipants() throws TournamentException {
		//Given
		Long tournamentId = 42L;
		Set<Player> allPlayers = Collections.singleton(new Player("player"));
		
		given(mockTournamentDAO.get(tournamentId)).willReturn(mockTournament);
		given(mockPlayerService.getPlayers()).willReturn(allPlayers);

		//When
		Set<Player> potentialPlayers = serviceUnderTest.listPotentialPlayers(tournamentId);
		
		//Then
		assertThat(potentialPlayers, is(notNullValue()));
		assertThat(potentialPlayers, is(equalTo(allPlayers)));
	}
	
	@Test
	public void testListPotentialPlayersExcludesCurrentParticpantsWhenTournamentHasParticipants() throws TournamentException {
		//Given
		Long tournamentId = 42L;
		Set<Player> allPlayers = new HashSet<>();
		Player participatingPlayer = new Player("player 1");
		allPlayers.add(participatingPlayer);
		allPlayers.add(new Player("player 2"));
		allPlayers.add(new Player("player 3"));
		Participant currentParticipant = new Participant(mockTournament, participatingPlayer);
		
		given(mockTournamentDAO.get(tournamentId)).willReturn(mockTournament);
		given(mockTournament.getParticipants()).willReturn(Collections.singleton(currentParticipant));
		given(mockPlayerService.getPlayers()).willReturn(allPlayers);
		
		//When
		Set<Player> potentialPlayers = serviceUnderTest.listPotentialPlayers(tournamentId);
		
		//Then
		SetView<Player> expectedAvailablePlayers = Sets.difference(allPlayers, Collections.singleton(participatingPlayer));
		assertThat(potentialPlayers, is(notNullValue()));
		assertThat(potentialPlayers, is(equalTo(expectedAvailablePlayers)));
	}
	
}
