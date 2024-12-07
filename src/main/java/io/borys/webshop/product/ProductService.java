package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.brand.BrandRepository;
import io.borys.webshop.category.Category;
import io.borys.webshop.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductDto);
    }

    public ProductDto findOne(Long id) throws RuntimeException {
        return productMapper.toProductDto(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID \"" + id + "\" not found")));
    }

    public ProductDto findBySlug(String slug) {
        return productMapper.toProductDto(productRepository.findBySlug(slug)
                .orElseThrow(() -> new ProductNotFoundException("Product with slug \"" + slug + "\" not found")));
    }

    public Page<ProductDto> findAllByBrand(Long brandId, Pageable pageable) {
        Brand brand = brandRepository.findById(brandId).orElseThrow();
        return productRepository.findAllByBrand(brand, pageable).map(productMapper::toProductDto);
    }

    public Page<ProductDto> findAllByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return productRepository.findAllByCategory(category, pageable).map(productMapper::toProductDto);
    }
}
