package nl.han.domainlayer.services;

import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.Token;
import nl.han.servicelayer.Entities.User;
import nl.han.domainlayer.mappers.UserMapper;
import nl.han.domainlayer.viewmodels.TokenizedUserViewModel;
import nl.han.domainlayer.viewmodels.UserViewModel;
import nl.han.servicelayer.daos.Dao;

import java.util.UUID;

public class LoginServiceImpl implements LoginService {

    @Inject
    private Dao<User> _userDao;

    @Inject
    private Dao<Token> _tokenDao;

    @Inject
    private UserMapper _mapper;

    @Override
    public TokenizedUserViewModel GetUser(UserViewModel userDto) {
        var newToken = UUID.randomUUID().toString();

        var user = _mapper.toUser(userDto);
        if (_userDao.get(user) == null) return null;
        var token = new Token(user.GetUsername(), newToken);
        if (_tokenDao.get(token) == null) _tokenDao.add(token);
        else _tokenDao.update(token);

        return _mapper.toDto(user.GetUsername(), token.GetToken());
    }
}
