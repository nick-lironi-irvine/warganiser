package org.warganiser.server.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentService;

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
		
		Tournament createdTournament = resourceUnderTest.create(name);
		assertThat(createdTournament, is(notNullValue()));
		
		verify(mockTournamentService).createTournament(name);
	}

}
