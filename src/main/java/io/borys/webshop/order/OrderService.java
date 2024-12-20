package io.borys.webshop.order;

import io.borys.webshop.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Page<Order> findByUserId(final Long userId, final Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Page<Order> findByCurrentUser(final Pageable pageable) {
        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        return orderRepository.findAllByUserId(currentUser.getId(), pageable);
    }
}
