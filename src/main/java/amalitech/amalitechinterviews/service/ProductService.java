package amalitech.amalitechinterviews.service;

import amalitech.amalitechinterviews.entity.EntityMapper;
import amalitech.amalitechinterviews.entity.Order;
import amalitech.amalitechinterviews.entity.Product;
import amalitech.amalitechinterviews.entity.ProductLine;
import amalitech.amalitechinterviews.exception.OrderDoesNotExistException;
import amalitech.amalitechinterviews.exception.ProductDoesNotExistException;
import amalitech.amalitechinterviews.exception.ProductExistException;
import amalitech.amalitechinterviews.repository.OrderRepository;
import amalitech.amalitechinterviews.repository.ProductRepository;
import amalitech.amalitechinterviews.request.ProductRequest;
import amalitech.amalitechinterviews.response.ProductLineResponse;
import amalitech.amalitechinterviews.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        productRepository.findByName(productRequest.getName()).ifPresent(product -> {
            throw new ProductExistException(productRequest.getName());
        });
        Product product = EntityMapper.INSTANCE.convertToProduct(productRequest);
        productRepository.save(product);
        log.info("Product successfully created");
        return EntityMapper.INSTANCE.convertToProductResponse(product);
    }

    public ProductResponse updateProduct(ProductRequest productRequest, long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductDoesNotExistException(id));
        EntityMapper.INSTANCE.updateProductDetails(product, productRequest);
        productRepository.save(product);
        log.info("Product with id {} successfully updated", id);
        return EntityMapper.INSTANCE.convertToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(EntityMapper.INSTANCE::convertToProductResponse)
                .toList();
    }
    public List<ProductLineResponse> getProductsByOrderId(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderDoesNotExistException(orderId));
        return order.getListOfProductLines().stream()
                .map(EntityMapper.INSTANCE::convertToProductLineResponse)
                .collect(Collectors.toList());
    }
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
