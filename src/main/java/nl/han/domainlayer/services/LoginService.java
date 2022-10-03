package nl.han.domainlayer.services;

import nl.han.domainlayer.viewmodels.TokenizedUserViewModel;
import nl.han.domainlayer.viewmodels.UserViewModel;

public interface LoginService {
    TokenizedUserViewModel GetUser(UserViewModel userDto);
}
