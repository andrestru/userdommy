# DummyJSON Login API - Spring Boot

Esta API permite autenticar usuarios contra dummy y guardar los accesos válidos en una base de datos PostgreSQL, incluyendo `accessToken` y `refreshToken`.

---

## 🚀 Endpoints disponibles

### 1. Autenticación de usuario

**POST** `/api/auth/login`

Autentica al usuario usando DummyJSON y guarda el login en la base de datos.

#### Request
```json
{
  "username": "emilys",
  "password": "emilyspass"
}

Es importante contar con la tabla creada en PostgreSQL
CREATE TABLE login (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    login_time TIMESTAMP NOT NULL,
    access_token TEXT NOT NULL,
    refresh_token TEXT
);


Tener los siguientes curl:
curl --location 'http://localhost:8080/api/auth/me' \
--header 'Authorization: token...'

curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "emilys",
  "password": "emilyspass"
}'
