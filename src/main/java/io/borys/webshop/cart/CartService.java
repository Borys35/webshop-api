package io.borys.webshop.cart;

import io.borys.webshop.order.Order;
import io.borys.webshop.order.OrderItem;
import io.borys.webshop.order.OrderRepository;
import io.borys.webshop.product.Product;
import io.borys.webshop.product.ProductDto;
import io.borys.webshop.product.ProductRepository;
import io.borys.webshop.user.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@SessionScope
@Transactional
public class CartService {

    private final ConcurrentMap<ProductDto, Integer> products;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartService(final ProductRepository productRepository, OrderRepository orderRepository) {
        products = new ConcurrentHashMap<>();
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void addProduct(ProductDto product, int quantity) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public void changeQuantity(ProductDto product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        } else if (products.containsKey(product)) {
            products.put(product, quantity);
        }
    }

    public void removeProduct(ProductDto product) {
        products.remove(product);
    }

    public Map<ProductDto, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void clearProducts() {
        products.clear();
    }

    public void checkout() {

        // Check quantity and stock. Deduct if sufficient amount. Throw if not.
        List<Product> productsToSave = new ArrayList<>();
        Set<OrderItem> orderItems = new HashSet<>();
        for (Map.Entry<ProductDto, Integer> entry : getProducts().entrySet()) {
            ProductDto productDto = entry.getKey();
            Integer quantity = entry.getValue();

            if (productDto.getStock() >= quantity) {
                Product product = productRepository.findById(productDto.getProductId()).orElseThrow();
                product.setStock(product.getStock() - quantity);
                productsToSave.add(product);

                orderItems.add(new OrderItem(quantity, product.getPrice(), product));
            }
        }
        // Update products from the cart
        productRepository.saveAll(productsToSave);

        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        // Create order with orderItems
        Order order = Order.builder()
                .user(currentUser)
                .items(orderItems)
                .build();
        orderRepository.save(order);

        // Flush repos
        orderRepository.flush();
        productRepository.flush();

        // Clear the cart
        clearProducts();
    }
}
