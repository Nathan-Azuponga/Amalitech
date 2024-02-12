package amalitech.amalitechinterviews.entity;
import amalitech.amalitechinterviews.request.ProductRequest;
import amalitech.amalitechinterviews.request.UserRequest;
import amalitech.amalitechinterviews.response.OrderResponse;
import amalitech.amalitechinterviews.response.ProductLineResponse;
import amalitech.amalitechinterviews.response.ProductResponse;
import amalitech.amalitechinterviews.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "id", target = "id")
    UserResponse convertToUserDto(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "password", target = "password")
    User convertToUser(UserRequest userRequest);

    void updateUserDetails(@MappingTarget User user, UserRequest userRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", expression = "java(order.getUser().getId())")
    @Mapping(target = "email", expression = "java(order.getUser().getEmail())")
    @Mapping(target = "listOfProductLines", source = "listOfProductLines")
    OrderResponse convertToOrderResponse(Order order);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stock", source = "stock")
    ProductResponse convertToProductResponse(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stock", source = "stock")
    Product convertToProduct(ProductRequest productRequest);

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "productName", expression = "java(productLine.getProduct().getName())")
    @Mapping(target = "unitPrice", expression = "java(productLine.getProduct().getPrice())")
    @Mapping(target = "productId", expression = "java(productLine.getProduct().getId())")
    ProductLineResponse convertToProductLineResponse(ProductLine productLine);

    void updateProductDetails(@MappingTarget Product product, ProductRequest productRequest);
}