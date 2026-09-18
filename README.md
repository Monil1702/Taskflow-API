# TaskFlow API

A production-style REST API for collaborative task management, built with Java 17 and Spring Boot. It supports projects, task assignment, status workflows, validation, API-key authentication, persistence, tests, and containerized deployment.

## Features

- Create projects and tasks through versioned REST endpoints
- Assign tasks, set priorities and due dates, and update workflow status
- Validate request bodies before they reach the service layer
- Return consistent RFC 7807-style error responses
- Protect application endpoints with an `X-API-Key` header
- Run locally with H2 or in Docker with PostgreSQL
- Expose OpenAPI documentation at `/swagger-ui.html`

## API overview

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/actuator/health` | Public health check |
| `POST` | `/api/v1/projects` | Create a project |
| `GET` | `/api/v1/projects` | List projects |
| `POST` | `/api/v1/tasks` | Create a task |
| `GET` | `/api/v1/tasks?projectId=1` | List tasks, optionally by project |
| `PATCH` | `/api/v1/tasks/{id}/status` | Change task status |

## Quick start

Prerequisites: Java 17 and Maven 3.9+.

```bash
export TASKFLOW_API_KEY=local-development-key
mvn spring-boot:run
```

Example:

```bash
curl -X POST http://localhost:8080/api/v1/projects \
  -H 'Content-Type: application/json' \
  -H 'X-API-Key: local-development-key' \
  -d '{"name":"Portfolio Launch","description":"Ship the new portfolio"}'
```

## Docker

```bash
cp .env.example .env
docker compose up --build
```

## Tests

```bash
mvn test
```

Tests cover successful requests, authentication rejection, invalid payloads, missing resources, and status transitions.

## Design choices

- Controllers handle HTTP concerns only; services own business rules.
- DTOs prevent persistence entities from becoming a public API contract.
- Database constraints complement request validation.
- The default H2 profile makes evaluation fast; the Docker profile uses PostgreSQL.

## License

MIT
