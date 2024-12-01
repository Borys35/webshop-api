package io.borys.webshop.cart;

import io.borys.webshop.product.ProductDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@SessionScope
@Transactional
public class CartService {

    private final ConcurrentMap<ProductDto, Integer> products;

    public CartService() {
        products = new ConcurrentHashMap<>();
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
}
