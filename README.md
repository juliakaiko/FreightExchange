# 🚛 Freight Exchange RESTful API

Монолитное RESTful Web API для биржи грузоперевозок. Биржа предназначена для экспедиторов, которые размещают заказы на перевозку грузов, и логистов (менеджеров перевозчиков), которые выбирают и выполняют эти заказы.

## 🛠 Технологии

- **Backend**: Java 17, Spring Boot
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL, JPA/Hibernate, Liquibase
- **Build**: Maven
- **Testing**: JUnit, Mockito, Spring Test
- **Deployment**: Docker

## 📌 Функциональность

### 👥 Роли пользователей

#### Логист (CarrierManager)
- Регистрация и аутентификация
- Внесение данных о фирме (Carrier) и автопарке (TruckPark)
- Редактирование и удаление своих данных и данных фирмы
- Просмотр и выбор заказа на перевозку
- Отмена заказа

#### Экспедитор (FreightForwarder)
- Регистрация и аутентификация
- Редактирование и удаление своих данных
- Создание, редактирование и удаление заказов

### 🔗 Связи между сущностями

| Связь         | Описание |
|---------------|----------|
| one-to-one    | `Carrier ↔ TruckPark` — у перевозчика есть один автопарк, автопарк принадлежит одному перевозчику |
| one-to-many   | `Carrier → Orders` — один перевозчик может выполнять много заказов |
| one-to-many   | `FreightForwarder → Orders` — один экспедитор может создать много заказов |
| many-to-many  | `Carrier ↔ CarrierManager` — один перевозчик может иметь нескольких логистов |

## 🚀 Примеры API запросов

### 1. Аутентификация экспедитора

POST /auth/authentication
Content-Type: application/json

{
  "email": "tuminas@gmail.com",
  "password": "111111"
}

### 2. Создание заказа
http
POST /app/orders
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderName": "MyOrder",
  "startPoint": "Brest",
  "finishPoint": "Moskow",
  "cargo": "Cookie",
  "freight": 1600,
  "valid": true
}

### 3. Регистрация менеджера
POST /auth/registration
Content-Type: application/json

{
  "email": "user@gmail.com",
  "password": "password",
  "firstName": "Mark",
  "surName": "Karmanovich",
  "role": "MANAGER"
}

### 4. Просмотр заказов (с пагинацией)
GET /app/pageable_orders?page=0&size=5
Authorization: Bearer <JWT_TOKEN>

### 5. Взять заказ
PUT /app/orders/take_order/MyOrder
Authorization: Bearer <JWT_TOKEN>

✨ Дополнительные возможности
🔐 Ролевая авторизация с JWT
📄 Пагинация и защита от некорректных параметров
❌ Централизованная обработка ошибок
📝 Логирование
✅ Покрытие тестами (JUnit, Mockito, Spring Test)

📧 Контакты
Автор проекта: Кайко Юлия Романовна
Email: pugaculia94@gmail.com
