package amalitech.amalitechinterviews.exception;

public class OrderDoesNotExistException extends RuntimeException {
public OrderDoesNotExistException(long id) {
        super(String.format("Order with id %d does not exist", id));
        }
}
