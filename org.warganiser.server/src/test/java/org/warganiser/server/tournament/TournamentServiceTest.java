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
import java.util.List;

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
	
}
