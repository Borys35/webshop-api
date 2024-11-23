package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getProducts(final Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable final Long id) {
        return productService.findOne(id);
    }

    @GetMapping("/slug/{slug}")
    public Product getProduct(@PathVariable("slug") String slug) {
        return productService.findBySlug(slug);
    }

    @GetMapping("/brand/{brandId}")
    public Page<Product> getProductsByBrand(@PathVariable Long brandId, Brand brand, Pageable pageable) {
        return productService.findAllByBrand(brand, pageable);
    }

    @GetMapping("/category/{categoryId}")
    public Page<Product> getProductsByCategory(@PathVariable Long categoryId, Category category, Pageable pageable) {
        return productService.findAllByCategory(category, pageable);
    }
}
