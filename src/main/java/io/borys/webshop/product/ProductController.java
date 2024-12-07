package io.borys.webshop.product;

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
    public Page<ProductDto> getProducts(final Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable final Long id) {
        return productService.findOne(id);
    }

    @GetMapping("/slug/{slug}")
    public ProductDto getProductBySlug(@PathVariable("slug") String slug) {
        return productService.findBySlug(slug);
    }

    @GetMapping("/brand/{brandId}")
    public Page<ProductDto> getProductsByBrand(@PathVariable Long brandId, Pageable pageable) {
        return productService.findAllByBrand(brandId, pageable);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductDto> getProductsByCategory(@PathVariable Long categoryId, Pageable pageable) {
        return productService.findAllByCategory(categoryId, pageable);
    }
}
