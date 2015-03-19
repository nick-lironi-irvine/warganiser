package org.warganiser.server.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Used to map application specific exceptions into a useful format for clients
 * in JSON format
 */
@Provider
public class WarganiserExceptionMapper implements ExceptionMapper<WarganiserWebException> {

	/**
	 * Wrapper that gives a useful set of error data back to the client
	 */
	public class ErrorMessage {

		private WarganiserWebException exception;

		public ErrorMessage(WarganiserWebException ex) {
			this.exception = ex;
		}

		public int getStatusCode() {
			return exception.getStatus().getStatusCode();
		}

		public String getMessage() {
			return exception.getMessage();
		}

	}

	public Response toResponse(WarganiserWebException ex) {
		return Response.status(ex.getStatus()).entity(new ErrorMessage(ex)).type(MediaType.APPLICATION_JSON).build();
	}

}
