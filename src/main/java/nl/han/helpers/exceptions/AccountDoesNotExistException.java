package nl.han.helpers.exceptions;

public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException(String msg) {
        super(msg);
    }
}
