# Spring Backend Template

This is a basic Spring backend template designed to provide foundational features for web applications. The project is structured around the Spring Boot framework and offers functionalities such as user authentication, JWT token handling, and department management.

## Features

1. **User Authentication**: The application provides endpoints for user registration and login. It uses JWT (JSON Web Tokens) for authentication and authorization.
2. **Error Handling**: A global exception handler is in place to catch and respond to various exceptions throughout the application.
3. **Department Management**: The application includes basic CRUD operations for managing departments.
4. **User Management**: Users can be created, retrieved, and managed. The system also supports different user roles and statuses.
5. **Configuration**: The project is equipped with configurations for JWT authentication, security, and other application-specific settings.

## How to Use

### Prerequisites

- Docker and Docker Compose installed on your machine.

### Running the Application Locally

1. **Start the Services**: Navigate to the root directory of the project and run the following command:
   ```
   docker-compose up
   ```
   This will start the MariaDB database and the API service. The API will be accessible on port `8080`, and the MariaDB database will be available on port `3306`.

2. **User Registration and Login**: Use the endpoints provided in `AuthenticationController.java` for user registration and login. Upon successful authentication, a JWT token will be returned.

3. **Managing Departments**: Use the endpoints related to the `Department` entity for creating, retrieving, updating, or deleting departments.

4. **Managing Users**: The `UserController.java` provides endpoints for user management. You can create, retrieve, or manage users using these endpoints.

5. **Error Handling**: In case of exceptions, the `GlobalExceptionHandler.java` will catch and provide appropriate error responses.

## Configuration

- **JWT Configuration**: The application uses JWT for authentication. The configurations related to JWT can be found in `JwtAuthenticationFilter.java`, `JwtService.java`, and `SecurityConfiguration.java`.
- **Security Configuration**: The security configurations, including the authentication entry point and security constraints, are located in `SecurityConfiguration.java`.
## Continuous Integration (CI) Workflow

### Workflow Name: Java CI with Maven and MariaDB

This workflow is designed to automate the build and test process for the project using Maven and MariaDB. It is triggered on every `push` and `pull_request` event to the `main` branch.

#### Workflow Steps:

1. **Environment Setup**: The workflow runs on the latest version of Ubuntu.

2. **MariaDB Service**: A MariaDB service is started using the `mariadb:10.5` image. The database is configured with the root password as `secret` and a database named `test_db`. The service is exposed on port `3306`.

3. **Checkout Code**: The code from the repository is checked out using the `actions/checkout@v4` action.

4. **Setup JDK**: The Java Development Kit (JDK) 17 is set up using the `actions/setup-java@v3` action. The distribution used is `temurin`, and Maven dependencies are cached for faster subsequent builds.

5. **Build and Test**: The project is built and tested using Maven. The database connection for the tests is configured to connect to the MariaDB service started earlier.

#### Configuration:

The workflow configuration is as follows:

  ```yaml
name: Java CI with Maven and MariaDB

on:
  push:
    branches: ["main"]
  pull_request:
    branches: ["main"]

jobs:
  build:
    runs-on: ubuntu-latest

    services:
      mariadb:
        image: mariadb:10.5
        env:
          MYSQL_ROOT_PASSWORD: secret
          MYSQL_DATABASE: test_db
        ports:
          - 3306:3306
        options: --health-cmd="mysqladmin ping" --health-interval=10s --health-timeout=5s --health-retries=5

    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: "17"
          distribution: "temurin"
          cache: maven

      - name: Build and Test with Maven
        run: mvn -B test --file pom.xml -Dspring.datasource.url=jdbc:mariadb://localhost:3306/test_db -Dspring.datasource.username=root -Dspring.datasource.password=secret
  ```
