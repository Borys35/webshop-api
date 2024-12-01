package io.borys.webshop.product;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link Product}
 */
@Getter
@Setter
public class ProductDto implements Serializable {
    Long productId;
    String slug;
    String name;
    Long brandId;
    String brandName;
    String description;
    Long categoryId;
    String categoryName;
    Currency currency;
    double priceAmount;
    Integer stock;
    List<String> imageUrls;
    Map<String, String> specifications;

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", stock=" + stock +
                ", currency=" + currency +
                ", priceAmount=" + priceAmount +
                ", imageUrls=" + imageUrls +
                ", categoryName='" + categoryName + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}