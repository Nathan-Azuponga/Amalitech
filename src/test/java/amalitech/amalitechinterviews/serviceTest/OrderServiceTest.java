package amalitech.amalitechinterviews.serviceTest;

import amalitech.amalitechinterviews.entity.Order;
import amalitech.amalitechinterviews.entity.Product;
import amalitech.amalitechinterviews.entity.User;
import amalitech.amalitechinterviews.repository.OrderRepository;
import amalitech.amalitechinterviews.repository.ProductRepository;
import amalitech.amalitechinterviews.repository.UserRepository;
import amalitech.amalitechinterviews.request.OrderRequest;
import amalitech.amalitechinterviews.request.ProductLineRequest;
import amalitech.amalitechinterviews.response.OrderResponse;
import amalitech.amalitechinterviews.service.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @Disabled("Will work on it later")
    void createOrder_Success() {
        long userId = 1L;
        User user = new User();
        OrderRequest orderRequest = new OrderRequest();
        ProductLineRequest productLineRequest = new ProductLineRequest(1L, 5);
        orderRequest.setListOfProductLines(new ArrayList<>(List.of(productLineRequest)));
        Product product = new Product();
        product.setStock(10);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productLineRequest.getProductId())).thenReturn(Optional.of(product));
        doNothing().when(orderRepository).save(any(Order.class));

        OrderResponse response = orderService.createOrder(orderRequest, userId);

        assertNotNull(response);
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productLineRequest.getProductId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
