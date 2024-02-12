package amalitech.amalitechinterviews.response;

import lombok.Data;

import java.util.List;
@Data
public class OrderResponse {
    private long id;
    private long userId;
    private String email;
    private List<ProductLineResponse> listOfProductLines;
}
