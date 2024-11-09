package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String slug;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Price price;

    private Integer stock;

    @ElementCollection
    private List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "specification", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "specification_name")
    @Column(name = "specification_value")
    private Map<String, String> specifications;

    @Builder
    public Product(String name,
                   String slug,
                   String description,
                   Brand brand,
                   Category category,
                   Price price,
                   Integer stock,
                   List<String> imageUrls,
                   Map<String, String> specifications) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.imageUrls = imageUrls;
        this.specifications = specifications;
    }
}
