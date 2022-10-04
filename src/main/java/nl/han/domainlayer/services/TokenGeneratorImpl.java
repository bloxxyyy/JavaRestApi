package nl.han.domainlayer.services;

import java.util.UUID;

public class TokenGeneratorImpl implements ITokenGenerator {

    @Override
    public String getToken() {
        return UUID.randomUUID().toString();
    }
}
