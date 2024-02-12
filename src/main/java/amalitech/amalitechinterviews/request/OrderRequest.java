package amalitech.amalitechinterviews.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class OrderRequest {
    private List<ProductLineRequest> listOfProductLines;

    public OrderRequest(List<ProductLineRequest> productLineRequest) {
        this.listOfProductLines=productLineRequest;
    }
}
