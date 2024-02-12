package amalitech.amalitechinterviews.exception;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String name) {
        super(String.format("Product with name %s already exists", name));
    }
}
