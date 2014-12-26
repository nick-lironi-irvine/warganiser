package org.warganiser.server.resources;

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
import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentService;
import org.warganiser.server.resources.dto.TournamentDto;

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
	public void testCreateReturnsCreatedTournament() {
		String name = "Test Name";

		when(mockTournamentService.createTournament(anyString())).thenReturn(mockTournament);

		TournamentDto createdTournament = resourceUnderTest.create(name);
		assertThat(createdTournament, is(notNullValue()));

		verify(mockTournamentService).createTournament(name);
	}

	@Test
	public void testListTournaments() {
		List<Tournament> tournaments = new ArrayList<>();
		tournaments.add(mockTournament);
		when(mockTournamentService.listTournaments()).thenReturn(tournaments);

		List<TournamentDto> result = resourceUnderTest.list();
		assertThat(result.size(), equalTo(1));
	}

	@Test
	public void testListTournamentsWithEmptyResult() {
		when(mockTournamentService.listTournaments()).thenReturn(new ArrayList<Tournament>());

		List<TournamentDto> result = resourceUnderTest.list();
		assertThat(result.size(), equalTo(0));
	}
}
