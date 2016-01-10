package org.warganiser.server.participant.rest;

import java.util.Set;

import org.warganiser.server.participant.Participant;
import org.warganiser.server.resources.ListResourceWrapper;
import org.warganiser.server.resources.SingleResourceWrapper;

/**
 * Responsible for converting between {@link Participant} and {@link ParticipantDto} objects.
 */
public class ParticipantConverter {

	public static ListResourceWrapper<ParticipantDto> convert(Set<Participant> set) {
		ListResourceWrapper<ParticipantDto> result = new ListResourceWrapper<>();
		for (Participant participant : set) {
			result.addData(convertToDto(participant));
		}
		return result;
	}

	private static SingleResourceWrapper<ParticipantDto> convertToDto(Participant participant) {
		SingleResourceWrapper<ParticipantDto> singleResourceWrapper = new SingleResourceWrapper<ParticipantDto>(new ParticipantDto(participant));
		return singleResourceWrapper;
	}

}
