package amalitech.amalitechinterviews.exception;
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String email) {
        super(String.format("%s already exists", email));
    }
}
