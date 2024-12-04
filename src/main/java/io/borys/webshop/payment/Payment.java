package io.borys.webshop.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.borys.webshop.order.Order;
import io.borys.webshop.product.Price;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Order order;

    private Price price;

    private PaymentStatus status = PaymentStatus.PENDING;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "paymentDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date paymentDate;

    @Builder
    public Payment(Order order, Price price) {
        this.order = order;
        this.price = price;
    }
}
