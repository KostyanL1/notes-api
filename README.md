# Note API

> A REST API pet project showcasing backend development practices with Spring Boot

## Overview

Note API is a pet project built to demonstrate REST API development, security best practices, and clean architecture patterns. It implements a complete note management system with sophisticated authentication, authorization, and session management.

This project goes beyond basic CRUD operations to showcase production-ready patterns including JWT-based stateless authentication, refresh token rotation, session tracking, and comprehensive error handling.

## Key Features

** Authentication**
  JWT-based stateless authentication
    Refresh token rotation mechanism
    Database-backed session management
    Secure logout with token revocation

  ** Note Management**
    Create, read, update, and delete notes
    User-specific note ownership
    Role-based access control (User/Admin)
    Admin access to all notes

** Security & Validation**
    Spring Security integration
    DTO-based input/output validation
    Centralized exception handling
    Predictive error handling

** Clean Architecture**
    Clear separation of concerns (Controller/Service/Repository)
    Layered architecture pattern
    Entity-DTO separation
    RESTful API design

## Technology Stack

### Core Technologies
**Java 17+** - Programming language
  **Spring Boot 3** - Application framework
  **Maven** - Build and dependency management

### Spring Ecosystem
  **Spring Security** - Authentication and authorization
  **Spring Data JPA** - Data persistence layer
  **Hibernate** - ORM implementation
  **Spring Web** - REST API development

### Database
  **PostgreSQL 15** - Primary database
  **Docker** - Database containerization

### Security
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing

## Getting Started

### Prerequisites

Java 17 or higher
Maven 3.6+
Docker (for PostgreSQL)

### 1. Start PostgreSQL Database

Run PostgreSQL in a Docker container:

```bash
docker run --name note-postgres \
  -e POSTGRES_DB=note_api \
  -e POSTGRES_USER=note_user \
  -e POSTGRES_PASSWORD=note_pass \
  -p 5432:5432 \
  -d postgres:15
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```properties
# Database Configuration
POSTGRES_DB=note_api
POSTGRES_USER=note_user
POSTGRES_PASSWORD=note_pass

# Spring DataSource
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/note_api
SPRING_DATASOURCE_USERNAME=note_user
SPRING_DATASOURCE_PASSWORD=note_pass
```

### 3. Configure Application Properties

Update `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/note_api
spring.datasource.username=note_user
spring.datasource.password=note_pass
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
jwt.secret=CHANGE_ME_TO_A_LONG_RANDOM_SECRET
jwt.access.ttlSeconds=900
jwt.refresh.ttlSeconds=2592000
```

### 4. Run the Application

Using Maven Wrapper:
```bash
./mvnw spring-boot:run
```

Or using Maven:
```bash
mvn clean package
java -jar target/*.jar
```

The API will be available at `http://localhost:8080`

## Authentication Flow

1. **Register** - Create a new user account
2. **Login** - Receive access token (short-lived) and refresh token (long-lived)
3. **Access Protected Endpoints** - Use access token in Authorization header
4. **Refresh Token** - When access token expires, use refresh token to get new tokens
5. **Logout** - Revoke refresh token and invalidate session

### Token Rotation

The project implements refresh token rotation for enhanced security:
When refreshing, the old refresh token is revoked
A new refresh token is issued
Prevents replay attacks with expired refresh tokens

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and receive tokens
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout and revoke session

### Notes
- `GET /api/notes` - Get all user's notes
- `GET /api/notes/{id}` - Get specific note
- `POST /api/notes` - Create new note
- `PUT /api/notes/{id}` - Update note
- `DELETE /api/notes/{id}` - Delete note

### Admin
- `GET /api/admin/notes` - Get all notes (admin only)

## Project Structure

```
src/main/java/
├── controller/     # REST endpoints (thin, HTTP-focused)
├── service/        # Business logic layer
├── repository/     # Data access layer
├── entity/         # JPA entities
├── dto/            # Data Transfer Objects
├── security/       # Security configuration and JWT handling
├── exception/      # Global exception handling
└── config/         # Application configuration
```

## Learning Objectives

This pet project demonstrates:

RESTful API design principles
Layered architecture implementation
Spring Security in stateless mode
JWT token management and rotation
Database session tracking
DTO pattern for API contracts
Global exception handling
Input validation
Role-based access control
Clean code practices

## Security Features

  **Stateless Authentication** - No server-side session storage for access tokens
  **Token Rotation** - Refresh tokens are rotated on each use
  **Session Management** - Refresh tokens stored in database for tracking
  **Authorization** - Users can only access their own notes
  **Admin Role** - Privileged access to all resources
  **Password Hashing** - BCrypt for secure password storage

## Testing the API

1. Register a new user
2. Login to receive tokens
3. Create notes using the access token
4. Test token refresh when access token expires
5. Verify ownership enforcement (users can't access others' notes)
6. Test admin privileges
7. Test logout and session revocation

## License

This is a pet project for educational and portfolio purposes.


**Note**: This is a pet project designed to showcase technical skills in REST API development, security implementation, and clean architecture patterns. It serves as a practical example of how to build production-ready backend services.
