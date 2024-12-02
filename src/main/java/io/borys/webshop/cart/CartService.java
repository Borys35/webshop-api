package io.borys.webshop.cart;

import io.borys.webshop.product.Product;
import io.borys.webshop.product.ProductDto;
import io.borys.webshop.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@SessionScope
@Transactional
public class CartService {

    private final ConcurrentMap<ProductDto, Integer> products;
    private final ProductRepository productRepository;

    public CartService(final ProductRepository productRepository) {
        products = new ConcurrentHashMap<>();
        this.productRepository = productRepository;
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
        // TODO: Create order with productsToSave from the cart

        // Check quantity and stock. Deduct if sufficient amount. Throw if not.
        List<Product> productsToSave = new ArrayList<>();
        for (Map.Entry<ProductDto, Integer> entry : getProducts().entrySet()) {
            ProductDto productDto = entry.getKey();
            Integer quantity = entry.getValue();

            if (productDto.getStock() >= quantity) {
                Product product = productRepository.findById(productDto.getProductId()).orElseThrow();
                product.setStock(product.getStock() - quantity);
                productsToSave.add(product);
            }
        }
        productRepository.saveAll(productsToSave);

        // Clear the cart
        clearProducts();
    }
}
