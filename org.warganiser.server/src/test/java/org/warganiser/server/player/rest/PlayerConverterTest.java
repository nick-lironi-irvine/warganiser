package org.warganiser.server.player.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.warganiser.server.player.Player;

public class PlayerConverterTest {

	private PlayerConverter serviceUnderTest;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setup(){
		this.serviceUnderTest = new PlayerConverter();
	}

	@Test
	public void testToPlayerForNullPlayerDtoThrowsException() {
		//Then
		expectedException.expect(IllegalArgumentException.class);

		//Given
		PlayerDto playerDto = null;

		//When
		this.serviceUnderTest.toPlayer(playerDto);
	}

	@Test
	public void testToPlayerForNonNullPlayerDtoReturnsPopulatedPlayer() {
		//Given
		PlayerDto playerDto = new PlayerDto();
		playerDto.setId(42L);
		playerDto.setName("Player Name");

		//When
		Player player = this.serviceUnderTest.toPlayer(playerDto);

		//Then
		assertThat(player.getId(), equalTo(playerDto.getId()));
		assertThat(player.getName(), equalTo(playerDto.getName()));
	}

}
