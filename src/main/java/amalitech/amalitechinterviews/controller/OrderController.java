package amalitech.amalitechinterviews.controller;

import amalitech.amalitechinterviews.request.OrderRequest;
import amalitech.amalitechinterviews.response.OrderResponse;
import amalitech.amalitechinterviews.service.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public void deleteOrder(@Argument("id") long id) {
        orderService.deleteOrder(id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public OrderResponse updateOrder(@Argument("input") OrderRequest orderRequest, @Argument("id") long id) {
        return orderService.updateOrder(orderRequest, id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public OrderResponse createOrder(@Argument("input") OrderRequest orderRequest, @Argument("userId") long userId) {
        return orderService.createOrder(orderRequest, userId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public OrderResponse getOrderById(@Argument("id") long id) {
        return orderService.getOrderById(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponse> getOrdersByUserId(@Argument("id") long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}
