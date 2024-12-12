package io.borys.webshop.cart;

import io.borys.webshop.auth.JwtService;
import io.borys.webshop.auth.LoginResponse;
import io.borys.webshop.user.LoginUserDto;
import io.borys.webshop.user.RegisterUserDto;
import io.borys.webshop.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");
    RestClient client;
    @LocalServerPort
    private int port;
    @Autowired
    private CartService cartService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        client = RestClient.create("http://localhost:" + port);
    }

    @Test
    @Order(1)
    void shouldViewCartWhenEmpty() {
        Map<String, Integer> items = client.get()
                .uri("/cart")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert Objects.requireNonNull(items).isEmpty();
    }

    @Test
    @Order(2)
    public void shouldAddTwoProductsToCart() {
        ResponseEntity<Void> response = client
                .put()
                .uri("/cart/add/1?quantity=2")
                .retrieve()
                .toBodilessEntity();

        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    @Order(3)
    void shouldViewCartWhenNotEmpty() {
        Map<String, Integer> items = client.get()
                .uri("/cart")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert !Objects.requireNonNull(items).isEmpty();
    }

    @Test
    @Order(4)
    void shouldUpdateCart() {
        ResponseEntity<Void> response = client
                .put()
                .uri("/cart/update/1?quantity=10")
                .retrieve()
                .toBodilessEntity();

        assert response.getStatusCode().is2xxSuccessful();

        Map<String, Integer> items = client.get()
                .uri("/cart")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert !Objects.requireNonNull(items).isEmpty();
        assert items.containsValue(10);
    }

    @Test
    @Order(5)
    void shouldClearCart() {
        ResponseEntity<Void> response = client
                .delete()
                .uri("/cart/clear")
                .retrieve()
                .toBodilessEntity();

        assert response.getStatusCode().is2xxSuccessful();

        Map<String, Integer> items = client.get()
                .uri("/cart")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        assert Objects.requireNonNull(items).isEmpty();
    }

    @Test
    @Order(6)
    void shouldProceedToCheckout() {
        client
                .put()
                .uri("/cart/add/1?quantity=2")
                .retrieve();

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("test@test.com");
        registerUserDto.setPassword("password");
        registerUserDto.setFirstName("Test");
        registerUserDto.setLastName("Surname");

        User user = client.post()
                .uri("auth/register")
                .body(registerUserDto)
                .retrieve()
                .body(User.class);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@test.com");
        loginUserDto.setPassword("password");

        LoginResponse loginResponse = client.post()
                .uri("auth/login")
                .body(loginUserDto)
                .retrieve()
                .body(LoginResponse.class);

        assert loginResponse != null;

        ResponseEntity<Void> response = client
                .post()
                .uri("/cart/checkout")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .retrieve()
                .toBodilessEntity();

        assert response.getStatusCode().is2xxSuccessful();
    }
}
