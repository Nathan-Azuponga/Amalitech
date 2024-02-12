package amalitech.amalitechinterviews.serviceTest;

import amalitech.amalitechinterviews.entity.Product;
import amalitech.amalitechinterviews.exception.ProductDoesNotExistException;
import amalitech.amalitechinterviews.exception.ProductExistException;
import amalitech.amalitechinterviews.repository.OrderRepository;
import amalitech.amalitechinterviews.repository.ProductRepository;
import amalitech.amalitechinterviews.request.ProductRequest;
import amalitech.amalitechinterviews.service.ProductService;
import amalitech.amalitechinterviews.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_Success() {
        ProductRequest productRequest = new ProductRequest("Product Name", 10.00, 5);
        when(productRepository.findByName("Product Name")).thenReturn(Optional.empty());

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_ProductExists_ThrowsException() {
        ProductRequest productRequest = new ProductRequest("Product Name", 10.00, 5);
        when(productRepository.findByName("Product Name")).thenReturn(Optional.of(new Product()));

        assertThrows(ProductExistException.class, () -> productService.createProduct(productRequest));
        verify(productRepository, never()).save(any(Product.class));
    }
    @Test
    void updateProduct_ProductExists_Success() {
        // Given
        long productId = 1L;
        ProductRequest productRequest = new ProductRequest("Updated Name", 15.00, 10);
        Product existingProduct = new Product(); // Assume default constructor and setters
        existingProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        ProductResponse updatedResponse = productService.updateProduct(productRequest, productId);

        assertNotNull(updatedResponse);
        assertEquals("Updated Name", updatedResponse.getProductName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ProductDoesNotExist_ThrowsException() {
        long productId = 1L;
        ProductRequest productRequest = new ProductRequest("Nonexistent", 20.00, 5);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExistException.class, () -> productService.updateProduct(productRequest, productId));
    }

    @Test
    void getAllProducts_ReturnsProductList() {
        // Given
        Product product1 = new Product(); // Assume a constructor or setters to set properties
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // When
        List<ProductResponse> products = productService.getAllProducts();

        // Then
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository).findAll();
    }
}
