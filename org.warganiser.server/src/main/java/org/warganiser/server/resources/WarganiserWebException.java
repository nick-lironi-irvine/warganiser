package org.warganiser.server.resources;

import javax.ws.rs.core.Response.Status;

/**
 * Generic Exception for throwing from the REST layer. Mapped with a Jersey
 * Exception Mapper to a nice JSON format.
 * 
 * @see WarganiserExceptionMapper
 */
public class WarganiserWebException extends Exception {

	private static final long serialVersionUID = 1L;
	/** Indicate the HTTP status most appropriate for this exception */
	private final Status status;

	public WarganiserWebException(Throwable cause, Status status) {
		super(cause);
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
}
