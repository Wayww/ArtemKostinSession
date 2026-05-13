# ArtemKostinSession

Simple Spring Boot 3.2 student project for online course platform.

## Stack

- Java 17
- Spring Boot 3.2
- Maven
- PostgreSQL
- Swagger UI
- Docker
- MailHog

## Run With Docker

```bash
docker compose up --build
```

## Run Local

1. Start PostgreSQL
2. Start MailHog on `localhost:1025`
3. Run project:

```bash
mvn spring-boot:run
```

## Useful Links

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Actuator health: `http://localhost:8080/actuator/health`
- MailHog UI: `http://localhost:8025`

## Main Endpoints

### Auth

- `POST /api/auth/register`
- `POST /api/auth/login`

### Users

- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

### Courses

- `GET /api/courses`
- `GET /api/courses/{id}`
- `POST /api/courses`
- `PUT /api/courses/{id}`
- `DELETE /api/courses/{id}`

### Lessons

- `GET /api/lessons`
- `GET /api/lessons/{id}`
- `GET /api/courses/{courseId}/lessons`
- `POST /api/lessons`
- `PUT /api/lessons/{id}`
- `DELETE /api/lessons/{id}`

### Enrollment And Review

- `POST /api/enrollments`
- `GET /api/users/{userId}/enrollments`
- `POST /api/reviews`
- `GET /api/courses/{courseId}/reviews`

### Files

- `POST /api/files/upload`
- `GET /api/files/{fileId}/download`
