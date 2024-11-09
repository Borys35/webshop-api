package io.borys.webshop.brand;

import io.borys.webshop.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Product> products;

    @Builder
    public Brand(String name) {
        this.name = name;
    }
}
