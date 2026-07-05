# Trabajo Final - Aplicaciones Web

Tienda online académica desarrollada con **Spring Boot 3.3.5**, **Thymeleaf**, **Spring Data JPA** y **MySQL**.

## Descripción

Aplicación web de comercio electrónico donde los usuarios pueden:
- Ver productos disponibles con nombre, precio y stock
- Realizar compras seleccionando productos y cantidades
- Validar automáticamente disponibilidad de stock y saldo del usuario
- Recibir confirmación de compra con detalle de la transacción

## Tecnologías

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring MVC** con **Thymeleaf**
- **Spring Data JPA** + **MySQL**
- **Maven**
- **CSS** responsivo

## Estructura

```
final/
├── src/main/java/com/example/store/
│   ├── controllers/    → Controladores MVC
│   ├── model/          → Entidades JPA (Producto, Usuarios)
│   ├── repositories/   → Repositorios Spring Data
│   └── service/        → Lógica de negocio
├── src/main/resources/templates/  → Vistas Thymeleaf
└── pom.xml
```

## Cómo ejecutar

```bash
cd final
./mvnw spring-boot:run
```

Requiere una base de datos MySQL configurada en `application.properties`.
