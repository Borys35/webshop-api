package io.borys.webshop.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<Order> getOrders(final Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable final long id) {
        return orderService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    public Page<Order> getCurrentUserOrders(final Pageable pageable) {
        return orderService.findByCurrentUser(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public Order createOrder(@RequestBody final Order order) {
        return orderService.save(order);
    }
}
