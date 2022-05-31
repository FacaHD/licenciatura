package exception;

public class UserAcessToServerInvalidException extends Exception {
    public UserAcessToServerInvalidException() {
        super();
    }

    public UserAcessToServerInvalidException(String message) {
        super(message);
    }
}
