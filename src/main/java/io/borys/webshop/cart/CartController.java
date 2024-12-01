package io.borys.webshop.cart;

import io.borys.webshop.product.ProductDto;
import io.borys.webshop.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final ProductService productService;

    public CartController(final ProductService productService) {
        this.cartService = new CartService();
        this.productService = productService;
    }

    @GetMapping()
    public Map<ProductDto, Integer> getProducts() {
        return cartService.getProducts();
    }

    @PutMapping("/add/{productId}")
    public void addProduct(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity) throws RuntimeException {
        if (quantity <= 0) {
            quantity = 1;
        }
        ProductDto productDto = productService.findOne(productId);
        cartService.addProduct(productDto, quantity);
    }

    @PutMapping("/update/{productId}")
    public void updateProductQuantity(@PathVariable Long productId, @RequestParam(required = true) int quantity) throws RuntimeException {
        ProductDto productDto = productService.findOne(productId);
        cartService.changeQuantity(productDto, quantity);
    }

    @PutMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) throws RuntimeException {
        ProductDto productDto = productService.findOne(productId);
        cartService.removeProduct(productDto);
    }

    @DeleteMapping("/clear")
    public void clear() {
        cartService.clearProducts();
    }
}
