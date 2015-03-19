package org.warganiser.server.core;

public class TournamentException extends Exception {
	private static final long serialVersionUID = 1L;

	public TournamentException(Throwable cause, String message, Object... messageParams) {
		super(String.format(message, messageParams), cause);
	}

}
