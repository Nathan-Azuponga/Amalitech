package amalitech.amalitechinterviews.response;

import lombok.Data;

@Data
public class ProductLineResponse {
    private long productId;
    private int quantity;
    private double unitPrice;
    private String productName;
}
