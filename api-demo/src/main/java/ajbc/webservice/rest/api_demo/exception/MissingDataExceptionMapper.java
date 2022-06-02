package ajbc.webservice.rest.api_demo.exception;

import ajbc.webservice.rest.api_demo.models.ErrorMessage;
import ajbc.webservice.rest.api_demo.models.InternalErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException>{

	@Override
	public Response toResponse(MissingDataException e) {
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), InternalErrorCode.INVALID_PARAMETER, "google.com");
		return Response.status(Status.NOT_FOUND)
		.entity(errorMessage)
		.build();
	}
}