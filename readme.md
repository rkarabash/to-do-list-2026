# User Stories: To-Do List (с использованием токенов)

## 1. Регистрация пользователя
**Как** новый пользователь,  
**Я хочу** зарегистрироваться в приложении (указав email и пароль),  
**Чтобы** иметь возможность сохранять свой список задач и возвращаться к нему позже.

## 2. Авторизация (получение токена)
**Как** пользователь,  
**Я хочу** авторизоваться, указав свой email и пароль,  
**Чтобы** получить токен и выполнять действия в приложении.

## 3. Добавление новой задачи
**Как** авторизованный пользователь (с валидным токеном),  
**Я хочу** добавлять задачи в свой личный список,  
**Чтобы** отслеживать свои дела и планировать работу.

## 4. Редактирование задачи
**Как** авторизованный пользователь,  
**Я хочу** иметь возможность редактировать существующие задачи (связанные с моим аккаунтом),  
**Чтобы** обновлять их содержание, дедлайны или приоритет при необходимости.

## 5. Отметка о выполнении задачи
**Как** авторизованный пользователь,  
**Я хочу** отмечать задачу выполненной,  
**Чтобы** видеть прогресс и понимать, какие задачи у меня ещё остаются.

## 6. Удаление задачи
**Как** авторизованный пользователь,  
**Я хочу** удалять задачи, которые мне больше не нужны,  
**Чтобы** поддерживать свой список в актуальном состоянии.

## 7. Фильтрация и сортировка задач (опционально)
**Как** активный пользователь,  
**Я хочу** фильтровать и сортировать задачи по приоритету, статусу (выполнено/невыполнено) или сроку,  
**Чтобы** быстрее находить нужную информацию и удобнее управлять своими задачами.

## 8. Настройки профиля (опционально)
**Как** пользователь,  
**Я хочу** иметь возможность настраивать некоторые параметры приложения (например, тему оформления, количество задач на странице),  
**Чтобы** адаптировать внешний вид и работу приложения под себя.

## 9. Выход из приложения (отзыв токена)
**Как** авторизованный пользователь,  
**Я хочу** иметь возможность «выйти» из приложения, отозвав свой токен,  
**Чтобы** более не иметь доступа к приватным данным.


# Схема сущностей БД
```mermaid
erDiagram

    USER ||--|{ TASK : "имеет"
    USER ||--|{ TOKEN : "может иметь несколько"

    USER {
        int id PK
        string email
        string password
        string username
    }

    TASK {
        int id PK
        int user_id FK
        string title
        string description
        boolean is_done
        date due_date
        date created_at
        date updated_at
        int priority
    }

    TOKEN {
        int id PK
        string value
        int user_id FK
        date expires_at
        boolean revoked
    }
```
# DDL
```sql
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255)
);

CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    is_done     BOOLEAN DEFAULT FALSE,
    due_date    TIMESTAMP,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    priority    INT     DEFAULT 0
);

CREATE TABLE tokens
(
    id         BIGSERIAL PRIMARY KEY,
    value      VARCHAR(255) NOT NULL UNIQUE,
    user_id    BIGINT       NOT NULL REFERENCES users,
    expires_at TIMESTAMP,
    revoked    BOOLEAN DEFAULT FALSE
);
```

# Команда для поднятия контейнеров
```shell
docker compose up -d
```

# Команда для актуализации мастер-ветки
```shell
git fetch
git merge origin/master master
```

# Swagger
[Ссылка на Swagger](http://localhost:8080/swagger-ui/index.html)