package io.borys.webshop.order;

import io.borys.webshop.payment.Payment;
import io.borys.webshop.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"))
    private Set<OrderItem> items;

    private OrderStatus status = OrderStatus.WAITING_FOR_PAYMENT;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Payment payment;

    @Builder
    public Order(Set<OrderItem> items, User user, Payment payment) {
        this.items = items;
        this.user = user;
        this.payment = payment;
    }
}
