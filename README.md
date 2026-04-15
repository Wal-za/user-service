# User Service API

Proyecto backend desarrollado en **Java Spring Boot** para la gestión de usuarios con autenticación, activación por correo y recuperación de contraseña.

## 🚀 Funcionalidades

- Registro de usuarios
- Activación de cuenta mediante token
- Inicio de sesión con JWT
- Protección de endpoints con Spring Security
- Recuperación de contraseña mediante token
- Restablecimiento de contraseña
- Envío de correos electrónicos
- Documentación con Swagger
- Frontend básico en HTML + JavaScript

## 🛠️ Tecnologías

- Java 21
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- PostgreSQL
- Spring Mail (SMTP)
- Swagger (springdoc-openapi)
- HTML / JavaScript

## ⚙️ Configuración del proyecto

Crear la base de datos en PostgreSQL:

CREATE DATABASE userdb;

Configurar `application.properties`:

spring.application.name=user-service  
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb  
spring.datasource.username=postgres  
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080

spring.mail.host=smtp.gmail.com  
spring.mail.port=587  
spring.mail.username=TU_CORREO@gmail.com  
spring.mail.password=TU_APP_PASSWORD  
spring.mail.properties.mail.smtp.auth=true  
spring.mail.properties.mail.smtp.starttls.enable=true

## 📌 Notas importantes

- Usa App Password si utilizas Gmail SMTP
- Asegúrate de tener PostgreSQL en ejecución antes de iniciar el proyecto
- Swagger UI: http://localhost:8080/swagger-ui.html