package amalitech.amalitechinterviews.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private double price;
    private int stock;
}
