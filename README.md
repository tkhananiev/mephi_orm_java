# ORM-фреймворки для Java — Итоговый проект (PJ)

Учебная платформа на Spring Boot + Hibernate/JPA + PostgreSQL.

## Запуск (dev)

```bash
docker compose up -d
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Параметры подключения:
- DB_URL=jdbc:postgresql://localhost:5432/orm_platform
- DB_USER=orm_user
- DB_PASSWORD=orm_pass

## Тестирование
```bash
mvn test
```
Для тестов нужен запущенный Docker (используется Testcontainers с PostgreSQL).

## Эндпойнты
- Users: POST/GET `/api/users`
- Courses: POST/GET `/api/courses`, GET `/api/courses/{id}/structure`, POST `/api/courses/modules`, POST `/api/courses/lessons`
- Enrollments: POST `/api/enrollments`, DELETE `/api/enrollments`, GET `/api/enrollments`
- Assignments: POST `/api/assignments`, POST `/api/assignments/submit`, POST `/api/assignments/grade`, GET `/api/assignments/submissions`
- Quizzes: POST `/api/quizzes`, POST `/api/quizzes/take`, GET `/api/quizzes/{id}`
