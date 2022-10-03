package nl.han.presentationlayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.domainlayer.services.LoginService;
import nl.han.domainlayer.viewmodels.UserViewModel;
import nl.han.helpers.exceptions.AccountDoesNotExistException;

@Path("/login")
public class LoginResource {

    @Inject
    private LoginService _loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response LoginRequestResponse(UserViewModel userDto) {
        var user = _loginService.GetUser(userDto);

        if (user == null) {
            String NOT_AUTHORIZED = "Invalid user information entered!";
            throw new AccountDoesNotExistException(NOT_AUTHORIZED);
        }


        return Response.status(200).entity(user).build();
    }
}
