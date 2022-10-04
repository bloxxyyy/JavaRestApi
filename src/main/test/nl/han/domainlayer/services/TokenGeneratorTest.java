package nl.han.domainlayer.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenGeneratorTest {

    @Test
    public void generateNewStringTest() {
        // Arrange
        TokenGeneratorImpl tokenGenerator = new TokenGeneratorImpl();
        String string1 = "";
        String string2 = "";
        String string3 = "";
        String string4 = "";

        // Act
        string1 = tokenGenerator.getToken();
        string2 = tokenGenerator.getToken();
        string3 = tokenGenerator.getToken();
        string4 = tokenGenerator.getToken();

        // Assert
        Assertions.assertNotEquals(string1, string2);
        Assertions.assertNotEquals(string1, string3);
        Assertions.assertNotEquals(string1, string4);
        Assertions.assertNotEquals(string2, string3);
        Assertions.assertNotEquals(string2, string4);
        Assertions.assertNotEquals(string3, string4);
    }

}
