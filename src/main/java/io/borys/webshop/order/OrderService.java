package io.borys.webshop.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(final Order order) {
        return orderRepository.save(order);
    }

    public Page<Order> findAll(final Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findById(final Long id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
