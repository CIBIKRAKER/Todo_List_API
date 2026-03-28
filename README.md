# Todo List API

A REST API for managing personal todo tasks with JWT-based authentication. Built with Spring Boot and PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot 4.0.3
- Spring Security 6
- JWT (jjwt 0.11.5)
- PostgreSQL 16
- Docker
- Gradle Kotlin DSL

## Getting Started

### Prerequisites

- Java 21
- Docker

### 1. Clone the repository
```bash
git clone https://github.com/cibikraker/Todo-List-API.git
cd Todo-List-API
```

### 2. Start the database
```bash
docker run --name todoapi-db \
  -e POSTGRES_DB=tododb \
  -e POSTGRES_USER=youruser \
  -e POSTGRES_PASSWORD=yourpassword \
  -p 5432:5432 \
  -d postgres:16
```

### 3. Configure the application

Copy the example properties file and fill in your values:
```bash
cp application.properties.example src/main/resources/application.properties
```

### 4. Run the application
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

## Authentication

This API uses JWT authentication. To access protected endpoints you need to:

1. Register a user at `POST /auth/register`
2. Login at `POST /auth/login` to get a token
3. Include the token in the `Authorization` header as `Bearer <token>`

## Endpoints

### Auth

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /auth/register | No | Register a new user |
| POST | /auth/login | No | Login and receive a JWT token |

### Todos

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | /todos | Yes | Get all your tasks (paginated) |
| GET | /todos/{id} | Yes | Get a task by ID |
| POST | /todos | Yes | Create a new task |
| PUT | /todos/{id} | Yes | Update a task |
| DELETE | /todos/{id} | Yes | Delete a task |

## Example Requests

### Register
```json
POST /auth/register
{
  "username": "john",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login
```json
POST /auth/login
{
  "username": "john",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Create a task
```json
POST /todos
Authorization: Bearer <token>

{
  "name": "Buy groceries",
  "description": "Milk, eggs, bread"
}
```

### Get all tasks
```
GET /todos?page=0&size=10
Authorization: Bearer <token>
```

Response:
```json
{
  "data": [
    {
      "id": 1,
      "name": "Buy groceries",
      "description": "Milk, eggs, bread"
    }
  ],
  "page": 0,
  "limit": 10,
  "total": 1
}
```

### Update a task
```json
PUT /todos/1
Authorization: Bearer <token>

{
  "name": "Buy groceries",
  "description": "Milk, eggs, bread, butter"
}
```

### Delete a task
```
DELETE /todos/1
Authorization: Bearer <token>
```

## Security

- Passwords are hashed using BCrypt
- JWT tokens expire after 93 minutes
- Users can only access and modify their own tasks
- Secrets are managed via `application.properties` which is excluded from version control
