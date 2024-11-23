package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    Page<Product> findAllByBrand(Brand brand, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);
}
