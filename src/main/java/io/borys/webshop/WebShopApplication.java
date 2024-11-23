package io.borys.webshop;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.brand.BrandRepository;
import io.borys.webshop.category.Category;
import io.borys.webshop.category.CategoryRepository;
import io.borys.webshop.product.Currency;
import io.borys.webshop.product.Price;
import io.borys.webshop.product.Product;
import io.borys.webshop.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Map;

// This is starter project URL from start.spring.io
// https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.5&packaging=jar&jvmVersion=23&groupId=io.borys&artifactId=webshop&name=webshop&description=Fully-equiped%20Webshop%20API%20built%20w%2F%20Java%2C%20Spring%20Boot%2C%20Paypal%2C%20Auth%2C%20and%20more&packageName=io.borys.webshop&dependencies=lombok,web,devtools,docker-compose,testcontainers,data-jdbc,data-jpa,h2,postgresql,jdbc

// TODO: Caching
// TODO: Validating request body

@SpringBootApplication
@EnableJpaAuditing
public class WebShopApplication {

    private static final Logger log = LoggerFactory.getLogger(WebShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(BrandRepository brandRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            brandRepository.saveAll(List.of(
                    new Brand("Sennheiser"),
                    new Brand("Sony"),
                    new Brand("Apple")
            ));

            categoryRepository.saveAll(List.of(
                    new Category("Headphones"),
                    new Category("Smartphones"),
                    new Category("Laptops")
            ));


            List<Brand> brands = brandRepository.findAll();
            List<Category> categories = categoryRepository.findAll();

            log.info("Added Brands: {}", brands.stream().map(Brand::getName).toList());
            log.info("Added Product: {}", categories.stream().map(Category::getName).toList());

            productRepository.saveAll(List.of(
                    new Product(
                            "Sennheiser Momentum 4",
                            "sennheiser-momentum-4",
                            "Premium wireless headphones with adaptive noise cancellation, offering high-quality sound and comfort.",
                            brands.get(0),
                            categories.get(0),
                            new Price(Currency.USD, 349.99),
                            20,
                            List.of("image1.jpg", "image2.jpg", "image3.jpg"), // Add actual image URLs
                            Map.of("Battery Life", "60 hours",
                                    "Noise Cancellation", "Adaptive",
                                    "Bluetooth", "5.2",
                                    "Weight", "293g",
                                    "Frequency Response", "6 Hz - 22 kHz")
                    )
            ));

            log.info("Added Product: {}", productRepository.findBySlug("sennheiser-momentum-4").get().getName());

            log.info("Products fetched: {}", productRepository.findAll(Pageable.unpaged()).getContent().size());
        };
    }
}
