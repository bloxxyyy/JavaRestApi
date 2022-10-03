package nl.han.helpers.filters;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.helpers.exceptions.AccountDoesNotExistException;

@Provider
public class AccountDoesNotExistMapper implements ExceptionMapper<AccountDoesNotExistException> {
    public Response toResponse(AccountDoesNotExistException e) {
        return Response.status(401).entity(e.getMessage()).type("text/plain").build();
    }
}