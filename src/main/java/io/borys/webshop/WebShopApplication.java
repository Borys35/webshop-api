package io.borys.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is starter project URL from start.spring.io
// https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.5&packaging=jar&jvmVersion=23&groupId=io.borys&artifactId=webshop&name=webshop&description=Fully-equiped%20Webshop%20API%20built%20w%2F%20Java%2C%20Spring%20Boot%2C%20Paypal%2C%20Auth%2C%20and%20more&packageName=io.borys.webshop&dependencies=lombok,web,devtools,docker-compose,testcontainers,data-jdbc,data-jpa,h2,postgresql,jdbc

@SpringBootApplication
public class WebShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
	}

}
