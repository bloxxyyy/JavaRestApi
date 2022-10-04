package nl.han.domainlayer.services;

import nl.han.domainlayer.mappers.UserMapper;
import nl.han.domainlayer.viewmodels.TokenizedUserViewModel;
import nl.han.domainlayer.viewmodels.UserViewModel;
import nl.han.servicelayer.Entities.User;
import nl.han.servicelayer.daos.LoginDAOImpl;
import nl.han.servicelayer.daos.TokenDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class LoginServiceTest {

    private UserMapper _mapper = Mockito.spy(UserMapper.class);
    private LoginDAOImpl _loginDao = Mockito.mock(LoginDAOImpl.class);
    private TokenDAOImpl _tokenDao = Mockito.mock(TokenDAOImpl.class);

    private ITokenGenerator _tokenGenerator = Mockito.mock(TokenGeneratorImpl.class);

    private UserViewModel _userViewModel;

    private final String _user = "user";
    private final String _pass = "pass";
    private final String _correctToken = "1234";


    @BeforeEach
    public void setup() {
        when(_tokenGenerator.getToken()).thenReturn(_correctToken);

        _userViewModel = new UserViewModel();
        _userViewModel.setUser(_user);
        _userViewModel.setPassword(_pass);
        when(_mapper.toUser(_userViewModel)).thenReturn(new User(_user, _pass));
    }

    @Test
    public void getUserReturnsUserDtoWithCorrectDataTest() {
        //Arrange
        var user = _mapper.toUser(_userViewModel);
        when(_loginDao.get(user)).thenReturn(new User(_user, _pass));
        LoginServiceImpl lsi = new LoginServiceImpl(_mapper, _loginDao, _tokenDao, _tokenGenerator);

        //Act
        var tokenizedUserViewModel = lsi.GetUser(_userViewModel);

        //Assert
        Assertions.assertEquals(tokenizedUserViewModel.getToken(), _correctToken);
    }


    @Test
    public void returnNullWhenUserDoesNotExistInUserDtoTest() {
        //Arrange
        when(_loginDao.get(_mapper.toUser(_userViewModel))).thenReturn(null);
        LoginServiceImpl lsi = new LoginServiceImpl(_mapper, _loginDao, _tokenDao, _tokenGenerator);

        //Act
        var data = lsi.GetUser(_userViewModel);

        //Assert
        Assertions.assertNull(data);
    }

}
