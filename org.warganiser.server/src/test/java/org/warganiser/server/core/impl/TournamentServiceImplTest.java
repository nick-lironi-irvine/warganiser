package org.warganiser.server.core.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.warganiser.server.core.Tournament;
import org.warganiser.server.core.TournamentService;

public class TournamentServiceImplTest {
	
	private TournamentService serviceUnderTest;
	
	@Before
	public void beforeTest(){
		this.serviceUnderTest = new TournamentServiceImpl();
	}
	
	@Test
	public void testCreateTournamentReturnsTournamentWithGivenName() {
		String name = "Test Tournament";
		Tournament createdTournament = this.serviceUnderTest.createTournament(name);
		assertThat(createdTournament, is(notNullValue()));
		assertThat(createdTournament.getName(), is(equalTo(name)));
	}

}
