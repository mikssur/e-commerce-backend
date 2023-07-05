# E-commerce Backend

This is the readme file for the E-Commerce Backend project built with Java Spring Boot and PostgreSQL. The project utilizes Google OAuth2 for user authentication and Cloudinary for image uploads. It also includes security features for path authorization and role-based access control to ensure secure access to specific paths.

## Authentication and Authorization

The project implements Google OAuth2 for user authentication. Users can log in to the application using their Google accounts, which provides a secure and convenient way to access the protected endpoints.

Additionally, the project includes security measures for path authorization and role-based access control. This ensures that only authenticated users with the appropriate roles can access certain paths, providing granular control over the API endpoints.

## API Endpoints

The backend project exposes the following API endpoints:

api/v1/auth
POST /api/v1/auth/login: Authenticate user using Google OAuth2.
POST /api/v1/auth/logout: Log out the authenticated user.
api/v1/products
GET /api/v1/products: Get a list of products.
GET /api/v1/products/{productId}: Get details of a specific product.
POST /api/v1/products: Create a new product.
PUT /api/v1/products/{productId}: Update an existing product.
DELETE /api/v1/products/{productId}: Delete a product.
api/v1/product-categories
GET /api/v1/product-categories: Get a list of product categories.
GET /api/v1/product-categories/{categoryId}: Get details of a specific product category.
POST /api/v1/product-categories: Create a new product category.
PUT /api/v1/product-categories/{categoryId}: Update an existing product category.
DELETE /api/v1/product-categories/{categoryId}: Delete a product category.
api/v1/orders
GET /api/v1/orders: Get a list of orders.
GET /api/v1/orders/{orderId}: Get details of a specific order.
POST /api/v1/orders: Create a new order.
PUT /api/v1/orders/{orderId}: Update an existing order.
DELETE /api/v1/orders/{orderId}: Delete an order.
api/v1/users
GET /api/v1/users: Get a list of users.
GET /api/v1/users/{userId}: Get details of a specific user.
POST /api/v1/users: Create a new user.
PUT /api/v1/users/{userId}: Update an existing user.
DELETE /api/v1/users/{userId}: Delete a user.

Please note that the above endpoints represent the basic structure and functionality of the API. You can modify or expand upon them based on your specific requirements.

## Database

The project uses PostgreSQL as the database for storing data. Make sure you have PostgreSQL installed and properly configured in your development environment. The application.properties file contains the necessary database configuration, such as the database URL, username, and password. Adjust these settings to match your database configuration.
