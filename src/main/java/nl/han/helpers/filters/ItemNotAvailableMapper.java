package nl.han.helpers.filters;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.helpers.exceptions.ItemNotAvailableException;

@Provider
public class ItemNotAvailableMapper implements ExceptionMapper<ItemNotAvailableException> {
    public Response toResponse(ItemNotAvailableException e) {
        return Response.status(404).entity(e.getMessage()).type("text/plain").build();
    }
}