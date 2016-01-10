package org.warganiser.server.tournament.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.warganiser.server.player.Player;
import org.warganiser.server.player.rest.PlayerConverter;
import org.warganiser.server.player.rest.PlayerDto;
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;
import org.warganiser.server.resources.WarganiserWebException;
import org.warganiser.server.tournament.Tournament;
import org.warganiser.server.tournament.TournamentException;
import org.warganiser.server.tournament.TournamentService;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class TournamentResourceTest {

	private TournamentResource resourceUnderTest;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private TournamentService mockTournamentService;
	@Mock
	private PlayerConverter mockPlayerConverter;
	@Mock
	private Tournament mockTournament;
	@Mock
	private Player mockPlayer;
	@Mock
	private PlayerDto mockPlayerDto;

	@Before
	public void setUp() {
		resourceUnderTest = new TournamentResource(mockTournamentService, mockPlayerConverter);
	}

	@Test
	public void testCreateReturnsCreatedTournament() throws TournamentException, WarganiserWebException {
		String name = "Test Name";

		when(mockTournamentService.createTournament(anyString())).thenReturn(mockTournament);

		SingleResourceWrapper<TournamentDto> createdTournament = resourceUnderTest.create(name);
		assertThat(createdTournament, is(notNullValue()));

		verify(mockTournamentService).createTournament(name);
	}

	@Test
	public void testListTournaments() {
		List<Tournament> tournaments = new ArrayList<>();
		tournaments.add(mockTournament);
		when(mockTournamentService.listTournaments()).thenReturn(tournaments);

		ListResourceWrapper<TournamentSummaryDto> result = resourceUnderTest.list();
		assertThat(result.getData().size(), equalTo(1));
	}

	@Test
	public void testListTournamentsWithEmptyResult() {
		when(mockTournamentService.listTournaments()).thenReturn(new ArrayList<Tournament>());

		ListResourceWrapper<TournamentSummaryDto> result = resourceUnderTest.list();
		assertThat(result.getData().size(), equalTo(0));
	}

	@Test
	public void testAddNewPlayerAsParticipant() throws TournamentException, WarganiserWebException {
		//Given
		Long tournamentId = 1L;
		given(mockPlayerDto.getId()).willReturn(null); //Indicate transient Player
		given(mockTournamentService.getTournament(tournamentId)).willReturn(mockTournament);
		given(mockTournamentService.addPlayer(anyLong(), argThat(any(Player.class)))).willReturn(mockTournament);
		given(mockPlayerConverter.toPlayer(mockPlayerDto)).willReturn(mockPlayer);

		//When
		SingleResourceWrapper<TournamentDto> updatedTournament = this.resourceUnderTest.addParticpant(tournamentId, mockPlayerDto);

		//Then
		verify(mockTournamentService).addPlayer(tournamentId, mockPlayer);
		assertThat(updatedTournament, not(equalTo(null)));
	}

	@Test
	public void testAddNewPlayerAsParticipantWontUseThePlayerIdProvided() throws TournamentException, WarganiserWebException {
		//Then
		expectedException.expect(IllegalArgumentException.class);

		//Given
		Long tournamentId = 1L;
		Long playerId = 42L;
		given(mockPlayerDto.getId()).willReturn(playerId);

		//When
		this.resourceUnderTest.addParticpant(tournamentId, mockPlayerDto);
	}
}
