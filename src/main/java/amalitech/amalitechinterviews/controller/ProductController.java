package amalitech.amalitechinterviews.controller;

import amalitech.amalitechinterviews.request.ProductRequest;
import amalitech.amalitechinterviews.response.ProductLineResponse;
import amalitech.amalitechinterviews.response.ProductResponse;
import amalitech.amalitechinterviews.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public void deleteProduct(@Argument("id") long id) {
        productService.deleteProduct(id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ProductResponse updateProduct(@Argument("input") ProductRequest productRequest, @Argument long id) {
        return productService.updateProduct(productRequest, id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ProductResponse createProduct(@Argument("input") ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductLineResponse> getProductsByOrderId(@Argument("id") long id) {
        return productService.getProductsByOrderId(id);
    }
}
