package io.borys.webshop.product;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("borys")
            .withUsername("borys")
            .withPassword("borys");

    static {
        postgreSQLContainer.start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFindAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }
}
