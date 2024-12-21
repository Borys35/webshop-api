# E-commerce System REST API

This project is a fully functional REST API for an e-commerce platform. It includes user authentication, product browsing, cart management, and order processing. Built with modern technologies, it demonstrates robust backend development and secure design principles.

## API Routes

### Products
- **GET /products** - Retrieve all products with optional pagination and filtering.
- **GET /products/:id** - Retrieve details of a specific product by ID.
- **POST /products** - Add a new product (admin-only route).
- **PUT /products/:id** - Update a specific product by ID (admin-only route).
- **DELETE /products/:id** - Delete a specific product by ID (admin-only route).

### Authentication
- **POST /auth/register** - Register a new user.
- **POST /auth/login** - Log in an existing user and retrieve a JWT token.
- **GET /auth/test** - Test endpoint.

### Orders
- **GET /orders** - Retrieve all orders for the authenticated user.
- **POST /orders** - Create a new order.

### Cart
- **GET /cart** - Retrieve the cart for the authenticated user.
- **POST /cart** - Add an item to the cart for the authenticated user.
- **DELETE /cart/:id** - Remove an item from the cart by ID for the authenticated user.

### Users
- **GET /users** - Retrieve all users (admin-only route).
- **GET /users/:id** - Retrieve details of a specific user by ID (admin-only route).
- **DELETE /users/:id** - Delete a user by ID (admin-only route).

## Key Features
- **Role-Based Access Control**: Differentiates between ADMIN and USER roles to secure sensitive endpoints.
- **JWT-Based Authentication**: Implements stateless authentication with token expiration.
- **Pagination and Filtering**: Enhances the usability of product browsing.
- **Secure Design Principles**: Focus on ensuring data integrity and protecting user information.

## Testing
Testing is a critical part of this project to ensure reliability and maintainability. It focuses on integration testing, using tools like Testcontainers to validate the entire application flow with real dependencies.

## Technologies Used
- **Spring Boot**: For building the REST API.
- **JPA (Java Persistence API)**: For database interactions.
- **JWT (JSON Web Tokens)**: For secure and stateless authentication.
- **Testcontainers**: For integration testing.

This project demonstrates my ability to design scalable REST APIs, implement secure authentication systems, and apply modern software development practices.

