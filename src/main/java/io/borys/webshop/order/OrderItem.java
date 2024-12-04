package io.borys.webshop.order;

import io.borys.webshop.product.Price;
import io.borys.webshop.product.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class OrderItem {
    private int quantity;

    private Price price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
}
