package handler.errors;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
