package exception;

public class UserLoginNotValidException extends Exception{
    public UserLoginNotValidException() {
        super();
    }

    public UserLoginNotValidException(String message) {
        super(message);
    }
}
