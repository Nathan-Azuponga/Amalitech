package amalitech.amalitechinterviews.exception;

public class ProductDoesNotExistException extends RuntimeException{
    public ProductDoesNotExistException(long id) {
        super(String.format("Product with name %d does not exist", id));
    }
}
