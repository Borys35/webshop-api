package io.borys.webshop;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.brand.BrandRepository;
import io.borys.webshop.category.Category;
import io.borys.webshop.category.CategoryRepository;
import io.borys.webshop.product.Currency;
import io.borys.webshop.product.Price;
import io.borys.webshop.product.Product;
import io.borys.webshop.product.ProductRepository;
import io.borys.webshop.role.Role;
import io.borys.webshop.role.RoleRepository;
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
    public CommandLineRunner init(BrandRepository brandRepository, CategoryRepository categoryRepository, ProductRepository productRepository, RoleRepository roleRepository) {
        return args -> {
            roleRepository.saveAll(List.of(
                    new Role("ADMIN"),
                    new Role("USER")
            ));

            brandRepository.saveAll(List.of(
                    new Brand("Sennheiser"),
                    new Brand("Sony"),
                    new Brand("Apple"),
                    new Brand("Dell")
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
                    ),
                    new Product(
                            "Sony WH-1000XM5",
                            "sony-wh-1000xm5",
                            "Industry-leading noise-canceling headphones with premium sound quality and extended battery life.",
                            brands.get(1),
                            categories.get(0),
                            new Price(Currency.USD, 399.99),
                            15,
                            List.of("sony-wh-1000xm5-1.jpg", "sony-wh-1000xm5-2.jpg", "sony-wh-1000xm5-3.jpg"),
                            Map.of("Battery Life", "30 hours",
                                    "Noise Cancellation", "Industry-leading",
                                    "Bluetooth", "5.2",
                                    "Weight", "254g",
                                    "Frequency Response", "4 Hz - 40 kHz")
                    ),
                    new Product(
                            "Apple iPhone 15 Pro",
                            "apple-iphone-15-pro",
                            "The latest Apple smartphone with an A17 Bionic chip, superior camera system, and Titanium build.",
                            brands.get(2),
                            categories.get(1),
                            new Price(Currency.USD, 1199.99),
                            10,
                            List.of("iphone-15-pro-1.jpg", "iphone-15-pro-2.jpg", "iphone-15-pro-3.jpg"),
                            Map.of("Chipset", "A17 Bionic",
                                    "Camera", "48 MP Triple-lens",
                                    "Storage", "128 GB to 1 TB",
                                    "Display", "6.1-inch Super Retina XDR",
                                    "Weight", "187g")
                    ),
                    new Product(
                            "Dell XPS 15",
                            "dell-xps-15",
                            "High-performance laptop with a stunning 15.6-inch InfinityEdge display and robust build quality.",
                            brands.get(3),
                            categories.get(2),
                            new Price(Currency.USD, 1999.99),
                            5,
                            List.of("dell-xps-15-1.jpg", "dell-xps-15-2.jpg", "dell-xps-15-3.jpg"),
                            Map.of("Processor", "Intel Core i9",
                                    "RAM", "32 GB",
                                    "Storage", "1 TB SSD",
                                    "Graphics", "NVIDIA GeForce RTX 4060",
                                    "Battery Life", "13 hours")
                    ),
                    new Product(
                            "Sony Xperia 1 V",
                            "sony-xperia-1-v",
                            "A flagship smartphone with a 4K HDR OLED display and advanced camera technology.",
                            brands.get(1),
                            categories.get(1),
                            new Price(Currency.USD, 1299.99),
                            8,
                            List.of("sony-xperia-1-v-1.jpg", "sony-xperia-1-v-2.jpg", "sony-xperia-1-v-3.jpg"),
                            Map.of("Display", "6.5-inch 4K HDR OLED",
                                    "Chipset", "Snapdragon 8 Gen 2",
                                    "Camera", "12 MP Triple-lens",
                                    "Battery", "5000 mAh",
                                    "Weight", "185g")
                    ),
                    new Product(
                            "MacBook Pro 16-inch",
                            "macbook-pro-16",
                            "A powerhouse laptop for professionals with M3 Max chip and incredible battery life.",
                            brands.get(2),
                            categories.get(2),
                            new Price(Currency.USD, 3299.99),
                            7,
                            List.of("macbook-pro-16-1.jpg", "macbook-pro-16-2.jpg", "macbook-pro-16-3.jpg"),
                            Map.of("Processor", "Apple M3 Max",
                                    "RAM", "64 GB",
                                    "Storage", "2 TB SSD",
                                    "Display", "16.2-inch Liquid Retina XDR",
                                    "Battery Life", "21 hours")
                    )
            ));

            log.info("Products fetched: {}", productRepository.findAll(Pageable.unpaged()).getContent().size());
        };
    }
}
