package amalitech.amalitechinterviews.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductResponse {
    private long id;
    private String productName;
    private double price;
    private int stock;
}
