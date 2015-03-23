package org.warganiser.server.core;

public class PlayerException extends Exception {
	private static final long serialVersionUID = 1L;

	public PlayerException(Throwable cause, String message, Object... messageParams) {
		super(String.format(message, messageParams), cause);
	}

}
