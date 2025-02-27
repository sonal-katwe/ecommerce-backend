# E-Commerce Backend

This is the backend for an e-commerce platform built using Spring Boot. It provides APIs for user authentication, product management, order processing, and payment integration.

## Tech Stack
- **Spring Boot** - Backend framework
- **Spring Data JPA** - Database interaction
- **Spring Security & JWT** - Authentication & authorization
- **MySQL** - Database
- **Bcrypto** - Password hashing
- **Payment Gateway Integration**

## Features
- User authentication (registration, login, JWT-based security)
- Product management (add, update, delete, fetch products)
- Cart and order management
- Payment processing with a payment gateway

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo-link.git
   cd ecommerce-backend
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
- **Authentication**
  - `POST /api/auth/register` - Register a new user
  - `POST /api/auth/login` - Login and get JWT token
- **Products**
  - `GET /api/products` - Get all products
  - `POST /api/products/add` - Add a product
- **Orders**
  - `POST /api/orders/order` - Place an order
  - `GET /api/orders/{id}` - Get order details

## Future Enhancements
- Implement order tracking
- Add recommendation system
- Improve search functionality

