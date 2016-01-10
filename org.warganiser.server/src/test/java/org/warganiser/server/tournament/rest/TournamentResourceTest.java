package org.warganiser.server.tournament.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;
import org.warganiser.server.resources.WarganiserWebException;
import org.warganiser.server.tournament.Tournament;
import org.warganiser.server.tournament.TournamentException;
import org.warganiser.server.tournament.TournamentService;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class TournamentResourceTest {

	private TournamentResource resourceUnderTest;

	@Mock
	private TournamentService mockTournamentService;

	@Mock
	private Tournament mockTournament;

	@Before
	public void setUp() {
		resourceUnderTest = new TournamentResource(mockTournamentService);
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

		ListResourceWrapper<TournamentDto> result = resourceUnderTest.list();
		assertThat(result.getData().size(), equalTo(1));
	}

	@Test
	public void testListTournamentsWithEmptyResult() {
		when(mockTournamentService.listTournaments()).thenReturn(new ArrayList<Tournament>());

		ListResourceWrapper<TournamentDto> result = resourceUnderTest.list();
		assertThat(result.getData().size(), equalTo(0));
	}
}
