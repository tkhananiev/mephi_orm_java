# ORM-фреймворки для Java --- Итоговый проект (MEPHI)

Веб-приложение (учебная платформа), разработанное в рамках итогового
проекта по дисциплине\
**«ORM-фреймворки для Java» (Spring Boot + Hibernate/JPA +
PostgreSQL)**.

------------------------------------------------------------------------

## Технологический стек

-   Java 17\
-   Spring Boot 3.x\
-   Hibernate / JPA\
-   PostgreSQL\
-   Maven\
-   Docker / Docker Compose\
-   Testcontainers\
-   GitHub Actions (CI)

------------------------------------------------------------------------

## Архитектура

Проект построен по layered-архитектуре:

Controller → Service → Repository → Database

Структура:

src/main/java/com/example/ormplatform - api --- REST-контроллеры\
- service --- бизнес-логика\
- repository --- Spring Data JPA\
- entity --- JPA-сущности\
- exception --- обработка ошибок\
- config --- конфигурация

------------------------------------------------------------------------

## Реализованная модель данных

Сущности:

User, Profile, Course, Category, CourseModule, Lesson,\
Enrollment, Assignment, Submission, Quiz, Question,\
AnswerOption, QuizSubmission, CourseReview, Tag

Типы связей:

-   OneToOne (User--Profile)\
-   OneToMany (Course--Module--Lesson)\
-   ManyToMany (Course--Tag)\
-   ManyToMany через сущность (Enrollment)

------------------------------------------------------------------------

## REST API

### Users

POST /api/users\
GET /api/users\
GET /api/users/{id}

### Courses

GET /api/courses\
GET /api/courses/{id}\
GET /api/courses/{id}/structure

### Enrollments

POST /api/enrollments\
DELETE /api/enrollments\
GET /api/enrollments

### Assignments

POST /api/assignments\
POST /api/assignments/submit\
POST /api/assignments/grade\
GET /api/assignments/submissions

### Quizzes

POST /api/quizzes\
POST /api/quizzes/take\
GET /api/quizzes/{id}

------------------------------------------------------------------------

## Локальный запуск

### 1. Поднять PostgreSQL

docker run --name orm-postgres\
-e POSTGRES_DB=orm_db\
-e POSTGRES_USER=postgres\
-e POSTGRES_PASSWORD=postgres\
-p 5432:5432\
-d postgres:15

### 2. Запуск приложения

mvn clean spring-boot:run

Приложение будет доступно по адресу:

http://localhost:8080

------------------------------------------------------------------------

## Запуск через Docker Compose

docker compose up --build

API будет доступно:

http://localhost:8080/api/courses

------------------------------------------------------------------------

## Тестирование

mvn test

(требуется установленный Docker для Testcontainers)

------------------------------------------------------------------------

## CI

В проекте настроен GitHub Actions:

-   сборка Maven\
-   запуск тестов\
-   проверка Docker build

------------------------------------------------------------------------

## Автор

Timur Khananiev\
MEPHI --- Итоговый проект по ORM-фреймворкам для Java.
