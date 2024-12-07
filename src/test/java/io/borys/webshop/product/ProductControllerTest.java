package io.borys.webshop.product;

import io.borys.webshop.lib.RestResponsePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    private static final Logger log = LoggerFactory.getLogger(ProductControllerTest.class);
    RestClient client;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        client = RestClient.create("http://localhost:" + port);
    }

    @Test
    void shouldFindAllProducts() {
        RestResponsePage<ProductDto> products = client.get()
                .uri("products?size=20&page=0")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert products != null;
        assert !products.isEmpty();
    }

    @Test
    void shouldFindProductById() {
        ProductDto product = client.get()
                .uri("products/1")
                .retrieve()
                .body(ProductDto.class);

        assert product != null;
        assert product.getProductId() == 1;
        assert Objects.equals(product.getSlug(), "sennheiser-momentum-4");
    }

    @Test
    void shouldFindProductBySlug() {
        ProductDto product = client.get()
                .uri("products/slug/sennheiser-momentum-4")
                .retrieve()
                .body(ProductDto.class);

        assert product != null;
        assert Objects.equals(product.getSlug(), "sennheiser-momentum-4");
    }

    @Test
    void shouldFindProductByBrand() {
        RestResponsePage<ProductDto> products = client.get()
                .uri("products/brand/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert products != null;
        assert !products.isEmpty();
        products.forEach(product -> {
            assert product.getBrandId() == 1;
        });
    }

    @Test
    void shouldFindProductByCategory() {
        RestResponsePage<ProductDto> products = client.get()
                .uri("products/category/1")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert products != null;
        assert !products.isEmpty();
        products.forEach(product -> {
            assert product.getCategoryId() == 1;
        });
    }
}
