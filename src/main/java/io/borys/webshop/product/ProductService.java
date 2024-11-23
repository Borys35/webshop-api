package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findOne(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id \"" + id + "\" not found"));
    }

    public Product findBySlug(String slug) {
        return productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product with slug \"" + slug + "\" not found"));
    }

    public Page<Product> findAllByBrand(Brand brand, Pageable pageable) {
        return productRepository.findAllByBrand(brand, pageable);
    }

    public Page<Product> findAllByCategory(Category category, Pageable pageable) {
        return productRepository.findAllByCategory(category, pageable);
    }
}
