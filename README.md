# ORM-фреймворки для Java — Итоговый проект (MEPHI)

Веб-приложение (учебная платформа), разработанное в рамках итогового проекта по дисциплине
**«ORM-фреймворки для Java» (Spring Boot + Hibernate/JPA + PostgreSQL)**.

---

## Технологический стек

* Java 17
* Spring Boot 3.x
* Hibernate / JPA
* PostgreSQL
* Maven
* Docker / Docker Compose
* Testcontainers
* GitHub Actions (CI)

---

## Архитектура

Проект построен по layered-архитектуре:

**Controller → Service → Repository → Database**

Структура:

```
src/main/java/com/example/ormplatform
 ├─ api         — REST-контроллеры
 ├─ service     — бизнес-логика
 ├─ repository  — Spring Data JPA
 ├─ entity      — JPA-сущности
 ├─ dto         — DTO (контроллеры возвращают только DTO)
 ├─ exception   — обработка ошибок
 └─ config      — конфигурация
```

---

## Реализованная модель данных

### Сущности

User, Profile, Course, Category, CourseModule, Lesson,
Enrollment, Assignment, Submission, Quiz, Question,
AnswerOption, QuizSubmission, CourseReview, Tag, LessonProgress

### Типы связей

* OneToOne (User ↔ Profile)
* OneToMany (Course → Module → Lesson)
* ManyToMany (Course ↔ Tag)
* ManyToMany через сущность (Enrollment)
* OneToMany (Lesson → LessonProgress)

---

## REST API

### Users

```
POST /api/users
GET  /api/users
GET  /api/users/{id}
```

---

### Courses

```
GET    /api/courses
GET    /api/courses/{id}
GET    /api/courses/{id}/structure
POST   /api/courses
DELETE /api/courses/{id}
```

---

### Modules / Lessons (структура курса)

```
POST   /api/courses/{courseId}/modules
POST   /api/lessons
DELETE /api/lessons/{id}
```

---

### Enrollments

```
POST   /api/enrollments
DELETE /api/enrollments
GET    /api/enrollments
```

---

### Reviews

```
POST /api/reviews
GET  /api/reviews?courseId={id}
```

---

### Progress / Completion

```
POST /api/progress/complete
GET  /api/progress/course?courseId={id}&studentId={id}
```

Функциональность:

* отметка урока завершённым
* защита от повторного completion
* подсчёт прогресса по курсу
* процент completion

---

### Assignments

```
POST /api/assignments
POST /api/assignments/submit
POST /api/assignments/grade
GET  /api/assignments/submissions
```

---

### Quizzes

```
POST /api/quizzes
POST /api/quizzes/take
GET  /api/quizzes/{id}
```

---

## Особенности реализации

* Контроллеры возвращают только DTO (без Entity leakage)
* Единый глобальный обработчик исключений
* Lazy loading демонстрируется в тестах
* EntityGraph используется для загрузки структуры курса
* Проверка ролей пользователей на уровне сервисов
* Защита от дублей (Enrollment, Review, LessonProgress)

---

## Локальный запуск

### 1. Поднять PostgreSQL

```bash
docker run --name orm-postgres \
-e POSTGRES_DB=orm_db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-d postgres:15
```

---

### 2. Запуск приложения

```bash
mvn clean spring-boot:run
```

Приложение доступно:

```
http://localhost:8080
```

---

## Запуск через Docker Compose

```bash
docker compose up --build
```

API:

```
http://localhost:8080/api/courses
```

---

## Тестирование

```bash
mvn test
```

Используются:

* Testcontainers
* интеграционные тесты
* Lazy loading тест

---

## CI

GitHub Actions:

* Maven build
* запуск тестов
* Docker build

---

## Автор

Timur Khananiev
MEPHI — Итоговый проект по ORM-фреймворкам для Java.

