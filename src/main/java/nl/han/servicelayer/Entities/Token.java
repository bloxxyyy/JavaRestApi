package nl.han.servicelayer.Entities;

public class Token {
    private String username;
    private String token;

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public void SetUsername(String username) {
        this.username = username;
    }
    public String GetUsername() {
        return username;
    }

    public void SetToken(String token) {
        this.token = token;
    }
    public String GetToken() {
        return token;
    }

}
