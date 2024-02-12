package amalitech.amalitechinterviews.request;

import lombok.Data;


@Data
public class ProductLineRequest {
    private long productId;
    private int quantity;

    public ProductLineRequest(long id, int quantity) {
        this.productId=id;
        this.quantity=quantity;
    }
}
