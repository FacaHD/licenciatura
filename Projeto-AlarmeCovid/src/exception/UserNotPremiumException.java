package exception;

public class UserNotPremiumException extends Exception{
    public UserNotPremiumException() {
        super();
    }

    public UserNotPremiumException(String message) {
        super(message);
    }
}
