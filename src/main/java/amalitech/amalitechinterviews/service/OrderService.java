package amalitech.amalitechinterviews.service;

import amalitech.amalitechinterviews.entity.EntityMapper;
import amalitech.amalitechinterviews.entity.Order;
import amalitech.amalitechinterviews.entity.Product;
import amalitech.amalitechinterviews.entity.ProductLine;
import amalitech.amalitechinterviews.exception.OrderDoesNotExistException;
import amalitech.amalitechinterviews.exception.ProductDoesNotExistException;
import amalitech.amalitechinterviews.exception.StockLimitException;
import amalitech.amalitechinterviews.exception.UserDoesNotExistException;
import amalitech.amalitechinterviews.repository.OrderRepository;
import amalitech.amalitechinterviews.repository.ProductRepository;
import amalitech.amalitechinterviews.repository.UserRepository;
import amalitech.amalitechinterviews.request.OrderRequest;
import amalitech.amalitechinterviews.request.ProductLineRequest;
import amalitech.amalitechinterviews.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest, long userId) {
        Order order = new Order();
        List<ProductLine> productLines = new ArrayList<>();
        userRepository.findById(userId).ifPresentOrElse(order::setUser, () -> {
            throw new UserDoesNotExistException(userId);
        });
        orderRequest.getListOfProductLines().forEach(productLine -> validateOrder(productLine, order, productLines));
        order.setListOfProductLines(productLines);
        orderRepository.save(order);
        log.info("Order created successfully");
        return EntityMapper.INSTANCE.convertToOrderResponse(order);
    }

    public OrderResponse updateOrder(OrderRequest orderRequest, long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderDoesNotExistException(orderId));
        List<ProductLine> productLines = new ArrayList<>();
        orderRequest.getListOfProductLines().forEach(productLine -> validateOrder(productLine, order, productLines));
        order.setListOfProductLines(productLines);
        orderRepository.save(order);
        log.info("Order with id {} updated successfully", orderId);
        return EntityMapper.INSTANCE.convertToOrderResponse(order);
    }

    private void validateOrder(ProductLineRequest productLineRequest, Order order, List<ProductLine> productLines) {
        int orderedQuantity = productLineRequest.getQuantity();
        Product product = productRepository.findById(productLineRequest.getProductId()).orElseThrow(() -> new ProductDoesNotExistException(productLineRequest.getProductId()));
        int stock = product.getStock();
        if (stock >= orderedQuantity) {
            ProductLine newProductLine = new ProductLine();
            newProductLine.setQuantity(orderedQuantity);
            newProductLine.setProduct(product);
            newProductLine.setOrder(order);
            productLines.add(newProductLine);

            product.setStock(stock - orderedQuantity);
            productRepository.save(product);
        } else {
            throw new StockLimitException(product.getName(), stock, orderedQuantity);
        }
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(EntityMapper.INSTANCE::convertToOrderResponse).toList();
    }

    public OrderResponse getOrderById(long id) {
        return EntityMapper.INSTANCE.convertToOrderResponse(orderRepository.findById(id).orElseThrow(() -> new OrderDoesNotExistException(id)));
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderResponse> getOrdersByUserId(long userId) {
        return orderRepository.findOrdersByUserId(userId).stream().map(EntityMapper.INSTANCE::convertToOrderResponse).toList();
    }
}
