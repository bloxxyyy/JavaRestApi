package nl.han.domainlayer.mappers;

import nl.han.servicelayer.Entities.User;
import nl.han.domainlayer.viewmodels.TokenizedUserViewModel;
import nl.han.domainlayer.viewmodels.UserViewModel;

public class UserMapper {
    public TokenizedUserViewModel toDto(String username, String token) {
        var dto = new TokenizedUserViewModel();
        dto.setUser(username);
        dto.setToken(token);
        return dto;
    }

    public User toUser(UserViewModel userDTO) {
        return new User(userDTO.getUser(), userDTO.getPassword());
    }
}
