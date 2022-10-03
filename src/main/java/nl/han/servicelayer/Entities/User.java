package nl.han.servicelayer.Entities;

public class User {
    private String username;
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void SetUsername(String username) {
        this.username = username;
    }
    public String GetUsername() {
        return username;
    }

    public void SetPassword(String password) {
        this.password = password;
    }
    public String GetPassword() {
        return password;
    }

}
