package exception;

public class WrongUserRegisteredException extends Exception{
    public WrongUserRegisteredException() {
        super();
    }

    public WrongUserRegisteredException(String message) {
        super(message);
    }
}
