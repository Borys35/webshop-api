package io.borys.webshop.auth;

import io.borys.webshop.user.LoginUserDto;
import io.borys.webshop.user.RegisterUserDto;
import io.borys.webshop.user.User;
import io.borys.webshop.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;
import java.util.Optional;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    RestClient client;
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        client = RestClient.create("http://localhost:" + port);
    }

    @Test
    void shouldRegisterNewUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("test@test.com");
        registerUserDto.setPassword("password");
        registerUserDto.setFirstName("Test");
        registerUserDto.setLastName("Surname");

        ResponseEntity<User> response = client.post()
                .uri("auth/register")
                .body(registerUserDto)
                .retrieve()
                .toEntity(User.class);

        assert response.getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(response.getBody()).getEmail().equals(registerUserDto.getEmail());

        Optional<User> user = userRepository.findByEmail("test@test.com");

        assert user.isPresent();
    }

    @Test
    void shouldLoginNewUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("test@test.com");
        registerUserDto.setPassword("password");
        registerUserDto.setFirstName("Test");
        registerUserDto.setLastName("Surname");

        ResponseEntity<User> registerResponse = client.post()
                .uri("auth/register")
                .body(registerUserDto)
                .retrieve()
                .toEntity(User.class);

        assert registerResponse.getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(registerResponse.getBody()).getEmail().equals(registerUserDto.getEmail());

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("test@test.com");
        loginUserDto.setPassword("password");

        ResponseEntity<LoginResponse> response = client.post()
                .uri("auth/login")
                .body(loginUserDto)
                .retrieve()
                .toEntity(LoginResponse.class);

        assert response.getStatusCode().is2xxSuccessful();
        assert !Objects.requireNonNull(response.getBody()).getToken().isEmpty();
        assert response.getBody().getExpiresIn() > 0;
    }
}
