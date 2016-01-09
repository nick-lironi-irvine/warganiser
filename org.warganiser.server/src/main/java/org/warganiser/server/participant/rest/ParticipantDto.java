package org.warganiser.server.participant.rest;

import org.warganiser.server.participant.Participant;
import org.warganiser.server.player.Player;

public class ParticipantDto {
	
	private Long id;
	private String name;

	public ParticipantDto(){
	}

	public ParticipantDto(Participant participant) {
		Player player = participant.getPlayer();
		this.id = player.getId();
		this.name = player.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long playerId) {
		this.id = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String playerName) {
		this.name = playerName;
	}
	
}
