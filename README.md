# 🧩 User Management API

[![Java](https://img.shields.io/badge/Java-17-007396)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F)]()
[![Build](https://img.shields.io/badge/Build-Gradle-02303A)]()
[![License](https://img.shields.io/badge/license-MIT-blue.svg)]()

API REST para **gestión de usuarios y teléfonos** (modelo `User` ↔ `Phone`), con **UUID** como ID, **H2 en memoria** para desarrollo, validaciones (`jakarta.validation`), **Swagger/OpenAPI** y soporte para **token Bearer** en el header `Authorization` (requerido para POST).

---

## 📦 Requisitos

- **Java 17**
- **Gradle** (opcional; puedes usar el *wrapper* `./gradlew`)
- **Git**

---

## 🚀 Clonar el repositorio

```bash
git clone https://github.com/juanmanuel2586/user-management.git user-management
cd user-management
```

---

## ⚙️ Configuración (application.yml)

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:userdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: none
  h2:
    console:
      enabled: true
      path: /h2-console

springdoc:
  swagger-ui:
    path: /swagger-ui/index.html

app:
  password:
    regex: "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,64}$"
    message: "La contraseña debe tener 8-64 caract., con mayúscula, minúscula, dígito y símbolo"
  jwt:
    secret: "nisum-bci-user-management-secret"
    issuer: "user-management"
    expiration-millis: 3600000
```

---

## ▶️ Ejecutar la aplicación

Con **Gradle Wrapper** en Linux / Mac:

```bash
./gradlew clean bootRun
```

En **Windows (PowerShell o CMD)**:

```powershell
.\gradlew.bat clean bootRun
```

Si la compilación es correcta, la aplicación quedará disponible en:  

👉 [http://localhost:8080](http://localhost:8080)

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- API Docs (JSON): [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  
- Consola H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 🧠 Modelo de datos

| Tabla  | Campos principales                                                                 |
|--------|-------------------------------------------------------------------------------------|
| users  | id , name, email, password_hash, token, is_active, created, modified, last_login |
| phones | id (SERIAL), user_id, number, citycode, contrycode                           |

Relación: **User 1..N Phone**

---

## 🔌 Endpoints principales

| Método | Ruta                 | Descripción                         | Auth |
|-------:|----------------------|-------------------------------------|:----:|
| GET    | `/api/user/{id}`     | Obtener usuario por **ID**          |  No  |
| GET    | `/api/user?email=..` | Obtener usuario por **email**       |  No  |
| POST   | `/api/user`          | Crear usuario + teléfonos           |  No  |

### Ejemplo `POST /api/user` (request)

```json
{
  "name": "Juan Perez",
  "email": "juan.perez@example.com",
  "password": "Ja12345678.*",
  "phones": [{ 
      "number": "1234567", 
      "cityCode": "1", 
      "countryCode": "57" 
    }
  ]
}
```

**Headers:**
```
Content-Type: application/json
```

### Respuesta 201 (ejemplo)
```json
{
  "id": "11111111-1111-1111-1111-111111111111",
  "name": "Juan Perez",
  "email": "juan.perez@example.com",
  "isActive": true,
  "created": "2025-08-29T12:34:56Z",
  "modified": "2025-08-29T12:34:56Z",
  "lastLogin": "2025-08-29T12:34:56Z",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "phones": [
    {   "number": "1234567", 
        "cityCode": "1", 
        "countryCode": "57"
    }
  ]
}
```

---

## 🧪 Tests

Ejecutar pruebas:

```bash
./gradlew clean test
```

Generar reporte de cobertura:

```bash
./gradlew jacocoTestReport
# Reporte: build/reports/jacoco/test/html/index.html
```

---

Errores:
- **400** → Datos de entrada inválidos o no cumple con el formato esperado  
- **409** → Email proporcionado ya esta registrado  

Formato de errores:

```json
{"mensaje": "detalle del error"}
```

---

## 🧩 Tech Stack

- Java 17  
- Spring Boot 3 (Web, Data JPA, Validation)  
- H2 Database  
- Springdoc OpenAPI (Swagger UI)  
- Spring Security Crypto (BCrypt)  
- JUnit 5, MockMvc, Mockito  
- Gradle + JaCoCo  

---

