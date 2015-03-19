package org.warganiser.server.core.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
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
import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentException;
import org.warganiser.server.core.TournamentService;
import org.warganiser.server.core.dao.TournamentDAO;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceTest {

	private TournamentService serviceUnderTest;
	@Mock
	private TournamentDAO mockDAO;
	@Mock
	private Tournament mockTournament;

	@Before
	public void beforeTest() {
		serviceUnderTest = new TournamentServiceImpl(mockDAO);
		when(mockDAO.createTournament(anyString())).thenAnswer(new Answer<Tournament>() {
			@Override
			public Tournament answer(InvocationOnMock invocation) throws Throwable {
				return new Tournament((String) invocation.getArguments()[0]);
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
		when(mockDAO.listTournaments()).thenReturn(Collections.singletonList(mockTournament));
		List<Tournament> createdTournaments = serviceUnderTest.listTournaments();
		assertThat(createdTournaments, is(notNullValue()));
		assertThat(createdTournaments.size(), is(equalTo(1)));
	}
}
