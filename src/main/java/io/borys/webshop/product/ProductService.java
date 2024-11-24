package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductDto);
    }

    public ProductDto findOne(Long id) {
        return productMapper.toProductDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id \"" + id + "\" not found")));
    }

    public ProductDto findBySlug(String slug) {
        return productMapper.toProductDto(productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product with slug \"" + slug + "\" not found")));
    }

    public Page<ProductDto> findAllByBrand(Brand brand, Pageable pageable) {
        return productRepository.findAllByBrand(brand, pageable).map(productMapper::toProductDto);
    }

    public Page<ProductDto> findAllByCategory(Category category, Pageable pageable) {
        return productRepository.findAllByCategory(category, pageable).map(productMapper::toProductDto);
    }
}
