package io.borys.webshop.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Page<Order> getOrders(final Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable final long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody final Order order) {
        return orderService.save(order);
    }
}
