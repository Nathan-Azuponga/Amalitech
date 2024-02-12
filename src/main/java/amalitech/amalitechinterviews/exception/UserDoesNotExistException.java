package amalitech.amalitechinterviews.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(long id) {
        super(String.format("User with id %d does not exist", id));
    }

    public UserDoesNotExistException(String email) {
        super(String.format("User with email %s does not exist", email));
    }
}
