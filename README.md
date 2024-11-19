# Device Hub

**Device Hub** is a RESTful API for managing devices in a system. It includes endpoints to create, retrieve, update, delete, and filter devices by brand. The API is built using Spring Boot and follows best practices for documentation and testing.

---

## Features

- Create, retrieve, update, and delete devices.
- Filter devices by brand.
- Full OpenAPI documentation with Swagger UI.
- Built-in validation for API requests.
- Easily extendable and maintainable codebase.
- Includes integration tests.

---

## API Documentation

The API is documented using **Springdoc OpenAPI**. You can explore the API and test endpoints directly via Swagger UI.

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## Prerequisites

Ensure you have the following installed:
- Java 21 or higher.
- Maven 3.8.0 or higher.
- A MySQL database (or another database, configurable in `application.yaml`).

---

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/dilnei/device-hub.git
cd device-hub
