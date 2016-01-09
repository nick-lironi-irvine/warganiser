package org.warganiser.server.participant.rest;

import java.util.ArrayList;
import java.util.List;

import org.warganiser.server.participant.Participant;

/**
 * Responsible for converting between {@link Participant} and {@link ParticipantDto} objects.
 */
public class ParticipantConverter {

	public static List<ParticipantDto> convert(List<Participant> participants) {
		List<ParticipantDto> result = new ArrayList<>();
		for (Participant participant : participants) {
			result.add(convertToDto(participant));
		}
		return result;
	}

	private static ParticipantDto convertToDto(Participant participant) {
		ParticipantDto result = new ParticipantDto(participant);
		return result;
	}

}
