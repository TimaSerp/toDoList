# Сервис ведения задач ToDoList

## Задача

Разработать сервис ToDoList (ведения списка задач)

Должны быть следующие endpoint'ы:

- добавить новую задачу
- пометить задачу как выполненную
- получить список невыполненных задач
- удалить задачу

Задача - строка не более 1000 символов.

Для взаимодействия с сервисом использовать REST API.
Представление данных в формате JSON.  
Аутентификация и авторизация не требуются.

Дополнительные задания (опционально)

- Unit тесты
- Логирование

---

С доступными эндпойнтами можно ознакомиться в Swagger'е: http://localhost:8080/swagger-ui/index.html

Этапы запуска приложения:

1) mvn clean package
2) Запускаем TodoListApplication
3) Постучаться по эндпойнтам можно через Postman или курлами ниже:

**добавить новую задачу:**

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/tasks/create' \
  -H 'accept: */*' \
  -H 'X-Request-ID: 123' \
  -H 'Content-Type: application/json' \
  -d '{
  "task": "task"
}'
```

**пометить задачу как выполненную:**

```
curl -X 'PUT' \
  'http://localhost:8080/api/v1/tasks/1/complete' \
  -H 'accept: */*' \
  -H 'X-Request-ID: 123'
```

**получить список невыполненных задач:**

```
curl -X 'GET' \
  'http://localhost:8080/api/v1/tasks/uncompleted' \
  -H 'accept: */*' \
  -H 'X-Request-ID: 123'
```

**удалить задачу:**

```
curl -X 'DELETE' \
  'http://localhost:8080/api/v1/tasks/1' \
  -H 'accept: */*' \
  -H 'X-Request-ID: 123'
```