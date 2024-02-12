package amalitech.amalitechinterviews.exception;

public class StockLimitException extends RuntimeException{
    public StockLimitException(String name, int stock, int quantity) {
        super(String.format("Stock limit reached for product %s. Stock: %d, Quantity: %d", name, stock, quantity));
    }
}
