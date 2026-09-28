# Gestor de Finanzas · Spring Boot

Aplicación web de **finanzas personales**: crea cuentas, registra ingresos y gastos y consulta tu saldo.

![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)

## Funcionalidades

- **Cuentas**: crear, editar y eliminar, con moneda, saldo y descripción.
- **Transacciones**: registra ingresos y gastos con monto, categoría, nota y fecha.
- Relación **cuenta → transacciones** gestionada con JPA.
- Vistas web renderizadas en el servidor con Thymeleaf.

## Arquitectura

```
controller/   AccountController, TransactionController, HomeController   (capa web)
service/      AccountService, TransactionService                         (lógica de negocio)
repository/   AccountRepository, TransactionRepository                   (Spring Data JPA)
model/        Account, Transaction                                       (entidades)
templates/    accounts/, transactions/, index.html                       (vistas Thymeleaf)
```

## Stack

Spring Boot 3.5 · Spring Web · Spring Data JPA (Hibernate) · Thymeleaf · PostgreSQL · Lombok · Maven

## Ejecución

1. Crea la base de datos `gestorFinanzas` en PostgreSQL.
2. Define las credenciales con las variables de entorno `DB_USERNAME` (por defecto `postgres`) y `DB_PASSWORD`.
3. Ejecuta:

```bash
./mvnw spring-boot:run   # → http://localhost:8071
```

Hibernate crea y actualiza las tablas automáticamente (`ddl-auto=update`).

| Ruta | Descripción |
|---|---|
| `/` | Inicio |
| `/accounts` | Listado y CRUD de cuentas |
| `/transactions` | Listado y registro de transacciones |
